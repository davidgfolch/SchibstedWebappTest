package com.schibsted.webapp.di;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

/**
 * https://www.beyondjava.net/blog/build-dependency-injection-framework/ Note:
 * alternative implementation could be done with ServiceLoader standard java
 * 
 * @author slks
 */
public final class DIFactory {

	private static final Logger LOG = LogManager.getLogger(DIFactory.class);

	private static Map<Class<?>, Class<?>> namedClasses = new HashMap<>();
	private static Map<Class<?>, Class<?>> singletonClasses = new HashMap<>();
	private static Map<Class<?>, Object> singletonInstances = new HashMap<>();
	private static Reflections reflections;

	static {
		reflections = new Reflections("");
		find(Named.class, namedClasses);
		find(Singleton.class, singletonClasses);
		singletonClasses.keySet().stream().forEachOrdered(keyset -> LOG.debug("@Singleton class: " + keyset.getName()));
		Set<Class<?>> intersect = namedClasses.keySet().stream() //
				.filter(singletonClasses.keySet()::contains) //
				.collect(Collectors.toSet());
		intersect.forEach(claz -> LOG.warn(
				"@Singleton class don't need to be annotated with @Named, its redundant, for class: {}",
				claz.getName()));
		namedClasses.keySet().removeAll(singletonClasses.keySet());
		namedClasses.keySet().stream().forEachOrdered(keyset -> LOG.debug("@Named class: " + keyset.getName()));

		reflections = null;
	}

	private DIFactory() {
	}

	private static void find(Class<? extends Annotation> annotation, Map<Class<?>, Class<?>> classMap) {
		Set<Class<?>> types = reflections.getTypesAnnotatedWith(annotation);
		for (Class<?> implementationClass : types) {
			for (Class<?> iface : implementationClass.getInterfaces()) {
				classMap.put(iface, implementationClass);
			}
			classMap.put(implementationClass, implementationClass);
		}
	}

	public static <T> T inject(Class<? extends T> claz) throws ExceptionInInitializerError {
		T instance = isSingleton(claz) ? injectSingleton(claz) : injectNamed(claz);
		if (instance == null)
			throw new ExceptionInInitializerError(
					"No injector candidate, use @Named or @Singleton, for class: " + claz.getName());
		return instance;
	}

	private static boolean isSingleton(Class<?> claz) {
		return singletonClasses.get(claz) != null;
	}
	
	private static <T> T injectNamed(Class<? extends T> claz) {
		LOG.trace("Injecting new (@Named) instance of {}", claz.getName());
		return newInstance(claz);
	}

	@SuppressWarnings("unchecked")
	private static <T> T injectSingleton(Class<? extends T> claz) {
		if (singletonInstances.containsKey(claz)) {
			LOG.trace("Injecting singleton instance for {}", claz.getName());
			return (T) singletonInstances.get(claz);
		}
		LOG.trace("Injecting NEW singleton instance for {}", claz.getName());
		T instance = newInstance(claz);
		if (instance != null) {
			synchronized (singletonInstances) {
				singletonInstances.put(claz, instance);
			}
		}
		return instance;
	}

	private static <T> T newInstance(Class<? extends T> implClass) {
		try {
			return implClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.fatal(e);
		}
		return null;
	}

}

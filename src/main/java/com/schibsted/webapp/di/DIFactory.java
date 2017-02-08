package com.schibsted.webapp.di;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

/**
 * https://www.beyondjava.net/blog/build-dependency-injection-framework/
 * 
 * @author slks
 */
public final class DIFactory {

	private static final Logger LOG = LogManager.getLogger(DIFactory.class);

	private static Map<Class<?>, Class<?>> namedClasses = new HashMap<>();
	private static Map<Class<?>, Class<?>> singletonClasses = new HashMap<>();
	private static Map<Class<?>, Object> singletonInstances = new HashMap<>();
	// private static Map<Class<?>, Object> injectionPoints = new HashMap<>();
	private static Reflections reflections;

	static {
		reflections = new Reflections("");
		find(Named.class, namedClasses);
		find(Singleton.class, singletonClasses);
		reflections = null;
		// ServiceLoader.<S>
		// find(Inject.class,injectionPoints);
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

	@SuppressWarnings("unchecked")
	public static <T> T inject(Class<? extends T> claz) throws ExceptionInInitializerError {
		Class<?> implClass = namedClasses.get(claz);
		if (implClass == null)
			throw new ExceptionInInitializerError(
					"No injector candidate, use @Named and @Singleton, for class: " + claz.getName());
		if (singletonClasses.get(claz) == null)
			return (T) newInstance(implClass, false);
		if (singletonInstances.containsKey(claz))
			return (T) singletonInstances.get(implClass);
		return (T) newInstance(implClass, true);
	}

	private static Object newInstance(Class<?> implClass, boolean singleton) {
		try {
			Object service = implClass.newInstance();
			if (singleton) {
				synchronized (singletonInstances) {
					singletonInstances.put(implClass, service);
				}
			}
			return service;
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.fatal("Could not get candidate for " + implClass.getName(), e);
		}
		return null;
	}

}

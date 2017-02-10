package com.schibsted.webapp.server.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.inject.Singleton;

import org.reflections.Reflections;

import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.annotation.ContextPath;

@Singleton
public class ReflectionHelper {

	public Set<Class<?>> getContextHandlers() {
		// see https://code.google.com/archive/p/reflections/
		Reflections reflections = new Reflections("com.schibsted.webapp.controller");
		return reflections.getTypesAnnotatedWith(ContextHandler.class);
	}

	public boolean hasDefaultConstructor(Class<?> claz) {
		try {
			return claz.getConstructor() != null;
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
	}

	public boolean isControllerCandidate(Class<?> claz) {
		return !claz.isInterface() && //
				hasDefaultConstructor(claz) && //
				!Modifier.isAbstract(claz.getModifiers());
	}

	public String getAuthenticationRoles(IMVCController ctrl) {
		Authenticated authAnnon = ctrl.getClass().getAnnotation(Authenticated.class);
		return authAnnon == null ? null : authAnnon.role();
	}

	public String getContextPath(Class<? extends IController> claz) {
		ContextPath ann = getAnnotation(claz, ContextPath.class);
		if (ann == null)
			return null;
		return ann.value();
	}

	public ContextHandler getContextHandler(Class<? extends IController> claz) {
		return getAnnotation(claz, ContextHandler.class);
	}

	/**
	 * Recursively finds an annotation
	 * @param claz
	 * @param annotationClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T getAnnotation(Class<? extends IController> claz, Class<? extends Annotation> annotationClass) {
		if (claz.getSuperclass()==null) //is Object.class
			return null;
		Annotation annotation = claz.getAnnotation(annotationClass);
		if (annotation == null)
			annotation = claz.getAnnotatedSuperclass().getAnnotation(annotationClass);
		if (annotation == null) {
			Class<? extends IController> superClass= (Class<? extends IController>) claz.getSuperclass();
			if (superClass!=null)
				annotation=getAnnotation(superClass,annotationClass);
		}
		return (T) annotation;
	}

}

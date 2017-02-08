package com.schibsted.webapp.server.helper;

import java.lang.reflect.Modifier;
import java.util.Set;

import javax.inject.Singleton;

import org.reflections.Reflections;

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

	public String getContextPath(Class<?> claz) {
		ContextPath path = claz.getAnnotation(ContextPath.class);
		if (path == null)
			path = claz.getAnnotatedSuperclass().getAnnotation(ContextPath.class);
		if (path == null)
			return null;
		return path.value();
	}

}

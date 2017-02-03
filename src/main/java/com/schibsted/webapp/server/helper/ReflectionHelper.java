package com.schibsted.webapp.server.helper;

import java.lang.reflect.Modifier;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction") 
public class ReflectionHelper {

	private ReflectionHelper(){}

	public static boolean hasDefaultConstructor(Class<?> claz) {
		try {
			return claz.getConstructor() != null;
		} catch (NoSuchMethodException | SecurityException e) {
			return false;
		}
	}

	public static boolean isControllerCandidate(Class<?> claz) {
		return !claz.isInterface() && //
				hasDefaultConstructor(claz) && //
				!Modifier.isAbstract(claz.getModifiers());
	}

	public static String getAuthenticationRoles(HttpExchange ex) {
		IMVCController ctrl=(IMVCController) ex.getAttribute(Config.CONTROLLER);
		Authenticated authAnnon=ctrl.getClass().getAnnotation(Authenticated.class);
		return authAnnon==null?null:authAnnon.role();
	}

	public static String getContextPath(Class<?> claz) {
		ContextPath path = claz.getAnnotation(ContextPath.class);
		if (path == null)
			path = claz.getAnnotatedSuperclass().getAnnotation(ContextPath.class);
		if (path == null)
			return null;
		return path.value();
	}

}

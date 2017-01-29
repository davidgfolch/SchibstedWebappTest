package com.schibsted.webapp.server.helper;

import java.lang.reflect.Modifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction") 
public class ReflectionHelper {

	private static final Logger LOG = LogManager.getLogger(ReflectionHelper.class);

	private ReflectionHelper() {
	}

	public static boolean hasDefaultConstructor(Class<?> claz) {
		try {
			return claz.getConstructor() != null;
		} catch (NoSuchMethodException | SecurityException e) {
			LOG.error("", e);
			return false;
		}
	}

	public static boolean isControllerCandidate(Class<?> claz) {
		return claz.isInterface() || //
				!hasDefaultConstructor(claz) || //
				Modifier.isAbstract(claz.getModifiers());
	}

	public static String getAuthenticationRoles(HttpExchange ex) {
		IMVCController ctrl=(IMVCController) ex.getAttribute(Config.CONTROLLER);
		Authenticated authAnnon=ctrl.getClass().getAnnotation(Authenticated.class);
		return authAnnon.role();
	}

}

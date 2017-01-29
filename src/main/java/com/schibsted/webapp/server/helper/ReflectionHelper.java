package com.schibsted.webapp.server.helper;

import java.lang.reflect.Modifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

}

package com.schibsted.webapp.server.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.controller.web.LoginController;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.controller.TestController2;
import com.schibsted.webapp.server.injector.ConfigInjector;

public class ReflectionHelperTest extends ConfigInjector {

	private ReflectionHelper reflectionHelper = new ReflectionHelper();

	@Test
	public void hasDefaultConstructor() {
		assertTrue(reflectionHelper.hasDefaultConstructor(ReflectionHelperTest.class));
		// assertFalse(reflectionHelper.hasDefaultConstructor(ReflectionHelper.class));
	}

	@Test
	public void getContextPath() {
		assertEquals(config.get(Server.LOGIN_PATH), reflectionHelper.getContextPath(LoginController.class));
		assertEquals(null, reflectionHelper.getContextPath(TestController2.class));
	}

}

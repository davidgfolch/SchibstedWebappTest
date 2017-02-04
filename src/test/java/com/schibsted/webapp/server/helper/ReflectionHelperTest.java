package com.schibsted.webapp.server.helper;

import org.junit.*;
import static org.junit.Assert.*;

import com.schibsted.webapp.controller.web.LoginController;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.controller.TestController2;
import com.schibsted.webapp.server.injector.ConfigInjector;

public class ReflectionHelperTest extends ConfigInjector {

	@Test
	public void hasDefaultConstructor() {
		assertTrue(ReflectionHelper.hasDefaultConstructor(ReflectionHelperTest.class));
		assertFalse(ReflectionHelper.hasDefaultConstructor(ReflectionHelper.class));
	}

	@Test
	public void getContextPath() {
		assertEquals(config.get(Server.LOGIN_PATH), ReflectionHelper.getContextPath(LoginController.class));
		assertEquals(null, ReflectionHelper.getContextPath(TestController2.class));
	}

}

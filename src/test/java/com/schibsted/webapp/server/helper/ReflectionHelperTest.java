package com.schibsted.webapp.server.helper;

import static org.junit.Assert.*;
import org.junit.Test;

import com.schibsted.webapp.controller.web.LoginController;
import com.schibsted.webapp.server.Server;

public class ReflectionHelperTest {
	
	@Test
	public void hasDefaultConstructor() {
		assertTrue(ReflectionHelper.hasDefaultConstructor(ReflectionHelperTest.class));
		assertFalse(ReflectionHelper.hasDefaultConstructor(ReflectionHelper.class));
	}
	
	@Test
	public void getContextPath() {
		assertEquals(Server.getConfig().get(Server.LOGIN_PATH), ReflectionHelper.getContextPath(LoginController.class));
		assertEquals(null, ReflectionHelper.getContextPath(TestController2.class));
	}
	
}

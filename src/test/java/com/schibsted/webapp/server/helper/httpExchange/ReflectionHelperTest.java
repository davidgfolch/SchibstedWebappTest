package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.ServerTestHelper;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.TestController;

public class ReflectionHelperTest extends ServerHttpExchangeBaseTest {
	
	@Before
	public void before() {
		hook.setListener(this);
		try {
			ServerTestHelper.getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAuthenticationRoles() {
		String res=ReflectionHelper.getAuthenticationRoles(httpExchange);
		assertTrue(res==null);
	}

	@Test
	public void getContextPath() {
		assertTrue("/test".equals(ReflectionHelper.getContextPath(TestController.class)));
	}

	@Test
	public void hasDefaultConstructor() {
		assertTrue(ReflectionHelper.hasDefaultConstructor(TestController.class));
	}

	@Test
	public void isControllerCandidate() {
		assertTrue(ReflectionHelper.isControllerCandidate(TestController.class));
	}

}

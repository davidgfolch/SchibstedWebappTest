package com.schibsted.webapp.server.helper.httpExchange;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.controller.TestController;
import com.schibsted.webapp.server.helper.ReflectionHelper;

public class ReflectionHelperTest extends ServerHttpExchangeBaseTest {

	private ReflectionHelper reflectionHelper=new ReflectionHelper();

	@Before
	public void before() {
		hook.setListener(this);
		try {
			serverTestHelper.getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAuthenticationRoles() {
		String res=reflectionHelper.getAuthenticationRoles(httpExchange);
		assertTrue(res==null);
	}

	@Test
	public void getContextPath() {
		assertTrue("/test".equals(reflectionHelper.getContextPath(TestController.class)));
	}

	@Test
	public void hasDefaultConstructor() {
		assertTrue(reflectionHelper.hasDefaultConstructor(TestController.class));
	}

	@Test
	public void isControllerCandidate() {
		assertTrue(reflectionHelper.isControllerCandidate(TestController.class));
	}

}

package com.schibsted.webapp.server.helper.httpExchange;

import static com.schibsted.webapp.di.DIFactory.inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.base.ServerBaseTest;
import com.schibsted.webapp.server.controller.TestController;
import com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;
import com.schibsted.webapp.server.helper.ReflectionHelper;

public class ReflectionHelperTest extends ServerBaseTest {

	private ReflectionHelper reflectionHelper = inject(ReflectionHelper.class);

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
		IMVCController ctrl=httpExchangeHelper.getController(httpExchange);
		String res = reflectionHelper.getAuthenticationRoles(ctrl);
		assertTrue(res == null);
	}

	@Test
	public void getContextHandler() {
		ContextHandler ctxHandlerAnn=reflectionHelper.getContextHandler(TestController.class);
		assertEquals(CONTEXT_HANDLER.WEB_HANDLER,ctxHandlerAnn.contextHandler());
	}
	@Test
	public void getContextPath() {
		String path=reflectionHelper.getContextPath(TestController.class);
		assertEquals("/test",path);
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

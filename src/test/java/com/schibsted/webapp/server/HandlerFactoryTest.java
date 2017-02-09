package com.schibsted.webapp.server;

import static com.schibsted.webapp.di.DIFactory.inject;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.server.base.BaseTest;
import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;
import com.schibsted.webapp.server.handler.RestHandler;
import com.schibsted.webapp.server.handler.WebHandler;

public class HandlerFactoryTest extends BaseTest {

	private final HandlerFactory handlerFactory = inject(HandlerFactory.class);

	@Test
	public void getByEnumType() {
		assertTrue(handlerFactory.get(CONTEXT_HANDLER.WEB_HANDLER) instanceof WebHandler);
		assertTrue(handlerFactory.get(CONTEXT_HANDLER.REST_HANDLER) instanceof RestHandler);
	}

	@Test
	public void getByString() {
		assertTrue(handlerFactory.get(WebHandler.class.getSimpleName()) instanceof WebHandler);
		assertTrue(handlerFactory.get(RestHandler.class.getSimpleName()) instanceof RestHandler);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getByStringException() {
		handlerFactory.get("InexistentHandler");
	}

}

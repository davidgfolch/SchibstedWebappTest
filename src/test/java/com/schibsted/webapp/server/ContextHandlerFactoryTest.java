package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.WebHandler;

public class ContextHandlerFactoryTest {

	@Test
	public void getByEnumType() {
		assertTrue(HandlerFactory.get(HandlerFactory.CONTEXT_HANDLER.WEB_HANDLER) instanceof WebHandler);
	}

	@Test
	public void getByString() {
		assertTrue(HandlerFactory.get(WebHandler.class.getSimpleName()) instanceof WebHandler);
	}

}

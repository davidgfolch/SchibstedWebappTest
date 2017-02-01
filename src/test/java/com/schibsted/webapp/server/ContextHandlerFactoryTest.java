package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.server.contextHandler.HandlerFactory;
import com.schibsted.webapp.server.contextHandler.WebHandler;

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

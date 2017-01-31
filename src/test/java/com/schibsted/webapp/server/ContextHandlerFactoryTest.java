package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.server.contextHandler.ContextHandlerFactory;
import com.schibsted.webapp.server.contextHandler.WebContextHandler;

public class ContextHandlerFactoryTest {

	@Test
	public void getByEnumType() {
		assertTrue(ContextHandlerFactory.get(ContextHandlerFactory.CONTEXT_HANDLER.WEB_CONTEXT_HANDLER) instanceof WebContextHandler);
	}

	@Test
	public void getByString() {
		assertTrue(ContextHandlerFactory.get("WebContextHandler") instanceof WebContextHandler);
	}

}

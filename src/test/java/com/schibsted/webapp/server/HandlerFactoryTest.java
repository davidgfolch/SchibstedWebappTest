package com.schibsted.webapp.server;

import static org.junit.Assert.*;


import org.junit.Test;

import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.RestHandler;

import static com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;
import com.schibsted.webapp.server.handler.WebHandler;

public class HandlerFactoryTest {

	@Test
	public void getByEnumType() {
		assertTrue(HandlerFactory.get(CONTEXT_HANDLER.WEB_HANDLER) instanceof WebHandler);
		assertTrue(HandlerFactory.get(CONTEXT_HANDLER.REST_HANDLER) instanceof RestHandler);
	}

	@Test
	public void getByString() {
		assertTrue(HandlerFactory.get(WebHandler.class.getSimpleName()) instanceof WebHandler);
		assertTrue(HandlerFactory.get(RestHandler.class.getSimpleName()) instanceof RestHandler);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getByStringException() {
		HandlerFactory.get("InexistentHandler");
	}

}

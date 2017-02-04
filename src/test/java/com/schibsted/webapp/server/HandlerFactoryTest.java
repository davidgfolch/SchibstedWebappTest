package com.schibsted.webapp.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;
import com.schibsted.webapp.server.handler.RestHandler;
import com.schibsted.webapp.server.handler.WebHandler;
import com.schibsted.webapp.server.helper.CookieHelper;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.injector.ConfigInjector;

public class HandlerFactoryTest extends ConfigInjector {
	
	private final HandlerFactory handlerFactory;
	private final HttpExchangeHelper httpExchangeHelper;
	
	public HandlerFactoryTest() {
		httpExchangeHelper=new HttpExchangeHelper(new SessionHelper(config), new CookieHelper());
		this.handlerFactory=new HandlerFactory(config, httpExchangeHelper);
	}

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
	
	@Test(expected=IllegalArgumentException.class)
	public void getByStringException() {
		handlerFactory.get("InexistentHandler");
	}

}

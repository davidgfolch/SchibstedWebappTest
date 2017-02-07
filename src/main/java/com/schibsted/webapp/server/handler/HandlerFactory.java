package com.schibsted.webapp.server.handler;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.sun.net.httpserver.HttpHandler;

/**
 * Context handler factory<br/>
 * @author slks
 */
@SuppressWarnings("restriction")
public class HandlerFactory {
	
	private final Config config;
	private final HttpExchangeHelper exchangeHelper;
	private final HttpServerHelper httpServerHelper;
	
	public HandlerFactory(Config config, HttpExchangeHelper exchangeHelper, HttpServerHelper httpServerHelper) {
		this.config=config;
		this.exchangeHelper=exchangeHelper;
		this.httpServerHelper=httpServerHelper;
	}

	public enum CONTEXT_HANDLER {
		WEB_HANDLER, //
		REST_HANDLER
	}

	public HttpHandler get(CONTEXT_HANDLER handler) {
		switch (handler) {
		case REST_HANDLER:
			return new RestHandler();
		case WEB_HANDLER:
		default:
			return new WebHandler(config, exchangeHelper, httpServerHelper);
		}
	}

	public HttpHandler get(String handler) {
		String handlerEnum = handler.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
		try {
			CONTEXT_HANDLER ctxHdl = CONTEXT_HANDLER.valueOf(handlerEnum);
			return get(ctxHdl);
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("No contextHandler found: " + handler, iae);
		}
	}

}

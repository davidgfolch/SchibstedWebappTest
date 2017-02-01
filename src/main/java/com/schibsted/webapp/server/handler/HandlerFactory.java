package com.schibsted.webapp.server.handler;

import com.sun.net.httpserver.HttpHandler;

/**
 * Context handler factory<br/>
 * @author slks
 */
@SuppressWarnings("restriction")
public class HandlerFactory {

	public enum CONTEXT_HANDLER {
		WEB_HANDLER, //
		REST_HANDLER
	}

	public static HttpHandler get(CONTEXT_HANDLER handler) {
		switch (handler) {
		case REST_HANDLER:
			return new RestHandler();
		case WEB_HANDLER:
		default:
			return new WebHandler();
		}
	}

	public static HttpHandler get(String handler) {
		String handlerEnum = handler.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
		try {
			CONTEXT_HANDLER ctxHdl = CONTEXT_HANDLER.valueOf(handlerEnum);
			return get(ctxHdl);
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException("No contextHandler found: " + handler, iae);
		}
	}

}

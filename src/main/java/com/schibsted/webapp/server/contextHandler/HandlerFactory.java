package com.schibsted.webapp.server.contextHandler;

import com.sun.net.httpserver.HttpHandler;

/**
 * Context handler factory<br/>
 * 
 * @author slks
 */
@SuppressWarnings("restriction")
public class HandlerFactory {

	public enum CONTEXT_HANDLER {
		WEB_HANDLER, //
		REST_HANDLER
	}

	
	//todo: clean
	// public static Stack<HttpHandler> get(List<CONTEXT_HANDLER> handlers) {
	// final Stack<HttpHandler> handlersRes=new Stack<>();
	// for(CONTEXT_HANDLER handler: handlers) {
	// switch (handler) {
	// case DEFAULT: handlersRes.add((HttpHandler) new DefaultContextHandler());
	// default: break;
	// }
	// }
	// return handlersRes;
	// }
	public static HttpHandler get(CONTEXT_HANDLER handler) {
		switch (handler) {
		case REST_HANDLER:
			return new RestHandler();
		case WEB_HANDLER:
		default:
			return new WebHandler();
		}
	}

	// public static Stack<HttpHandler> get(String handler /*String
	// handlersList*/) {
	// List<String> handlers= Arrays.asList(handlersList.split(","));
	// List<CONTEXT_HANDLER> handlersTyped = new ArrayList<>();
	// for (String handler: handlers) {
	// handlersTyped.add(CONTEXT_HANDLER.valueOf(handler));
	// }
	// return get(handlersTyped);
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

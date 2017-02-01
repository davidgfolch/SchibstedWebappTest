package com.schibsted.webapp.server.handler;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public interface HttpHandlerTestCallbak {
	
	void beforeHandle(HttpExchange ex);
	void afterHandle(HttpExchange ex);

}

package com.schibsted.webapp.server.contextHandler;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public interface HttpHandlerTestCallbak {
	
	void beforeHandle(HttpExchange ex);
	void afterHandle(HttpExchange ex);

}

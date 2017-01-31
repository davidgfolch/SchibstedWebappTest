package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

//@Authenticated(role="ADMIN")
@SuppressWarnings("restriction")
public class WebContextHandlerTestHook extends WebContextHandler {
	
	private HttpHandlerTestCallbak listener=null;
	
	public void setListener(HttpHandlerTestCallbak listener) {
		this.listener=listener;
	}

	@Override
	public void handle(HttpExchange ex) throws IOException {
		listener.beforeHandle(ex);
		super.handle(ex);
		listener.afterHandle(ex);
	}

}

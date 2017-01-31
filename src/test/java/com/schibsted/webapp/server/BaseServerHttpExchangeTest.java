package com.schibsted.webapp.server;

import com.schibsted.webapp.server.contextHandler.HttpHandlerTestCallbak;
import com.schibsted.webapp.server.contextHandler.WebContextHandlerTestHook;
import com.schibsted.webapp.server.helper.TestController;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class BaseServerHttpExchangeTest extends BaseServerTest implements HttpHandlerTestCallbak {

	protected static Server SERVER = new Server().startServer();

	protected HttpExchange httpExchange = null;
	
	protected static WebContextHandlerTestHook hook=new WebContextHandlerTestHook();

	
	static  {
		HttpContext ctx = SERVER.registerHandler("/test", hook);
		SERVER.setHandlerController(ctx, new TestController());
	}

	@Override
	public void beforeHandle(HttpExchange ex) {
		this.httpExchange = ex;
	}

	@Override
	public void afterHandle(HttpExchange ex) {
	}

}

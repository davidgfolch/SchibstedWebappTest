package com.schibsted.webapp.server;

import org.junit.Assert;
import org.junit.Test;

import com.schibsted.webapp.server.contextHandler.HttpHandlerTestCallbak;
import com.schibsted.webapp.server.contextHandler.WebContextHandlerTestHook;
import com.schibsted.webapp.server.helper.TestController;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class ServerHttpExchangeBaseTest implements HttpHandlerTestCallbak {

	protected static Server server = new Server();
	protected static boolean serverStarted=false;;
	
	static {
		serverStarted=server.startServer();
	}

	protected HttpExchange httpExchange = null;
	
	protected static WebContextHandlerTestHook hook=new WebContextHandlerTestHook();

	
	static  {
		HttpContext ctx = server.registerHandler("/test", hook);
		server.setHandlerController(ctx, new TestController());
	}

	@Override
	public void beforeHandle(HttpExchange ex) {
		this.httpExchange = ex;
	}

	@Override
	public void afterHandle(HttpExchange ex) {
	}
	
	@Test
	public void startServer() {
		Assert.assertTrue(serverStarted);
	}

}

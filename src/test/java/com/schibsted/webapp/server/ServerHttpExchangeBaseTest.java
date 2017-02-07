package com.schibsted.webapp.server;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.junit.Test;

import com.schibsted.webapp.server.controller.TestController;
import com.schibsted.webapp.server.exception.ConfigurationException;
import com.schibsted.webapp.server.handler.HttpHandlerTestCallbak;
import com.schibsted.webapp.server.handler.WebContextHandlerTestHook;
import com.schibsted.webapp.server.helper.CookieHelper;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class ServerHttpExchangeBaseTest implements HttpHandlerTestCallbak, ILogger {

	private static boolean serverStarted=false;
	private static Server server;

	protected static WebContextHandlerTestHook hook;

	protected static Config config;
	protected static HttpExchangeHelper httpExchangeHelper;
	protected static SessionHelper sessionHelper;
	protected static ServerTestHelper serverTestHelper;
	protected HttpServerHelper httpServerHelper;
	
	
	protected HttpExchange httpExchange = null;
	protected CookieHelper cookieHelper=new CookieHelper();
	protected ParameterHelper parameterHelper=new ParameterHelper();
	
	static {
		try {
			server=new Server();
		} catch (ConfigurationException e) {
			LogManager.getLogger(ServerHttpExchangeBaseTest.class).error("",e);
			Assert.fail(e.getMessage());
		}
	}
	
	public ServerHttpExchangeBaseTest() {
		if (!serverStarted) {
			config=server.getConfig();
			sessionHelper=new SessionHelper(config); //TODO: GET FROM server OBJECT
			serverTestHelper=new ServerTestHelper(config);
			httpExchangeHelper=new HttpExchangeHelper(new SessionHelper(config), cookieHelper);
			httpServerHelper=new HttpServerHelper();
			hook=new WebContextHandlerTestHook(config,httpExchangeHelper);
			try {
				server.startServer();
				serverStarted=true;
			} catch (IOException e) {
				logger().error("",e);
				Assert.fail(e.getMessage());
			}		
			HttpContext ctx = server.registerHandler("/test", hook);
			server.setHandlerController(ctx, new TestController());
		}
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

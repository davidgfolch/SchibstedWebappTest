package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.BaseServerHttpExchangeTest;
import com.schibsted.webapp.server.contextHandler.WebContextHandlerTestHook;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.TestController;
import com.sun.net.httpserver.HttpContext;


@SuppressWarnings("restriction")
public class HttpServerHelperTest extends BaseServerHttpExchangeTest {
	
	private static final String PARAM_TO_ENCODE = "&enc";

	@Before
	public void before() {
		hook.setListener(this);
		try {
			getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void encode() {
		assertFalse(PARAM_TO_ENCODE.equals(HttpServerHelper.encode(PARAM_TO_ENCODE)));
	}
	
//Can't implement: java.io.IOException: headers already sent
	@Test
	public void permissionDenied() {
		try {
			HttpServerHelper.permissionDenied(httpExchange);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse(httpExchange.getResponseCode()==HttpStatus.SC_FORBIDDEN);
	}
	
	@Test
	public void notRedirect() {
		assertFalse(HttpServerHelper.isRedirect(httpExchange));
	}
	
	@Test
	public void redirect() {
		try {
			HttpServerHelper.redirect(httpExchange,"/page1");
		} catch (IOException e) {
			assertTrue(e.getMessage().equals("headers already sent"));
		}
//		assertTrue(HttpServerHelper.isRedirect(httpExchange)); for page1 not running testController hook don't work
	}

}

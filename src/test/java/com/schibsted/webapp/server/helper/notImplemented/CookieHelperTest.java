package com.schibsted.webapp.server.helper.notImplemented;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.ServerTestHelper;
import com.schibsted.webapp.server.helper.CookieHelper;
import com.schibsted.webapp.server.helper.SessionHelper;


public class CookieHelperTest extends ServerHttpExchangeBaseTest {
	
	@Before
	public void before() {
		hook.setListener(this);
		try {
			ServerTestHelper.getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		assertNotNull(CookieHelper.getCookie(httpExchange, SessionHelper.SCHIBSTED_SESSION));
//		CookieHelper.getCookie(cookieHeaders, cookieName)
//		CookieHelper.setCookie(httpExchange, cookie, value);
//		CookieHelper.setCookie(httpExchange, cookie, value, httpOnly, expires);
//		assertTrue(cond);
	}

}

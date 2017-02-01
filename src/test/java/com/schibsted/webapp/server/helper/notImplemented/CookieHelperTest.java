package com.schibsted.webapp.server.helper.notImplemented;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.ServerTestHelper;
import com.schibsted.webapp.server.helper.CookieHelper;
import com.schibsted.webapp.server.helper.SessionHelper;


public class CookieHelperTest extends ServerHttpExchangeBaseTest {
	
	private static final String NON_EXISTENT_COOKIE = "nonExistentCookieXXJDJFASDFKLAKJD";
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
	public void getCookie() {
		//From HttpExchange
		assertNotNull(CookieHelper.getCookie(httpExchange, SessionHelper.SCHIBSTED_SESSION));
		//From List
		//assertNotNull(CookieHelper.getCookie(cookieHeaders, cookieName)
		assertNull(CookieHelper.getCookie(new ArrayList<String>(), NON_EXISTENT_COOKIE));
		assertNull(CookieHelper.getCookie((List<String>)null, NON_EXISTENT_COOKIE));
	}
	@Test
	public void setCookie() {
		String CV = "valueTest";
		String C = "cookieTest";
		CookieHelper.setCookie(httpExchange, C, CV);
		assertEquals(CV,CookieHelper.getCookie(httpExchange, C));
		CookieHelper.setCookie(httpExchange, C, CV, true, null);
		assertEquals(CV,CookieHelper.getCookie(httpExchange, C));
		CookieHelper.setCookie(httpExchange, C, CV, true, 1000l);
		assertEquals(CV,CookieHelper.getCookie(httpExchange, C));
		CookieHelper.setCookie(httpExchange, C, CV, false, null);
		assertEquals(CV,CookieHelper.getCookie(httpExchange, C));
		CookieHelper.setCookie(httpExchange, C, CV, false, 1000l);
		assertEquals(CV,CookieHelper.getCookie(httpExchange, C));
	}

}

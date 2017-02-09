package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.base.ServerBaseTest;

public class CookieHelperTest extends ServerBaseTest {

	private static final String INEXISTENT_COOKIE = "nonExistentCookieXXJDJFASDFKLAKJD";

	@Before
	public void before() {
		hook.setListener(this);
			try {
				serverTestHelper.getResponseCode("/test");
			} catch (IOException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
	}

	@Test
	public void getCookie() {
		// From HttpExchange
		assertNotNull(cookieHelper.getCookie(httpExchange, sessionHelper.getCookieName()));
		// From List
		// assertNotNull(cookieHelper.getCookie(cookieHeaders, cookieName)
		assertFalse(cookieHelper.getCookie(new ArrayList<String>(), INEXISTENT_COOKIE).isPresent());
		assertFalse(cookieHelper.getCookie((List<String>) null, INEXISTENT_COOKIE).isPresent());
	}

	@Test
	public void setCookie() {
		String cVal = "valueTest";
		String cName = "cookieTest";
		cookieHelper.setCookie(httpExchange, cName, cVal);
		assertEquals(cVal, cookieHelper.getCookie(httpExchange, cName));
		cookieHelper.setCookie(httpExchange, cName, cVal, true, null);
		assertEquals(cVal, cookieHelper.getCookie(httpExchange, cName));
		cookieHelper.setCookie(httpExchange, cName, cVal, true, 1000l);
		assertEquals(cVal, cookieHelper.getCookie(httpExchange, cName));
		cookieHelper.setCookie(httpExchange, cName, cVal, false, null);
		assertEquals(cVal, cookieHelper.getCookie(httpExchange, cName));
		cookieHelper.setCookie(httpExchange, cName, cVal, false, 1000l);
		assertEquals(cVal, cookieHelper.getCookie(httpExchange, cName));
	}

}

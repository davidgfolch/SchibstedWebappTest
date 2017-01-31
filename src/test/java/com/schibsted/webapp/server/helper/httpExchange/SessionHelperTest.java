package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.BaseServerHttpExchangeTest;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.model.Session;


public class SessionHelperTest extends BaseServerHttpExchangeTest {

	private Long timeout=Long.valueOf(Server.getConfig().getInt(SessionHelper.SESSION_TIMEOUT_MS));

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
	public void test() {
		Session session=SessionHelper.getSession(httpExchange);
		assertNotNull(session);
		SessionHelper.invalidateSession(session);
		assertTrue(session.equals(SessionHelper.getSession(httpExchange)));
		assertFalse(SessionHelper.isAuthenticated(httpExchange));
		assertTrue(!SessionHelper.isSessionTimedOut(session));
		session.setLastUsed(timeout*2);
		assertTrue(!SessionHelper.isSessionTimedOut(session));
	}

}

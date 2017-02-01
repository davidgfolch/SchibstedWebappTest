package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.ServerTestHelper;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.model.Session;


public class SessionHelperTest extends ServerHttpExchangeBaseTest {

	private Long timeout=Long.valueOf(Server.getConfig().getInt(SessionHelper.SESSION_TIMEOUT_MS));

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
	public void invalidateSession() {
		Session session=HttpExchangeHelper.getSession(httpExchange);
		assertNotNull(session);
		SessionHelper.invalidateSession(session);
		assertFalse(session.equals(HttpExchangeHelper.getSession(httpExchange)));
		assertFalse(HttpExchangeHelper.isAuthenticated(httpExchange));
		assertFalse(SessionHelper.isSessionTimedOut(session));
	}
	
	@Test
	public void timeout() {
		Session session=HttpExchangeHelper.getSession(httpExchange);
		assertNotNull(session);
		session.setLastUsed(System.currentTimeMillis()-timeout*2);
		assertNotEquals(session.getUuid(),SessionHelper.getSession(session.getUuid()).getUuid());
		session.setLastUsed(System.currentTimeMillis()-timeout*2);
		assertTrue(SessionHelper.isSessionTimedOut(session));
	}

	@Test
	public void equals() {
		Session session=HttpExchangeHelper.getSession(httpExchange);
		Session session2=HttpExchangeHelper.getSession(httpExchange);
		assertTrue(session.equals(session2));
	}

}

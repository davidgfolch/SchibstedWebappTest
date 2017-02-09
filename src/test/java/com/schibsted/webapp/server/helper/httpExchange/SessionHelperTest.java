package com.schibsted.webapp.server.helper.httpExchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.schibsted.webapp.server.base.ServerBaseTest;
import com.schibsted.webapp.server.model.Session;

public class SessionHelperTest extends ServerBaseTest {

	public SessionHelperTest() {
		super();
	}

	@Before
	public void before() {
		hook.setListener(this);
		try {
			serverTestHelper.getResponseCode("/test");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void invalidateSession() {
		Session session = httpExchangeHelper.getSession(httpExchange);
		assertNotNull(session);
		sessionHelper.invalidateSession(session);
		assertNotEquals(session,httpExchangeHelper.getSession(httpExchange));
		assertFalse(httpExchangeHelper.isAuthenticated(httpExchange));
		assertFalse(sessionHelper.isSessionTimedOut(session));
	}

	@Test
	public void timeout() {
		Session session = httpExchangeHelper.getSession(httpExchange);
		assertNotNull(session);
		session.setLastUsed(System.currentTimeMillis() - sessionHelper.getTimeoutMs() * 2);
		assertNotEquals(session.getUuid(), sessionHelper.getSession(session.getUuid()).getUuid());
		session.setLastUsed(System.currentTimeMillis() - sessionHelper.getTimeoutMs() * 2);
		assertTrue(sessionHelper.isSessionTimedOut(session));
	}

	@Test
	public void sessionEquals() {
		Session session = httpExchangeHelper.getSession(httpExchange);
		Session session2 = httpExchangeHelper.getSession(httpExchange);
		assertEquals(session,session2);
	}

}

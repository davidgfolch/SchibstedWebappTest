package com.schibsted.webapp.server.helper.httpExchange;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;

import com.schibsted.webapp.server.ServerHttpExchangeBaseTest;
import com.schibsted.webapp.server.model.Session;

public class SessionHelperTest extends ServerHttpExchangeBaseTest {
	
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
		Session session=httpExchangeHelper.getSession(httpExchange);
		assertNotNull(session);
		sessionHelper.invalidateSession(session);
		assertFalse(session.equals(httpExchangeHelper.getSession(httpExchange)));
		assertFalse(httpExchangeHelper.isAuthenticated(httpExchange));
		assertFalse(sessionHelper.isSessionTimedOut(session));
	}
	
	@Test
	public void timeout() {
		Session session=httpExchangeHelper.getSession(httpExchange);
		assertNotNull(session);
		session.setLastUsed(System.currentTimeMillis()-sessionHelper.getTimeoutMs()*2);
		assertNotEquals(session.getUuid(),sessionHelper.getSession(session.getUuid()).getUuid());
		session.setLastUsed(System.currentTimeMillis()-sessionHelper.getTimeoutMs()*2);
		assertTrue(sessionHelper.isSessionTimedOut(session));
	}

	@Test
	public void equals() {
		Session session=httpExchangeHelper.getSession(httpExchange);
		Session session2=httpExchangeHelper.getSession(httpExchange);
		assertTrue(session.equals(session2));
	}

}

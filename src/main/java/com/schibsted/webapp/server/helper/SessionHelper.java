package com.schibsted.webapp.server.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.model.Session;

//todo: find & remove timedout sessions in an scheduled thread
//todo: concurrent hashmap
public class SessionHelper {

	private static final Logger LOG = LogManager.getLogger(SessionHelper.class);

	public static final String SESSION_TIMEOUT_MS = "session.timeoutMs";
	private static final String SESSION_COOKIE_NAME = "session.cookieName";
	
	private long timeoutMs;
	private String cookieName;

	private static Map<String, Session> sessions = new HashMap<>();

	public SessionHelper(Config config) {
		timeoutMs = config.getInt(SESSION_TIMEOUT_MS);
		cookieName = config.get(SESSION_COOKIE_NAME);
	}

	public synchronized Session getSession(String sessionUUID) {
		Session session = sessions.get(sessionUUID);
		if (session == null)
			session = newSession();
		else if (isSessionTimedOut(session)) {
			sessions.remove(sessionUUID);
			session = newSession();
		}
		return session;
	}

	private Session newSession() {
		String uuid = UUID.randomUUID().toString();
		LOG.debug("Creating new session: {}", uuid);
		Session session = new Session(uuid, System.currentTimeMillis());
		sessions.put(uuid, session);
		return session;
	}

	public boolean isSessionTimedOut(Session session) {
		boolean timedOut = session.getLastUsed() + timeoutMs < System.currentTimeMillis();
		if (timedOut) {
			LOG.debug("Session timeout, removing: {}", session.getUuid());
			sessions.remove(session.getUuid());
		}
		return timedOut;
	}
	
	public synchronized void invalidateSession(Session session) {
		sessions.remove(session.getUuid());
	}

	public String getCookieName() {
		return cookieName;
	}

	public long getTimeoutMs() {
		return timeoutMs;
	}

}

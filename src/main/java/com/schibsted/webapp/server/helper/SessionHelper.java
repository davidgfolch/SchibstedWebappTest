package com.schibsted.webapp.server.helper;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Singleton;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.exception.ConfigurationException;
import com.schibsted.webapp.server.model.Session;

//todo: find & remove timedout sessions in an scheduled thread
@Singleton
public class SessionHelper implements ILogger {

	public static final String TIMEOUT_MS = "session.timeoutMs";
	private static final String COOKIE_NAME = "session.cookieName";

	private Config config = inject(Config.class);
	private final long timeoutMs;
	private final String cookieName;

	private static final Map<String, Session> SESSIONS = new HashMap<>();

	public SessionHelper() throws ConfigurationException {
		timeoutMs = config.getInt(TIMEOUT_MS);
		cookieName = config.get(COOKIE_NAME);
	}

	public synchronized Session getSession(String sessionUUID) {
		Session session = SESSIONS.get(sessionUUID);
		if (session == null)
			session = newSession();
		else if (isSessionTimedOut(session)) {
			SESSIONS.remove(sessionUUID);
			session = newSession();
		}
		return session;
	}

	private Session newSession() {
		String uuid = UUID.randomUUID().toString();
		logger().debug("Creating new session: {}", uuid);
		Session session = new Session(uuid, System.currentTimeMillis());
		SESSIONS.put(uuid, session);
		return session;
	}

	public boolean isSessionTimedOut(Session session) {
		boolean timedOut = session.getLastUsed() + timeoutMs < System.currentTimeMillis();
		if (timedOut) {
			logger().debug("Session timeout, removing: {}", session.getUuid());
			SESSIONS.remove(session.getUuid());
		}
		return timedOut;
	}

	public synchronized void invalidateSession(Session session) {
		SESSIONS.remove(session.getUuid());
	}

	public String getCookieName() {
		return cookieName;
	}

	public long getTimeoutMs() {
		return timeoutMs;
	}

}

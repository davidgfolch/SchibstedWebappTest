package com.schibsted.webapp.server.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.model.Session;

public class SessionHelper {

	public static final String SESSION_TIMEOUT_MS = "session.timeoutMs";
	private static final String SESSION_COOKIE_NAME = "session.cookieName";
	
	private static final Config config = Server.getConfig();
	static final long TIMEOUT_MS = config.getInt(SESSION_TIMEOUT_MS);
	public static final String SCHIBSTED_SESSION = config.get(SESSION_COOKIE_NAME);

	private static final Logger LOG = LogManager.getLogger(SessionHelper.class);

	private static Map<String, Session> sessions = new HashMap<>();

	private SessionHelper() {
	}

	public static synchronized Session getSession(String sessionUUID) {
		Session session = sessions.get(sessionUUID);
		if (session == null)
			session = newSession();
		else if (isSessionTimedOut(session)) {
			sessions.remove(sessionUUID);
			session = newSession();
		}
		return session;
	}

	private static Session newSession() {
		String uuid = UUID.randomUUID().toString();
		LOG.debug("Creating new session: {}", uuid);
		Session session = new Session(uuid, System.currentTimeMillis());
		sessions.put(uuid, session);
		return session;
	}

	public static boolean isSessionTimedOut(Session session) {
		boolean timedOut = session.getLastUsed() > System.currentTimeMillis() + TIMEOUT_MS;
		if (timedOut) {
			LOG.debug("Session timeout, removing: {}", session.getUuid());
			sessions.remove(session.getUuid());
		}
		return timedOut;
	}
	
	public static void invalidateSession(Session session) {
		sessions.remove(session.getUuid());
	}

}

package com.schibsted.webapp.server.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.model.Session;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class SessionHelper {

	private static final String SESSION_COOKIE_NAME = "session.cookieName";
	private static final String SESSION_TIMEOUT_MS = "session.timeoutMs";

	private static final Config config = Server.getConfig();
	private static final long TIMEOUT_MS = config.getInt(SESSION_TIMEOUT_MS);
	private static final String SCHIBSTED_SESSION = config.get(SESSION_COOKIE_NAME);

	private static final Logger LOG = LogManager.getLogger(SessionHelper.class);

	private static Map<String, Session> sessions = new HashMap<>();

	private SessionHelper() {
	}

	public static synchronized Session getSession(HttpExchange ex) {
		String sessionUUID = CookieHelper.getCookie(ex, SCHIBSTED_SESSION);
		Session session = sessions.get(sessionUUID);
		if (session == null)
			session = newSession();
		else if (isSessionTimedOut(session)) {
			sessions.remove(sessionUUID);
			session = newSession();
		}
		long expires = System.currentTimeMillis() + TIMEOUT_MS;
		CookieHelper.setCookie(ex, SCHIBSTED_SESSION, session.getUuid(), true, expires);
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

	public static boolean isAuthenticated(HttpExchange ex) { // todo: move
																// somewhere
																// else
		return getSession(ex).getLoggedUser() != null;
	}

}
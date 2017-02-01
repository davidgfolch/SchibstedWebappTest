package com.schibsted.webapp.server.helper;

import com.schibsted.webapp.server.model.Session;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpExchangeHelper {
	
	public static Session getSession(HttpExchange ex) {
		String uuid=CookieHelper.getCookie(ex, SessionHelper.SCHIBSTED_SESSION);
		Session session=SessionHelper.getSession(uuid);
		long expires = System.currentTimeMillis() + SessionHelper.TIMEOUT_MS;
		CookieHelper.setCookie(ex, SessionHelper.SCHIBSTED_SESSION, session.getUuid(), true, expires);
		return session;
	}

	public static boolean isAuthenticated(HttpExchange ex) { 
		return getSession(ex).getLoggedUser() != null;
	}

}

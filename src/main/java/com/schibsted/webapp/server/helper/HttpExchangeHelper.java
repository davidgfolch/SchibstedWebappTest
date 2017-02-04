package com.schibsted.webapp.server.helper;

import com.schibsted.webapp.server.model.Session;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpExchangeHelper {
	
	private final SessionHelper sessionHelper;
	private final CookieHelper cookieHelper;
	
	public HttpExchangeHelper(SessionHelper sessionHelper, CookieHelper cookieHelper) {
		this.sessionHelper=sessionHelper;
		this.cookieHelper=cookieHelper;
	}
	
	public Session getSession(HttpExchange ex) {
		String uuid=cookieHelper.getCookie(ex, sessionHelper.getCookieName());
		Session session=sessionHelper.getSession(uuid);
		long expires = System.currentTimeMillis() + sessionHelper.getTimeoutMs();
		cookieHelper.setCookie(ex, sessionHelper.getCookieName(), session.getUuid(), true, expires);
		return session;
	}

	public boolean isAuthenticated(HttpExchange ex) { 
		return getSession(ex).getLoggedUser() != null;
	}

}

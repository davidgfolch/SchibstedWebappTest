package com.schibsted.webapp.server.helper;

import com.schibsted.webapp.server.model.Session;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpExchangeHelper {
	
	SessionHelper sessionHelper;
	
	public HttpExchangeHelper(SessionHelper sessionHelper) {
		this.sessionHelper=sessionHelper;
	}
	
	public Session getSession(HttpExchange ex) {
		String uuid=CookieHelper.getCookie(ex, sessionHelper.getCookieName());
		Session session=sessionHelper.getSession(uuid);
		long expires = System.currentTimeMillis() + sessionHelper.getTimeoutMs();
		CookieHelper.setCookie(ex, sessionHelper.getCookieName(), session.getUuid(), true, expires);
		return session;
	}

	public boolean isAuthenticated(HttpExchange ex) { 
		return getSession(ex).getLoggedUser() != null;
	}

}

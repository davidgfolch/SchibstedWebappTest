package com.schibsted.webapp.server.helper;

import static com.schibsted.webapp.di.DIFactory.inject;

import javax.inject.Named;
import javax.inject.Singleton;

import com.schibsted.webapp.server.model.Session;
import com.sun.net.httpserver.HttpExchange;

@Named
@Singleton
@SuppressWarnings("restriction")
public class HttpExchangeHelper {

	private final SessionHelper sessionHelper = inject(SessionHelper.class);
	private final CookieHelper cookieHelper = inject(CookieHelper.class);

	public Session getSession(HttpExchange ex) {
		String uuid = cookieHelper.getCookie(ex, sessionHelper.getCookieName());
		Session session = sessionHelper.getSession(uuid);
		long expires = System.currentTimeMillis() + sessionHelper.getTimeoutMs();
		cookieHelper.setCookie(ex, sessionHelper.getCookieName(), session.getUuid(), true, expires);
		return session;
	}

	public boolean isAuthenticated(HttpExchange ex) {
		return getSession(ex).getLoggedUser() != null;
	}

}

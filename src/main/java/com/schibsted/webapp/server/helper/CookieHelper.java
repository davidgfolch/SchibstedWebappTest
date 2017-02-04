package com.schibsted.webapp.server.helper;
import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.schibsted.webapp.server.ILogger;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class CookieHelper implements ILogger {

	private static final String COOKIE_SEPARATOR = ";";
	private static final String REQ_HEADER_COOKIE = "Cookie";
	private static final String RES_HEADER_COOKIE = "Set-Cookie";

	private static final String COOKIE_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

	/**
	 * Get cookie from request or response headers
	 * 
	 * @param ex
	 * @param cookieName
	 * @return
	 */
	public String getCookie(HttpExchange ex, String cookieName) {
		List<String> cookieHeaders = ex.getResponseHeaders().get(RES_HEADER_COOKIE);
		String res = getCookie(cookieHeaders, cookieName);
		if (res != null)
			return res;
		cookieHeaders = ex.getRequestHeaders().get(REQ_HEADER_COOKIE);
		return getCookie(cookieHeaders, cookieName);
	}

	/**
	 * Get cookie from a list of headers
	 * 
	 * @param cookieHeaders
	 * @param cookieName
	 * @return
	 */
	public String getCookie(List<String> cookieHeaders, String cookieName) {
		if (cookieHeaders == null)
			return null;
		for (String c : cookieHeaders) {
			String res = parse(c, cookieName);
			if (res != null)
				return res;
		}
		return null;
	}

	/**
	 * Parse cookies found between separators.
	 * 
	 * @param c
	 * @param cookieName
	 * @return
	 */
	private String parse(String c, String cookieName) {
		if (c == null)
			return null;
		for (String c2 : c.split(COOKIE_SEPARATOR)) {
			String cs = c2.trim();
			logger().debug("Parsing cookie: {}",cs);
			List<HttpCookie> lstHttpCookie = HttpCookie.parse(cs);
			for (HttpCookie cookie : lstHttpCookie) {
				logger().trace(cookie);
				if (cookieName.equalsIgnoreCase(cookie.getName()))
					return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * Sets cookie (v2 cookies that allow multicookies)
	 * 
	 * @see java.net.HttpCookie
	 * @param ex
	 * @param cookie
	 * @param value
	 */
	public void setCookie(HttpExchange ex, String cookie, String value) {
		setCookie(ex, cookie, value, true, null);
	}

	public synchronized void setCookie(HttpExchange ex, String cookie, String value, Boolean httpOnly, Long expires) {
		String cookieValue = cookie + "=" + value + COOKIE_SEPARATOR + " version=1";
		if (expires != null) {
			cookieValue += COOKIE_SEPARATOR + " expires=" + formatExpiresDate(expires);
		}
		if (httpOnly != null)
			cookieValue += COOKIE_SEPARATOR + " HttpOnly=1";
		ex.getResponseHeaders().add(RES_HEADER_COOKIE, cookieValue);
	}
	
	public synchronized String formatExpiresDate(Long expires) {
		SimpleDateFormat sdfExpires = new SimpleDateFormat(COOKIE_DATE_FORMAT, Locale.US);
		sdfExpires.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdfExpires.format(new Date(expires));
	}

}

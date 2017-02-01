package com.schibsted.webapp.server.helper;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class CookieHelper {

	private static final String COOKIE_SEPARATOR = ";";
	private static final String REQ_HEADER_COOKIE = "Cookie";
	private static final String RES_HEADER_COOKIE = "Set-Cookie";

	private static final String COOKIE_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";

	private static final Logger LOG = LogManager.getLogger(CookieHelper.class);
	
	private CookieHelper() {}

	/**
	 * Get cookie from request or response headers
	 * 
	 * @param ex
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpExchange ex, String cookieName) {
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
	public static String getCookie(List<String> cookieHeaders, String cookieName) {
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
	private static String parse(String c, String cookieName) {
		if (c == null)
			return null;
		for (String c2 : c.split(COOKIE_SEPARATOR)) {
			String cs = c2.trim();
			List<HttpCookie> lstHttpCookie = HttpCookie.parse(cs);
			for (HttpCookie cookie : lstHttpCookie) {
				LOG.trace(cookie);
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
	public static void setCookie(HttpExchange ex, String cookie, String value) {
		setCookie(ex, cookie, value, null, null);
	}

	public static synchronized void setCookie(HttpExchange ex, String cookie, String value, Boolean httpOnly, Long expires) {
		String cookieValue = cookie + "=" + value + COOKIE_SEPARATOR + " version=1";
		if (httpOnly != null)
			cookieValue += COOKIE_SEPARATOR + " HttpOnly";
		if (expires != null) {
			cookieValue += COOKIE_SEPARATOR + " expires=" + formatExpiresDate(expires);
		}
		ex.getResponseHeaders().add(RES_HEADER_COOKIE, cookieValue);
	}
	
	public static synchronized String formatExpiresDate(Long expires) {
		SimpleDateFormat sdfExpires = new SimpleDateFormat(COOKIE_DATE_FORMAT, Locale.US);
		sdfExpires.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdfExpires.format(new Date(expires));
	}

}

package com.schibsted.webapp.server.helper;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpServerHelper {
	
	private static final Logger LOG = LogManager.getLogger(HttpServerHelper.class);

	private HttpServerHelper() {}

	public static void redirect(HttpExchange ex, String path) throws IOException {
		ex.getResponseHeaders().add(HttpHeaders.LOCATION, path);
		ex.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, 0);
		ex.close();
	}

	public static void permissionDenied(HttpExchange ex) throws IOException {
		ex.sendResponseHeaders(HttpStatus.SC_FORBIDDEN, 0);
		ex.close();
	}

	public static boolean isRedirect(HttpExchange ex) {
		return ex.getResponseCode()==HttpStatus.SC_MOVED_TEMPORARILY;
	}

}

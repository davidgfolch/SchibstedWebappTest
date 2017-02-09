package com.schibsted.webapp.server.helper;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.inject.Singleton;

import com.google.common.net.HttpHeaders;
import com.sun.net.httpserver.HttpExchange;

@Singleton
@SuppressWarnings("restriction")
public class HttpServerHelper {

	public void redirect(HttpExchange ex, String path) throws IOException {
		ex.getResponseHeaders().add(HttpHeaders.LOCATION, path);
		ex.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_TEMP, 0);
		ex.close();
	}

	public void permissionDenied(HttpExchange ex) throws IOException {
		ex.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
		ex.close();
	}

	public boolean isRedirect(HttpExchange ex) {
		return ex.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP;
	}

}

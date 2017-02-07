package com.schibsted.webapp.server.helper;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class HttpServerHelper {
	
	public void redirect(HttpExchange ex, String path) throws IOException {
		ex.getResponseHeaders().add(HttpHeaders.LOCATION, path);
		ex.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, 0);
		ex.close();
	}

	public void permissionDenied(HttpExchange ex) throws IOException {
		ex.sendResponseHeaders(HttpStatus.SC_FORBIDDEN, 0);
		ex.close();
	}

	public boolean isRedirect(HttpExchange ex) {
		return ex.getResponseCode()==HttpStatus.SC_MOVED_TEMPORARILY;
	}

}

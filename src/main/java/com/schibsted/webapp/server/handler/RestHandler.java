package com.schibsted.webapp.server.handler;

import java.io.IOException;

import com.schibsted.webapp.server.ILogger;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class RestHandler extends BaseHandler implements ILogger {

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		logger().debug("NOT IMPLEMENTED!!!!!!!!!!!!!!!!!");
		writeResponseBody("NOT IMPLEMENTED!!!!!!!!!!!!!!!!!".getBytes());
	}

}

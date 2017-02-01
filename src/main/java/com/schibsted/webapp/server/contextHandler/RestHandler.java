package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class RestHandler extends BaseHandler {

	private static final Logger LOG = LogManager.getLogger(RestHandler.class);

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		LOG.debug("NOT IMPLEMENTED!!!!!!!!!!!!!!!!!");
		writeResponseBody("NOT IMPLEMENTED!!!!!!!!!!!!!!!!!".getBytes());
	}

}

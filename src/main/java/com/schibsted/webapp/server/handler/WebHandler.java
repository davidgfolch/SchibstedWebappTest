package com.schibsted.webapp.server.handler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.ViewModel;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class WebHandler extends MVCHandler {

	private static final Logger LOG = LogManager.getLogger(WebHandler.class);

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		LOG.debug(ex.getRequestMethod() + " " + ex.getProtocol() + " " + ex.getLocalAddress() + ex.getRequestURI());
		ViewModel model = execute(ex);
		setRedirect(HttpServerHelper.isRedirect(ex));
		if (!isRedirect()) {
			String res = getView(ex.getRequestURI(), model);
			ex.sendResponseHeaders(getStatusCode(ex), res.length());
			writeResponseBody(res.getBytes());
		}
	}

}

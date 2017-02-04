package com.schibsted.webapp.server.handler;

import java.io.IOException;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.ViewModel;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class WebHandler extends MVCHandler implements ILogger{


	public WebHandler(Config config, HttpExchangeHelper exchangeHelper) {
		super(config, exchangeHelper);
	}

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		logger().debug(ex.getRequestMethod() + " " + ex.getProtocol() + " " + ex.getLocalAddress() + ex.getRequestURI());
		ViewModel model = execute(ex);
		setRedirect(HttpServerHelper.isRedirect(ex));
		if (!isRedirect()) {
			String res = getView(ex.getRequestURI(), model);
			ex.sendResponseHeaders(getStatusCode(ex), res.length());
			writeResponseBody(res.getBytes());
		}
	}

}

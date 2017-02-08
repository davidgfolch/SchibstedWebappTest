package com.schibsted.webapp.server.handler;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.IOException;

import javax.inject.Named;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.ViewModel;
import com.sun.net.httpserver.HttpExchange;

@Named
@SuppressWarnings("restriction")
public class WebHandler extends MVCHandler implements ILogger {

	private final HttpServerHelper httpServerHelper=inject(HttpServerHelper.class);

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		logger().debug(
				ex.getRequestMethod() + " " + ex.getProtocol() + " " + ex.getLocalAddress() + ex.getRequestURI());
		ViewModel model = execute(ex);
		setRedirect(httpServerHelper.isRedirect(ex));
		if (!isRedirect()) {
			String res = getView(ex.getRequestURI(), model);
			ex.sendResponseHeaders(getStatusCode(ex), res.length());
			writeResponseBody(res.getBytes());
		}
	}

}

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

	private final HttpServerHelper httpServerHelper = inject(HttpServerHelper.class);

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		log(ex);
		ViewModel model = execute(ex);
		setRedirect(httpServerHelper.isRedirect(ex));
		if (isRedirect())
			return;
		writeResponseString(ex, model);
		/*writeResponseOutputStream(ex, model);*/
	}

	/*
	private void writeResponseOutputStream(HttpExchange ex, ViewModel model) throws IOException {
		getView(getOutputStream(), ex.getRequestURI(), model);
		ex.sendResponseHeaders(getStatusCode(ex), 0);
	}*/

	private void writeResponseString(HttpExchange ex, ViewModel model) throws IOException {
		String res = getView(ex.getRequestURI(), model);
		ex.sendResponseHeaders(getStatusCode(ex), res.length());
		writeResponseBody(res.getBytes());
	}

	private void log(HttpExchange ex) {
		if (logger().isDebugEnabled()) {
			String str = ex.getRequestMethod() + " " + ex.getProtocol() + " " + ex.getLocalAddress()
					+ ex.getRequestURI();
			logger().debug(str);
		}
	}

}

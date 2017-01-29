package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.ViewModel;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class WebContextHandler extends MVCHandler implements HttpHandler {

	private static final Logger LOG = LogManager.getLogger(WebContextHandler.class);

	@Override
	public void handle(HttpExchange ex) throws IOException {

		// todo: sun.net.httpserver.AuthFilter
		// ex.getRequestHeaders().set("Content-Type", "text/html");
		LOG.debug(ex.getRequestMethod() + " " + ex.getProtocol() + " " + ex.getLocalAddress() + ex.getRequestURI());

		final OutputStream os = ex.getResponseBody();
		boolean redirect=false;
		try {
			ViewModel model = execute(ex);
			redirect=HttpServerHelper.isRedirect(ex);
			if (!redirect) {
				String res = getView(ex.getRequestURI(), model);
				ex.sendResponseHeaders(HttpStatus.SC_OK, res.length());
				os.write(res.getBytes());
			}
		} catch (Exception e) {
			LOG.error("", e);
			ErrorHandler.handle(ex, e, os);
		} finally {
			if (!redirect) {
				os.flush();
				ex.close();
			}
		}
	}

}

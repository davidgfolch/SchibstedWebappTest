package com.schibsted.webapp.server.contextHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public abstract class BaseHandler implements HttpHandler {

	private static final Logger LOG = LogManager.getLogger(BaseHandler.class);

	private boolean redirect = false;
	private OutputStream os = null;

	@Override
	public void handle(HttpExchange ex) throws IOException {
		os = ex.getResponseBody();
		try {
			doHandle(ex);
		} catch (Exception e) {
			LOG.error("", e);
			handleException(ex, e, os);
			throw e;
		} finally {
			if (!isRedirect()) {
				os.flush();
				ex.close();
			}
		}

	}

	public abstract void doHandle(HttpExchange ex) throws IOException;

	public void handleException(HttpExchange ex, Throwable t, OutputStream os) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(bos));
		try {
			ex.sendResponseHeaders(HttpStatus.SC_INTERNAL_SERVER_ERROR, bos.size());
			os.write(bos.toByteArray());
		} catch (IOException e) {
			e.initCause(t);
			LOG.error("", e);
		}
	}

	public void writeResponseBody(byte[] bytes) throws IOException {
		os.write(bytes);
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

}

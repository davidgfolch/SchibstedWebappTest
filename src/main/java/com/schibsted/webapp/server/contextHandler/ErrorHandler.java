package com.schibsted.webapp.server.contextHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class ErrorHandler {

	private static final Logger LOG = LogManager.getLogger(ErrorHandler.class);

	public static void handle(HttpExchange ex, Throwable t, OutputStream os) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(bos));
		// ByteBuffer bb = ByteBuffer.wrap(bos.toByteArray());
		try {
			ex.sendResponseHeaders(HttpStatus.SC_INTERNAL_SERVER_ERROR, bos.size());
			os.write(bos.toByteArray());
		} catch (IOException e) {
			e.initCause(t);
			LOG.error("", e);
		}
	}

}

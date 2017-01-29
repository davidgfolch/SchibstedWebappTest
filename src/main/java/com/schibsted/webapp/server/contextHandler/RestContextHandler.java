package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class RestContextHandler implements HttpHandler {

	private static final Logger LOG = LogManager.getLogger(RestContextHandler.class);

	@Override
	public void handle(HttpExchange ex) throws IOException {
		String file = ex.getRequestMethod();
		final OutputStream os = ex.getResponseBody();

		try {
			Files.lines(Paths.get(file)).forEach(l -> {
				// String newLine=l.replaceAll("\\$\\{[a-zA-Z-]\\}");
				try {
					os.write(l.getBytes());
				} catch (IOException e) {
					// throw new RuntimeException(e);
					throw new UncheckedIOException(e);
				}
			});
		} catch (UncheckedIOException e) {
			LOG.error("", e);
		}
		os.flush();
		ex.close();
	}

}

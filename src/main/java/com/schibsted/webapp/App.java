package com.schibsted.webapp;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.exception.ConfigurationException;

class App {

	private static final Logger LOG = LogManager.getLogger(App.class);

	private App() {}

	public static void main(String[] args) {
		// Tell java logger to use log4j logger
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
		try {
			new Server().startServer();
		} catch (ConfigurationException | IOException e) {
			LOG.error("",e);
		}
	}

}

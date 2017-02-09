package com.schibsted.webapp;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Server;

/**
 * Entry point to execute server
 * @author slks
 */
final class App {

	/** 
	 * Logger
	 */
	private static final Logger LOG;
	static {
		// Tell java logger to use log4j logger
		System.setProperty("java.util.logging.manager","org.apache.logging.log4j.jul.LogManager");
		LOG = LogManager.getLogger(App.class);
	}

	private App() {
	}

	/**
	 * Entry execution point
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			new Server().startServer();
		} catch (IOException e) {
			LOG.error("", e);
		}
	}

}

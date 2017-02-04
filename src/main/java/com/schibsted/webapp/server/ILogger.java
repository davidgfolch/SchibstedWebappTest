package com.schibsted.webapp.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ILogger {

	default Logger logger() {
		return LogManager.getLogger(getClass());
	}
}

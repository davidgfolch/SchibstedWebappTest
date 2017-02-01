package com.schibsted.webapp.server.exception;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigurationException(String msg, Exception e) {
		super(msg,e);
	}

	public ConfigurationException(String msg) {
		super(msg);
	}


}

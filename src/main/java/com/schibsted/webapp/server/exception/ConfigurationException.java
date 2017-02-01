package com.schibsted.webapp.server.exception;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigurationException(String errMsg, Exception e) {
		super(errMsg,e);
	}


}

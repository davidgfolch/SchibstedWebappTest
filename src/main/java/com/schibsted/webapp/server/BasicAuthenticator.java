package com.schibsted.webapp.server;

@SuppressWarnings("restriction")
public class BasicAuthenticator extends com.sun.net.httpserver.BasicAuthenticator {

	public BasicAuthenticator(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		return username!=null && username.equals(password); //TODO: STUPID DUMMY AUTH :))
	}

}

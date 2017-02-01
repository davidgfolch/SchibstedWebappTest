package com.schibsted.webapp.server;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.helper.UserHelper;
import com.sun.net.httpserver.BasicAuthenticator;

@SuppressWarnings("restriction")
public class MyBasicAuthenticator extends BasicAuthenticator {

	public MyBasicAuthenticator(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		return UserHelper.checkCreadentials(InMemory.getUsers(),username,password)!=null;
	}

}

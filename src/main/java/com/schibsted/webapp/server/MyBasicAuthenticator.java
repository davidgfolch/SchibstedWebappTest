package com.schibsted.webapp.server;

import static com.schibsted.webapp.di.DIFactory.inject;

import com.schibsted.webapp.server.helper.UserHelper;
import com.sun.net.httpserver.BasicAuthenticator;

@SuppressWarnings("restriction")
public class MyBasicAuthenticator extends BasicAuthenticator {

	private UserHelper userHelper=inject(UserHelper.class);


	public MyBasicAuthenticator(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		return userHelper.checkCreadentials(username, password) != null;
	}

}

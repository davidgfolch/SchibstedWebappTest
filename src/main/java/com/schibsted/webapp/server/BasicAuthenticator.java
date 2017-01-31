package com.schibsted.webapp.server;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.User;

@Deprecated
@SuppressWarnings("restriction")
public class BasicAuthenticator extends com.sun.net.httpserver.BasicAuthenticator {

	public BasicAuthenticator(String realm) {
		super(realm);
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		User user=UserHelper.checkCreadentials(InMemory.getUsers(),username,password);
		return user!=null;
		//return username != null && username.equals(password); 
	}

}

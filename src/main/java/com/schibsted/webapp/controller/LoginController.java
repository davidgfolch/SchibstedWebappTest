package com.schibsted.webapp.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.model.User;

@ContextPath("/login")
public class LoginController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(LoginController.class);

	private static final List<User> users = Arrays.asList(new User[] { //
			new User("admin", "admin"),
			new User("user1", "user1"),
			new User("user2", "user2"),
			new User("user3", "user3")
			});

	@Override
	public void doLogic() {
		LOG.debug("Login controller method {}, params {}", getHttpMethod(), getParameters().size());
		//todo: Model in BaseController saves state between http calls
		setMessage(null);
		putInModel("user", getSession().getLoggedUser());
		if (isGet())
			return;
		for (User user : users) {
			if (user.getName().equals(getParameter("user.name")) && //
				user.getPassword().equals(getParameter("user.password"))) {
				getSession().setLoggedUser(user);
				setMessage("Logged in successfuly");
				putInModel("user", user);
				return;
			}
		}
		setMessage("Not a valid user!");
	}

}

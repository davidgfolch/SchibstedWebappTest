package com.schibsted.webapp.controller.web;

import java.util.List;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.User;

@ContextPath("/rest/user")
// @Authenticated(role = "PAGE_1")
public class UserRestController extends BaseRestController implements ILogger {

	@Override
	void get() {
		// User user=UserHelper.getUser(InMemory.getUsers(), (String)
		// getParameter("pathParam1"));
		// getModel().put("user",user);
		List<User> users = UserHelper.getUsers(InMemory.getUsers(), (String) getParameter("pathParam1"));
		getModel().put("users", users);
	}

}

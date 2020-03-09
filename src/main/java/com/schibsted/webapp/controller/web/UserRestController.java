package com.schibsted.webapp.controller.web;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.util.List;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.exception.DataException;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.User;

/**
 * Rest endpoint service for users:
 * /rest/user/{username} 
 * @author slks
 */
@ContextPath("/rest/user")
// @Authenticated(role = "PAGE_1")
public class UserRestController extends BaseRestController implements ILogger {
	
	private final UserHelper userHelper=inject(UserHelper.class);

	@Override
	void get() {
		List<User> users = userHelper.getUsers(getUserId());
		getModel().put("users", users);
	}

	@Override
	void delete() {
		try {
			userHelper.delete(getUserId());
		} catch (DataException e) {
			getModel().put("exception", e);
		}
	}

	private String getUserId() {
		return (String) getParameter("pathParam1");
	}
	
}

package com.schibsted.webapp.controller.web;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.helper.StringHelper;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.User;

@ContextPath("/login")
public class LoginController extends BaseWebController {

	public static final String MSG_LOGGED_IN_SUCCESSFULY = "Logged in successfuly";

	@Override
	public void doLogic() {
		logger().debug("Login controller method {}, params {}", getHttpMethod(), getParameters().size());
		// Model in BaseController saves state between http calls
		setMessage(null);
		getLoggedUser();
		if (isGet())
			return;
		doPost();
	}

	private void doPost() {
		String userName = (String) getParameter("user.name");
		String pass = (String) getParameter("user.password");
		String redirect = (String) getParameter("redirect");
		User user = UserHelper.checkCreadentials(InMemory.getUsers(), userName, pass);
		if (user != null) {
			getSession().setLoggedUser(user);
			setMessage(MSG_LOGGED_IN_SUCCESSFULY);
			if (StringHelper.isNotEmpty(redirect))
				sendRedirect(redirect);
			putInModel("user", user);
			return;
		}
		setMessage("Not a valid user!");
	}

	private void getLoggedUser() {
		User loggedUser = getSession().getLoggedUser();
		putInModel("user", loggedUser);
		if (loggedUser != null)
			setMessage("You are logged as " + loggedUser.getName());
	}

}

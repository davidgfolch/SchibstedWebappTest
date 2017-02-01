package com.schibsted.webapp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.User;

@ContextPath("/login")
public class LoginController extends BaseController {

	public static final String MSG_LOGGED_IN_SUCCESSFULY = "Logged in successfuly";
	private static final Logger LOG = LogManager.getLogger(LoginController.class);

	@Override
	public void doLogic() {
		LOG.debug("Login controller method {}, params {}", getHttpMethod(), getParameters().size());
		//todo: Model in BaseController saves state between http calls
		setMessage(null);
		getLoggedUser();
		if (isGet())
			return;
		doPost();
	}

	private void doPost() {
		String userName=(String)getParameter("user.name");
		String pass=(String)getParameter("user.password");
		String redirect=(String)getParameter("redirect");
		User user=UserHelper.checkCreadentials(InMemory.getUsers(),userName,pass);
		if (user!=null) {
			getSession().setLoggedUser(user);
			setMessage(MSG_LOGGED_IN_SUCCESSFULY);
			if (StringUtils.isNotEmpty(redirect))
				sendRedirect(redirect);
			putInModel("user", user);
			return;
		}
		setMessage("Not a valid user!");
	}

	private void getLoggedUser() {
		User loggedUser=getSession().getLoggedUser();
		putInModel("user", loggedUser);
		if (loggedUser!=null)
			setMessage("You are logged as "+loggedUser.getName());
	}
	
}

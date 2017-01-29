package com.schibsted.webapp.controller;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.model.User;

@ContextPath("/login")
public class LoginController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(LoginController.class);

	@Override
	public void doLogic() {
		LOG.debug("Login controller method {}, params {}", getHttpMethod(), getParameters().size());
		//todo: Model in BaseController saves state between http calls
		setMessage(null);
		User loggedUser=getSession().getLoggedUser();
		putInModel("user", loggedUser);
		if (loggedUser!=null)
			setMessage("You are logged as "+loggedUser.getName());
		if (isGet())
			return;
		for (User user : InMemory.getUsers()) {
			if (user.getName().equals(getParameter("user.name")) && //
				user.getPassword().equals(getParameter("user.password"))) {
				getSession().setLoggedUser(user);
				setMessage("Logged in successfuly");
				if (getParameter("redirect")!=null)
					sendRedirect((String)getParameter("redirect"));
				putInModel("user", user);
				return;
			}
		}
		setMessage("Not a valid user!");
	}
	
}

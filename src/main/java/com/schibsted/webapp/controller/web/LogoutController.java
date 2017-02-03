package com.schibsted.webapp.controller.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/logout")
public class LogoutController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(LogoutController.class);
	
	@Override
	public void doLogic() {
		LOG.debug("Logout controller method {}, params {}", getHttpMethod(), getParameters().size());
		invalidateSession();
		sendRedirect(config.get(Server.LOGIN_PATH));
	}

}

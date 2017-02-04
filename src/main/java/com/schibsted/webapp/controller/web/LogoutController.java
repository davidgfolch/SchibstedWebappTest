package com.schibsted.webapp.controller.web;


import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/logout")
public class LogoutController extends BaseController implements ILogger {

	@Override
	public void doLogic() {
		logger().debug("Logout controller method {}, params {}", getHttpMethod(), getParameters().size());
		invalidateSession();
		sendRedirect(config.get(Server.LOGIN_PATH));
	}

}

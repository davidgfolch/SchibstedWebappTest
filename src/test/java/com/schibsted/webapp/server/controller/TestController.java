package com.schibsted.webapp.server.controller;

import com.schibsted.webapp.controller.web.BaseWebController;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/test")
// @Authenticated(role="ADMIN")
public class TestController extends BaseWebController implements ILogger {

	@Override
	public void doLogic() {
		logger().debug("TestController method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

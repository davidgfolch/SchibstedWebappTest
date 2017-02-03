package com.schibsted.webapp.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.controller.web.BaseController;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/test")
// @Authenticated(role="ADMIN")
public class TestController extends BaseController {

	private static final Logger LOG = LogManager.getLogger(TestController.class);

	@Override
	public void doLogic() {
		LOG.debug("TestController method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

package com.schibsted.webapp.server.controller;

import com.schibsted.webapp.controller.web.BaseController;
import com.schibsted.webapp.server.ILogger;

public class TestController2 extends BaseController implements ILogger {

		@Override
		public void doLogic() {
			logger().debug("TestController2 method {}, params {}", getHttpMethod(), getParameters().size());
			putInModel("user", getSession().getLoggedUser());
		}

	}

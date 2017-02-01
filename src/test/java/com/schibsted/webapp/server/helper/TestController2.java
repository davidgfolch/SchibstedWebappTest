package com.schibsted.webapp.server.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.controller.web.BaseController;

public class TestController2 extends BaseController {

		private static final Logger LOG = LogManager.getLogger(TestController2.class);

		@Override
		public void doLogic() {
			LOG.debug("TestController2 method {}, params {}", getHttpMethod(), getParameters().size());
			putInModel("user", getSession().getLoggedUser());
		}

	}

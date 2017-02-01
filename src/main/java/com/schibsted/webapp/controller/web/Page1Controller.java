package com.schibsted.webapp.controller.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/page1")
@Authenticated(role="PAGE_1")
public class Page1Controller extends BaseController {

	private static final Logger LOG = LogManager.getLogger(Page1Controller.class);

	@Override
	public void doLogic() {
		LOG.debug("Page1Controller method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

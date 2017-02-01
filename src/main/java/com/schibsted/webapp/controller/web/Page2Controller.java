package com.schibsted.webapp.controller.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/page2")
@Authenticated(role="PAGE_2")
public class Page2Controller extends BaseController {

	private static final Logger LOG = LogManager.getLogger(Page2Controller.class);

	@Override
	public void doLogic() {
		LOG.debug("Page2Controller method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

package com.schibsted.webapp.controller.web;

import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/page1")
@Authenticated(role = "PAGE_1")
public class Page1Controller extends BaseWebController {

	@Override
	public void doLogic() {
		logger().debug("Page1Controller method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

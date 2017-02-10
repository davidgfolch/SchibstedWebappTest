package com.schibsted.webapp.controller.web;

import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/page2")
@Authenticated(role = "PAGE_2")
public class Page2Controller extends BaseWebController {

	@Override
	public void doLogic() {
		logger().debug("Page2Controller method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

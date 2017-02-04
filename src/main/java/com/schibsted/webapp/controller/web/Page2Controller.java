package com.schibsted.webapp.controller.web;


import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/page2")
@Authenticated(role="PAGE_2")
public class Page2Controller extends BaseController implements ILogger {

	@Override
	public void doLogic() {
		logger().debug("Page2Controller method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

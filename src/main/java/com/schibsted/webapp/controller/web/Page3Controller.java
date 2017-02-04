package com.schibsted.webapp.controller.web;



import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextPath;

@ContextPath("/page3")
@Authenticated(role="PAGE_3")
public class Page3Controller extends BaseController implements ILogger {

	@Override
	public void doLogic() {
		logger().debug("Page1Controller method {}, params {}", getHttpMethod(), getParameters().size());
		putInModel("user", getSession().getLoggedUser());
	}

}

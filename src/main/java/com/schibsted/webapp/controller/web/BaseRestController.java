package com.schibsted.webapp.controller.web;

import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;
import com.schibsted.webapp.server.helper.StringHelper;

@ContextHandler(value = "/", contextHandler = CONTEXT_HANDLER.REST_HANDLER)
public abstract class BaseRestController extends BaseController {
	
	private static final String SLASH = "/";

	@Override
	public void doLogic() {
		getPathParameters();
		logger().debug("Page1Controller method {}, params {}", getHttpMethod(), getParameters().size());
		switch (getHttpMethod()) {
		case "GET":
			get();
			break;
		case "POST":
			post();
			break;
		case "DELETE":
			delete();
			break;
		case "PUT":
			put();
			break;
		default:
			break;
		}
	}

	private void getPathParameters() {
		String extraPath= getRequestURI().getPath().replaceAll(getPath(), "");
		if (StringHelper.isEmpty(extraPath))
			return;
		if (extraPath.startsWith(SLASH))
			extraPath=extraPath.replaceFirst(SLASH, "");
		int index=1;
		for (String value: extraPath.split(SLASH))
			getParameters().put("pathParam"+index++, value);
	}

	void get() {
	}
	
	void put() {
	}

	void delete() {
	}

	void post() {
	}


}

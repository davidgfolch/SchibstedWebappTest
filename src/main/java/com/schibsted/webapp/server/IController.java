package com.schibsted.webapp.server;

import com.schibsted.webapp.server.model.Parameters;
import com.schibsted.webapp.server.model.ViewModel;

public interface IController {

	void setHttpMethod(String method);
	String getHttpMethod();
	boolean isGet();
	
	Parameters getParameters();
	void setParameters(Parameters parameters);
	Object getParameter(String string);
	
	void doLogic();
	ViewModel getModel();

	void checkPermisionDenied();
	void setStatusCode(int statusCode);
	int getStatusCode();
	
}

package com.schibsted.webapp.controller;

import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.contextHandler.WebContextHandler;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.model.Parameters;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.ViewModel;

@ContextHandler(value = "/", contextHandler = WebContextHandler.class)
public abstract class BaseController implements IController, IMVCController {

	private static final String ERROR_MSG = "errorMsg";
	private static final String MESSAGE_MSG = "messageMsg";
	private static final String GET = "GET";
	private ViewModel model = new ViewModel();
	private String httpMethod;
	private Parameters parameters;
	private Session session;
	private String redirect;

	@Override
	public String getHttpMethod() {
		return httpMethod;
	}
	
	@Override
	public void setHttpMethod(String method) {
		this.httpMethod=method;
	}
	
	@Override
	public Parameters getParameters() {
		return parameters;
	}
	@Override
	public Object getParameter(String string) {
		return getParameters().get(string);
	}
	
	@Override
	public void setParameters(Parameters params) {
		this.parameters=params;
	}
	
	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public void setSession(Session session) {
		this.session=session;
	}
	@Override
	public void invalidateSession() {
		SessionHelper.invalidateSession(session);
	}
	

	@Override
	public boolean isGet() {
		return GET.equals(httpMethod);
	}
	
	@Override
	public void setMessage(String msg) {
		putInModel(MESSAGE_MSG, msg);
	}
	@Override
	public void setError(String msg) {
		putInModel(ERROR_MSG, msg);
	}

	@Override
	public ViewModel putInModel(String name, Object obj) {
		model.put(name, obj);
		return model;
	}

	@Override
	public ViewModel getModel() {
		return model;
	}

	@Override
	public void sendRedirect(String redirect) {
		this.redirect=redirect;
	}
	@Override
	public String getRedirect() {
		return redirect;
	}

}

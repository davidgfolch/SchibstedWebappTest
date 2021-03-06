package com.schibsted.webapp.controller.web;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.net.HttpURLConnection;
import java.net.URI;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.model.Parameters;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.ViewModel;

//@ContextHandler(value = "/", contextHandler = WebHandler.class)
public abstract class BaseController implements IController, IMVCController, ILogger {

	private static final String ERROR_MSG = "errorMsg";
	private static final String MESSAGE_MSG = "messageMsg";
	private static final String GET = "GET";

	protected Config config=inject(Config.class);
	private SessionHelper sessionHelper=inject(SessionHelper.class);
	private final ViewModel model = new ViewModel();
	private String httpMethod;
	private Parameters parameters;
	private Session session;
	private String redirect;
	private int statusCode = HttpURLConnection.HTTP_OK;
	private String path;
	private URI requestURI;

	@Override
	public String getHttpMethod() {
		return httpMethod;
	}

	@Override
	public void setHttpMethod(String method) {
		this.httpMethod = method;
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
		this.parameters = params;
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void invalidateSession() {
		sessionHelper.invalidateSession(session);
	}

	@Override
	public void checkPermisionDenied() {
		if ((boolean) getSession().get("permDenied"))
			setStatusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
	}

	@Override
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public int getStatusCode() {
		return this.statusCode;
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
		this.redirect = redirect;
	}

	@Override
	public String getRedirect() {
		return redirect;
	}

	@Override
	public void setPath(String path) {
		this.path=path;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public void setRequestURI(URI requestURI) {
		this.requestURI=requestURI;
	}

	@Override
	public URI getRequestURI() {
		return requestURI;
	}

}

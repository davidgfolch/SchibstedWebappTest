package com.schibsted.webapp.server.handler;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.Parameters;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.ViewModel;
import com.schibsted.webapp.server.template.ITemplateRenderer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public abstract class MVCHandler extends BaseHandler implements ILogger {
	
	private static final String HEADER_CONTENT_TYPE = "Content-Type";

	public enum CONTENT_TYPE {
		JSON("application/json");

	    private final String value;

	    private CONTENT_TYPE(final String value) {
	        this.value = value;
	    }

	    @Override
	    public String toString() {
	        return value;
	    }		
	}

	private final HttpExchangeHelper exchangeHelper=inject(HttpExchangeHelper.class);
	private final HttpServerHelper httpServerHelper=inject(HttpServerHelper.class);
	private static List<IController> controllers = new ArrayList<>();

	public String getView(URI uri, ViewModel model, ITemplateRenderer templateRenderer) throws IOException {
		return templateRenderer.render(uri, model);
	}

	public void getView(OutputStream os, URI uri, ViewModel model, ITemplateRenderer templateRenderer) throws IOException {
		templateRenderer.render(os, uri, model);
	}

	public ViewModel execute(HttpExchange ex) {
		IController ctrl = getController(ex);
		IMVCController mvcCtrl = getMVCController(ex);
		ctrl.doLogic();
		checkRedirect(ex, mvcCtrl);
		return ctrl.getModel();
	}

	private IController getController(HttpExchange ex) {
		HttpContext ctx = ex.getHttpContext();
		IController ctrl = (IController) ctx.getAttributes().get(Config.CONTROLLER);
		ctrl.setHttpMethod(ex.getRequestMethod());
		ctrl.setParameters((Parameters) ctx.getAttributes().get(ParamsFilter.PARAMETERS));
		ctrl.setPath(ctx.getPath());
		ctrl.setRequestURI(ex.getRequestURI());
		return ctrl;
	}

	private IMVCController getMVCController(HttpExchange ex) {
		HttpContext ctx = ex.getHttpContext();
		IMVCController mvcCtrl = (IMVCController) ctx.getAttributes().get(Config.CONTROLLER);
		Session session = exchangeHelper.getSession(ex);
		mvcCtrl.setSession(session);
		return mvcCtrl;
	}

	public static List<IController> getControllers() {
		return MVCHandler.controllers;
	}

	public static void setControllers(List<IController> webControllers) {
		MVCHandler.controllers = webControllers;
	}

	protected int getStatusCode(HttpExchange ex) {
		IController ctrl = getController(ex);
		return ctrl.getStatusCode();
	}
	
	protected void setContentType(HttpExchange ex, CONTENT_TYPE contentType) {
		Headers headers = ex.getResponseHeaders();
		headers.add(HEADER_CONTENT_TYPE, contentType.toString());
	}

	protected void writeResponseString(HttpExchange ex, ViewModel model, ITemplateRenderer viewRenderer) throws IOException {
		String res = getView(ex.getRequestURI(), model, viewRenderer);
		ex.sendResponseHeaders(getStatusCode(ex), res.length());
		writeResponseBody(res.getBytes());
	}
	
	/*
	protected void writeResponseOutputStream(HttpExchange ex, ViewModel model) throws IOException {
		getView(getOutputStream(), ex.getRequestURI(), model);
		ex.sendResponseHeaders(getStatusCode(ex), 0);
	}*/
	

	private void checkRedirect(HttpExchange ex, IMVCController mvcCtrl) {
		if (mvcCtrl.getRedirect() == null)
			return;
		try {
			httpServerHelper.redirect(ex, mvcCtrl.getRedirect());
			mvcCtrl.sendRedirect(null);
		} catch (IOException e) {
			logger().error("Cannot redirect", e);
		}
	}

}

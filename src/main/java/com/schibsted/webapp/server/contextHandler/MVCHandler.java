package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.Parameters;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.ViewModel;
import com.schibsted.webapp.server.template.ITemplateRenderer;
import com.schibsted.webapp.server.template.JTwigTemplateRenderer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public abstract class MVCHandler extends BaseHandler {

	private static final Logger LOG = LogManager.getLogger(MVCHandler.class);

	private static final Config config;
	static {
		config = Server.getConfig();
	}

	private static final String EXT = config.get("templates.extension");
	private static final String TEMPLATES = config.get("templates.folder");
	
	private static final ITemplateRenderer templateRenderer = new JTwigTemplateRenderer(TEMPLATES,EXT);

	private static List<Object> webControllers = new ArrayList<>();

	public synchronized String getView(URI uri, ViewModel model) {
		return templateRenderer.render(uri,model);
	}

	public synchronized ViewModel execute(HttpExchange ex) {
		IController ctrl =getController(ex);
		IMVCController mvcCtrl = getMVCController(ex);
		ctrl.doLogic();
		checkRedirect(ex,mvcCtrl);
		return ctrl.getModel();
	}
	
	private IController getController(HttpExchange ex) {
		HttpContext ctx = ex.getHttpContext();
		IController ctrl = (IController) ctx.getAttributes().get(Config.CONTROLLER);
		ctrl.setHttpMethod(ex.getRequestMethod());
		ctrl.setParameters((Parameters) ctx.getAttributes().get(ParamsFilter.PARAMETERS));
		return ctrl; 
	}
	
	private IMVCController getMVCController(HttpExchange ex) {
		HttpContext ctx = ex.getHttpContext();
		IMVCController mvcCtrl = (IMVCController) ctx.getAttributes().get(Config.CONTROLLER);
		Session session=HttpExchangeHelper.getSession(ex);
		mvcCtrl.setSession(session);
		return mvcCtrl;
		
	}

	public static List<Object> getWebControllers() {
		return MVCHandler.webControllers;
	}

	public static void setWebControllers(List<Object> webControllers) {
		MVCHandler.webControllers = webControllers;
	}
	
	protected int getStatusCode(HttpExchange ex) {
		IController ctrl =getController(ex);
		return ctrl.getStatusCode();
	}

	private void checkRedirect(HttpExchange ex, IMVCController mvcCtrl) {
		if (mvcCtrl.getRedirect() == null)
			return;
		try {
			HttpServerHelper.redirect(ex, mvcCtrl.getRedirect());
			mvcCtrl.sendRedirect(null);
		} catch (IOException e) {
			LOG.error("Cannot redirect", e);
		}
	}
	
}

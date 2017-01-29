package com.schibsted.webapp.server.contextHandler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.model.Parameters;
import com.schibsted.webapp.server.model.ViewModel;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class MVCHandler {

	private static final Logger LOG = LogManager.getLogger(MVCHandler.class);

	private static final Config config;
	static {
		config = Server.getConfig();
	}

	private static final String CONTROLLER = "controller";
	private static final String EXT = config.get("templates.extension");
	private static final String TEMPLATES = config.get("templates.folder");

	private static List<Object> webControllers = new ArrayList<>();

	public synchronized String getView(URI uri, ViewModel model) {
		String templatePath = TEMPLATES + uri.getPath() + EXT;
		// todo: move JTwig outside here
		JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
		JtwigModel wigModel = JtwigModel.newModel();
		for (Entry<String, Object> entry : model.entrySet()) {
			wigModel.with(entry.getKey(), entry.getValue());
		}
		return template.render(wigModel);
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
		IController ctrl = (IController) ctx.getAttributes().get(CONTROLLER);
		ctrl.setHttpMethod(ex.getRequestMethod());
		ctrl.setParameters((Parameters) ctx.getAttributes().get(ParamsFilter.PARAMETERS));
		return ctrl; 
	}
	
	private IMVCController getMVCController(HttpExchange ex) {
		HttpContext ctx = ex.getHttpContext();
		IMVCController mvcCtrl = (IMVCController) ctx.getAttributes().get(CONTROLLER);
		mvcCtrl.setSession(SessionHelper.getSession(ex));
		return mvcCtrl;
		
	}

	public static List<Object> getWebControllers() {
		return MVCHandler.webControllers;
	}

	public static void setWebControllers(List<Object> webControllers) {
		MVCHandler.webControllers = webControllers;
	}

	private void checkRedirect(HttpExchange ex, IMVCController mvcCtrl) {
		if (mvcCtrl.getRedirect() == null)
			return;
		try {
			HttpServerHelper.redirect(ex, mvcCtrl.getRedirect());
		} catch (IOException e) {
			LOG.error("Cannot redirect", e);
		}
	}

}

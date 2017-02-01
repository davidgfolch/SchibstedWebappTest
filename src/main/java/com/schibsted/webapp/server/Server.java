package com.schibsted.webapp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.exception.ConfigurationException;
import com.schibsted.webapp.server.filter.AuthFilter;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.MVCHandler;
import com.schibsted.webapp.server.handler.WebHandler;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class Server {

	public static final String LOGIN_PATH = "login.path";
	
	private static final Logger LOG = LogManager.getLogger(Server.class);
	private static final String PORT = "port";

	private static Config config;
	static {
		try {
			config = new Config(Server.class);
		} catch (ConfigurationException e) {
			LOG.error("Configuration error, cannot start server...", e);
		}
	}

	private HttpServer serverInstance;

	private ParamsFilter paramsFilter = new ParamsFilter();
	private AuthFilter authFilter;

	public boolean startServer() {

		try {
			LOG.info("Starting server on port " + config.get(PORT));
			serverInstance = HttpServer.create(new InetSocketAddress(config.getInt(PORT)), 0);
		} catch (IOException e) {
			LOG.error("Cannot start server...", e);
			return false;
		}

		authFilter = new AuthFilter(config.get(LOGIN_PATH));
		// see https://code.google.com/archive/p/reflections/
		Reflections reflections = new Reflections("com.schibsted.webapp.controller");
		Set<Class<?>> webControllersClaz = reflections.getTypesAnnotatedWith(ContextHandler.class);
		webControllersClaz.forEach(this::addWebController);
		serverInstance.start();
		return true;
	}


	private void addWebController(Class<?> claz) {
		try {
			if (!ReflectionHelper.isControllerCandidate(claz))
				return;
			LOG.debug("Adding controller: {}", claz.getName());
			IController obj = (IController)claz.newInstance();
			setWebHandlers(obj);
			MVCHandler.getWebControllers().add(obj);
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error("", e);
		}
	}
	
	private void setWebHandlers(IController ctrl) {
		String contextPath= ReflectionHelper.getContextPath(ctrl.getClass());
		String handler = config.get("contextHandler." + contextPath,WebHandler.class.getSimpleName());
		HttpContext ctx = registerHandler(contextPath,HandlerFactory.get(handler));
		setHandlerController(ctx,ctrl);
	}
	
	private void setHandlerFilters(HttpContext ctx, IController ctrl) {
		List<Filter> filters = ctx.getFilters();
		if (ctrl.getClass().getAnnotation(Authenticated.class)!=null)
			filters.add(authFilter);
		filters.add(paramsFilter);
	}


	public void setHandlerController(HttpContext ctx, IController ctrl) {
		ctx.getAttributes().put(Config.CONTROLLER, ctrl);
		setHandlerFilters(ctx,ctrl);
	}
	
	public HttpContext registerHandler(String contextPath, HttpHandler handler) {
		HttpContext ctx = serverInstance.createContext(contextPath);
		ctx.setHandler(handler);
		return ctx;
	}

	public static Config getConfig() {
		return Server.config;
	}

}

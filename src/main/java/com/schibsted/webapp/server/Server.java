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
import com.schibsted.webapp.server.annotation.ContextPath;
import com.schibsted.webapp.server.contextHandler.ContextHandlerFactory;
import com.schibsted.webapp.server.contextHandler.MVCHandler;
import com.schibsted.webapp.server.contextHandler.WebContextHandler;
import com.schibsted.webapp.server.filter.AuthFilter;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
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
		} catch (IOException e) {
			LOG.error("Configuration error, cannot start server...", e);
		}
	}

	private HttpServer serverInstance;

	private ParamsFilter paramsFilter = new ParamsFilter();
	private AuthFilter authFilter;

	public Server startServer() {

		try {
			LOG.info("Starting server on port " + config.get(PORT));
			serverInstance = HttpServer.create(new InetSocketAddress(config.getInt(PORT)), 0);
		} catch (IOException e) {
			LOG.error("Cannot start server...", e);
			return this;
		}

		/**
		 * Authenticator.setDefault(new BasicAuthenticator(""));
		 * server.setExecutor(executor);
		 */
		authFilter = new AuthFilter(config.get(LOGIN_PATH));
		// see https://code.google.com/archive/p/reflections/
		Reflections reflections = new Reflections("com.schibsted.webapp");
		/**
		 * Set<Class<?>> authenticatedServices =
		 * reflections.getTypesAnnotatedWith(Authenticated.class);
		 */
		Set<Class<?>> webControllersClaz = reflections.getTypesAnnotatedWith(ContextHandler.class);

		webControllersClaz.forEach(this::addWebControllers);

		// for (String contextPath : config.getList("contexts")) {
		// HttpContext ctx = server.createContext(contextPath);
		// String cxtHandlers = config.get("contextHandler." + contextPath);
		// ctx.setHandler(ContextHandlerFactory.get(cxtHandlers));
		// List<Filter> filters = ctx.getFilters();
		// filters.add(new AuthFilter(config.get("login.path")));
		// filters.add(new ParamsFilter());
		// // ctx.setAuthenticator(new BasicAuthenticator(contextPath));
		// }
		serverInstance.start();
		return this;
	}

	private void addWebControllers(Class<?> claz) {
		try {
			if (!ReflectionHelper.isControllerCandidate(claz))
				return;
			LOG.debug("Adding controller: {}", claz.getName());
			Object obj = claz.newInstance();
			setWebHandlers(obj);
			MVCHandler.getWebControllers().add(obj);
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.error("", e);
		}
	}

	private void setWebHandlers(Object ctrl) {
		Class<?> claz = ctrl.getClass();
		ContextPath ch = claz.getAnnotation(ContextPath.class);
		if (ch == null)
			ch = claz.getAnnotatedSuperclass().getAnnotation(ContextPath.class);
		String contextPath = ch.value();
		HttpContext ctx = serverInstance.createContext(contextPath);
		ctx.getAttributes().put(Config.CONTROLLER, ctrl);
		String cxtHandlers = config.get("contextHandler." + contextPath);
		cxtHandlers = cxtHandlers == null ? WebContextHandler.class.getSimpleName() : cxtHandlers;
		ctx.setHandler(ContextHandlerFactory.get(cxtHandlers));
		List<Filter> filters = ctx.getFilters();
		if (claz.getAnnotation(Authenticated.class)!=null)
			filters.add(authFilter);
		filters.add(paramsFilter);
	}

	public static Config getConfig() {
		return Server.config;
	}

}

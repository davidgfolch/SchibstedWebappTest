package com.schibsted.webapp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.annotation.ContextHandler;
import com.schibsted.webapp.server.exception.ConfigurationException;
import com.schibsted.webapp.server.filter.AuthFilter;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.MVCHandler;
import com.schibsted.webapp.server.handler.WebHandler;
import com.schibsted.webapp.server.helper.CookieHelper;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.injector.IConfigInjector;
import com.schibsted.webapp.server.injector.ISessionHelperInjector;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class Server implements ILogger {

	public static final String LOGIN_PATH = "login.path";
	private static final String PORT = "port";

	private final HandlerFactory handlerFactory;
	private final Config config;
	private final SessionHelper sessionHelper;
	private final ParameterHelper parameterHelper=new ParameterHelper();
	private final CookieHelper cookieHelper = new CookieHelper();
	private final HttpServerHelper httpServerHelper=new HttpServerHelper();

	private HttpServer serverInstance;

	private final ReflectionHelper reflectionHelper= new ReflectionHelper();
	private final ParamsFilter paramsFilter = new ParamsFilter();
	private final AuthFilter authFilter;
	private final HttpExchangeHelper exchangeHelper;
	private final HttpExchangeHelper httpExchangeHelper;
	

	public Server() throws ConfigurationException {
		config = Config.getConfig(Server.class);
		sessionHelper = new SessionHelper(config);
		exchangeHelper = new HttpExchangeHelper(sessionHelper, cookieHelper);
		httpExchangeHelper=new HttpExchangeHelper(sessionHelper, cookieHelper);
		authFilter = new AuthFilter(httpExchangeHelper,reflectionHelper,parameterHelper,httpServerHelper,config.get(LOGIN_PATH));
		this.handlerFactory=new HandlerFactory(config, exchangeHelper, httpServerHelper);
	}

	public void startServer() throws IOException {
		try {
			logger().info("Starting server on port " + config.get(PORT));
			serverInstance = HttpServer.create(new InetSocketAddress(config.getInt(PORT)), 0);
		} catch (IOException e) {
			logger().error("Cannot start server...", e);
			throw e;
		}
		// see https://code.google.com/archive/p/reflections/
		Reflections reflections = new Reflections("com.schibsted.webapp.controller");
		Set<Class<?>> webControllersClaz = reflections.getTypesAnnotatedWith(ContextHandler.class);
		webControllersClaz.forEach(this::addWebController);
		serverInstance.start();
	}

	private void addWebController(Class<?> claz) {
		try {
			if (!reflectionHelper.isControllerCandidate(claz))
				return;
			logger().debug("Adding controller: {}", claz.getName());
			IController obj = (IController) claz.newInstance();
			setWebHandlers(obj);
			MVCHandler.getWebControllers().add(obj);
		} catch (InstantiationException | IllegalAccessException e) {
			logger().error("", e);
		}
	}

	private void setWebHandlers(IController ctrl) {
		String contextPath = reflectionHelper.getContextPath(ctrl.getClass());
		String handler = config.get("contextHandler." + contextPath, WebHandler.class.getSimpleName());
		HttpContext ctx = registerHandler(contextPath, handlerFactory.get(handler));
		setHandlerController(ctx, ctrl);
	}

	private void setHandlerFilters(HttpContext ctx, IController ctrl) {
		List<Filter> filters = ctx.getFilters();
		if (ctrl.getClass().getAnnotation(Authenticated.class) != null)
			filters.add(authFilter);
		filters.add(paramsFilter);
	}

	public void setHandlerController(HttpContext ctx, IController ctrl) {
		ctx.getAttributes().put(Config.CONTROLLER, ctrl);
		if (ctrl instanceof ISessionHelperInjector)
			((ISessionHelperInjector)ctrl).injectSessionHelper(sessionHelper);
		if (ctrl instanceof IConfigInjector)
			((IConfigInjector)ctrl).injectConfig(config);
		setHandlerFilters(ctx, ctrl);
	}

	public HttpContext registerHandler(String contextPath, HttpHandler handler) {
		HttpContext ctx = serverInstance.createContext(contextPath);
		ctx.setHandler(handler);
		return ctx;
	}

	 public Config getConfig() {
		 return config;
	 }

}

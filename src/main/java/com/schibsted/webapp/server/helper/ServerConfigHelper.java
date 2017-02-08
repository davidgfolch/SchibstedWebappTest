package com.schibsted.webapp.server.helper;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.util.List;

import javax.inject.Singleton;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.filter.AuthFilter;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.schibsted.webapp.server.handler.HandlerFactory;
import com.schibsted.webapp.server.handler.MVCHandler;
import com.schibsted.webapp.server.handler.WebHandler;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;

@Singleton
@SuppressWarnings("restriction")
public class ServerConfigHelper implements ILogger {

	
	private final Config config = inject(Config.class);

	private final HandlerFactory handlerFactory = inject(HandlerFactory.class);
	private final ReflectionHelper reflectionHelper = inject(ReflectionHelper.class);
	private final ParamsFilter paramsFilter = inject(ParamsFilter.class);
	private final AuthFilter authFilter = inject(AuthFilter.class);
	
	public void addWebController(Server server, Class<?> claz) {
		try {
			if (!reflectionHelper.isControllerCandidate(claz))
				return;
			logger().debug("Adding controller: {}", claz.getName());
			IController obj = (IController) claz.newInstance();
			setWebHandlers(server,obj);
			MVCHandler.getWebControllers().add(obj);
		} catch (InstantiationException | IllegalAccessException e) {
			logger().error("", e);
		}
	}

	public void setWebHandlers(Server server, IController ctrl) {
		String contextPath = reflectionHelper.getContextPath(ctrl.getClass());
		String handler = config.get("contextHandler." + contextPath, WebHandler.class.getSimpleName());
		HttpContext ctx = server.registerHandler(contextPath, handlerFactory.get(handler));
		setHandlerController(ctx, ctrl);
	}

	public void setHandlerFilters(HttpContext ctx, IController ctrl) {
		List<Filter> filters = ctx.getFilters();
		if (ctrl.getClass().getAnnotation(Authenticated.class) != null)
			filters.add(authFilter);
		filters.add(paramsFilter);
	}
	
	public void setHandlerController(HttpContext ctx, IController ctrl) {
		ctx.getAttributes().put(Config.CONTROLLER, ctrl);
		setHandlerFilters(ctx, ctrl);
	}

}

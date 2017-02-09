package com.schibsted.webapp.server.helper;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.util.List;

import javax.inject.Singleton;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.IController;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.annotation.Authenticated;
import com.schibsted.webapp.server.filter.AuthFilter;
import com.schibsted.webapp.server.filter.ParamsFilter;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;

import rx.Observable;

@Singleton
@SuppressWarnings("restriction")
public class ServerConfigHelper implements ILogger {

	private final ReflectionHelper reflectionHelper = inject(ReflectionHelper.class);
	private final ParamsFilter paramsFilter = inject(ParamsFilter.class);
	private final AuthFilter authFilter = inject(AuthFilter.class);

	public Observable<IController> addWebController(Class<?> claz) {
		return createControllerInstance(claz);
	}

	private Observable<IController> createControllerInstance(Class<?> claz) {
		Observable<IController> res = Observable.empty();
		if (reflectionHelper.isControllerCandidate(claz)) {
			logger().debug("Adding controller: {}", claz.getName());
			try {
				res = Observable.just((IController) claz.newInstance()).share();
			} catch (InstantiationException | IllegalAccessException e) {
				logger().error("", e);
				
			}
		}
		return res;
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

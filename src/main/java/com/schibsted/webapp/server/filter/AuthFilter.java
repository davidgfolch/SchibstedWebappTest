package com.schibsted.webapp.server.filter;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.IOException;

import javax.inject.Named;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.IMVCController;
import com.schibsted.webapp.server.Server;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.User;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@Named
//@Singleton
@SuppressWarnings("restriction")
public class AuthFilter extends Filter implements ILogger {

	private final Config config = inject(Config.class);
	private final String loginPath = config.get(Server.LOGIN_PATH);
	private final ParameterHelper parameterHelper = inject(ParameterHelper.class);
	private final HttpExchangeHelper httpExchangeHelper = inject(HttpExchangeHelper.class);
	private final ReflectionHelper reflectionHelper = inject(ReflectionHelper.class);
	private final HttpServerHelper httpServerHelper = inject(HttpServerHelper.class);
	private final UserHelper userHelper=inject(UserHelper.class);

	

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		if (mustDoLogin(ex)) {
			String finalPath = parameterHelper.setUriParameter(loginPath, "redirect", ex.getHttpContext().getPath());
			logger().debug("Redirecting to login: {}", finalPath);
			httpServerHelper.redirect(ex, finalPath);
			return;
		}
		boolean permissionDenied = permissionDenied(ex);
		if (permissionDenied) {
			Session s = httpExchangeHelper.getSession(ex);
			logger().debug("Permission denied for user {} in path {}", s.getLoggedUser().getName(),
					ex.getHttpContext().getPath());
			s.put("permDenied", permissionDenied);
			httpServerHelper.permissionDenied(ex);
			return;
		}
		chain.doFilter(ex);
	}

	@Override
	public String description() {
		return "Ensures all defined paths are authenticated";
	}

	private boolean mustDoLogin(HttpExchange ex) {
		boolean isLoginPage = ex.getRequestURI() != null && ex.getRequestURI().toString().startsWith(loginPath);
		return !httpExchangeHelper.isAuthenticated(ex) && !isLoginPage;
	}

	private boolean permissionDenied(HttpExchange ex) {
		IMVCController ctrl=httpExchangeHelper.getController(ex);
		String roleRequiredInController = reflectionHelper.getAuthenticationRoles(ctrl);
		User user = httpExchangeHelper.getSession(ex).getLoggedUser();
		return !userHelper.hasUserRole(user, roleRequiredInController, InMemory.ROLE_ADMIN);
	}

}

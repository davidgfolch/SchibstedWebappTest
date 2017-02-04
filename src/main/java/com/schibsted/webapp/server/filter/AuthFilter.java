package com.schibsted.webapp.server.filter;
import java.io.IOException;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.User;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class AuthFilter extends Filter implements ILogger {
	
	private final ParameterHelper parameterHelper;
	private final HttpExchangeHelper httpExchangeHelper;
	private final String loginPath;

	public AuthFilter(HttpExchangeHelper httpExchangeHelper, ParameterHelper parameterHelper, String loginPath) {
		this.loginPath = loginPath;
		this.httpExchangeHelper=httpExchangeHelper;
		this.parameterHelper=parameterHelper;
	}

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		if (mustDoLogin(ex)) {
			String finalPath = parameterHelper.setUriParameter(loginPath, "redirect", ex.getHttpContext().getPath());
			logger().debug("Redirecting to login: {}", finalPath);
			HttpServerHelper.redirect(ex, finalPath);
			return;
		}
		boolean permissionDenied=permissionDenied(ex);
		if (permissionDenied) {
			Session s=httpExchangeHelper.getSession(ex);
			logger().debug("Permission denied for user {} in path {}", s.getLoggedUser().getName(),ex.getHttpContext().getPath());
			s.put("permDenied", permissionDenied);
			HttpServerHelper.permissionDenied(ex);
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
		String roleRequiredInController = ReflectionHelper.getAuthenticationRoles(ex);
		User user = httpExchangeHelper.getSession(ex).getLoggedUser();
		return !UserHelper.hasUserRole(user, roleRequiredInController, InMemory.ROLE_ADMIN);
	}

}

package com.schibsted.webapp.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.persistence.InMemory;
import com.schibsted.webapp.server.helper.HttpExchangeHelper;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ParameterHelper;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.Session;
import com.schibsted.webapp.server.model.User;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class AuthFilter extends Filter {
	
	// todo: use sun.net.httpserver.AuthFilter??

	private static final Logger LOG = LogManager.getLogger(AuthFilter.class);

	private String loginPath = "/login";

	public AuthFilter(String loginPath) {
		this.loginPath = loginPath;
	}

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		if (mustDoLogin(ex)) {
			String finalPath = ParameterHelper.setUriParameter(loginPath, "redirect", ex.getHttpContext().getPath());
			LOG.debug("Redirecting to login: {}", finalPath);
			HttpServerHelper.redirect(ex, finalPath);
			return;
		}
		boolean permissionDenied=permissionDenied(ex);
		if (permissionDenied) {
			Session s=HttpExchangeHelper.getSession(ex);
			LOG.debug("Permission denied for user {} in path {}", s.getLoggedUser().getName(),ex.getHttpContext().getPath());
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
		return !HttpExchangeHelper.isAuthenticated(ex) && !isLoginPage;
	}

	private boolean permissionDenied(HttpExchange ex) {
		String roleRequiredInController = ReflectionHelper.getAuthenticationRoles(ex);
		User user = HttpExchangeHelper.getSession(ex).getLoggedUser();
		return !UserHelper.hasUserRole(user, roleRequiredInController, InMemory.ROLE_ADMIN);
	}

}

package com.schibsted.webapp.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.helper.ReflectionHelper;
import com.schibsted.webapp.server.helper.SessionHelper;
import com.schibsted.webapp.server.helper.UserHelper;
import com.schibsted.webapp.server.model.User;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class AuthFilter extends Filter {

	private static final Logger LOG = LogManager.getLogger(AuthFilter.class);

	private String loginPath = "/login";

	public AuthFilter(String loginPath) {
		this.loginPath = loginPath;
	}

	@Override
	public void doFilter(HttpExchange ex, Chain chain) throws IOException {
		if (mustDoLogin(ex)) {
			String finalPath = HttpServerHelper.setUriParameter(loginPath, "redirect", ex.getHttpContext().getPath());
			LOG.debug("Redirecting to login: {}", finalPath);
			HttpServerHelper.redirect(ex, finalPath);
			return;
		}
		boolean permissionDenied=permissionDenied(ex);
		if (permissionDenied) {
			LOG.debug("Permission denied for user {} in path {}", SessionHelper.getSession(ex).getLoggedUser().getName(),ex.getHttpContext().getPath());
			SessionHelper.getSession(ex).put("permDenied", permissionDenied);
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
		return !SessionHelper.isAuthenticated(ex) && !isLoginPage;
	}

	private boolean permissionDenied(HttpExchange ex) {
		String roleRequired = ReflectionHelper.getAuthenticationRoles(ex);
		User user = SessionHelper.getSession(ex).getLoggedUser();
		return UserHelper.hasUserRole(user, roleRequired);
	}

}

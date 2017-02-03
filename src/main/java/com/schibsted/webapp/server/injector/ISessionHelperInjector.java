package com.schibsted.webapp.server.injector;

import com.schibsted.webapp.server.helper.SessionHelper;

@FunctionalInterface
public interface ISessionHelperInjector {

	void injectSessionHelper(SessionHelper sessionHelper);

}

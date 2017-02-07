package com.schibsted.webapp.server.injector;

import com.schibsted.webapp.server.Config;

@FunctionalInterface
public interface IConfigInjector {

	void injectConfig(Config config);

}

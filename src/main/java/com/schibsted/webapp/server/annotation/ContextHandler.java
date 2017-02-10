package com.schibsted.webapp.server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.schibsted.webapp.server.handler.HandlerFactory.CONTEXT_HANDLER;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextHandler {
	String value(); // path
	CONTEXT_HANDLER contextHandler();
}

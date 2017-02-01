package com.schibsted.webapp.server.template;

import java.net.URI;

import com.schibsted.webapp.server.model.ViewModel;

@FunctionalInterface
public interface ITemplateRenderer {

	String render(URI uri, ViewModel model);

}
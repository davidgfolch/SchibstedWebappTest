package com.schibsted.webapp.server.template;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.schibsted.webapp.server.model.ViewModel;

public interface ITemplateRenderer {

	String render(URI uri, ViewModel model) throws IOException;

	void render(OutputStream os, URI uri, ViewModel model) throws IOException;

}
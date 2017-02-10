package com.schibsted.webapp.server.template;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.model.ViewModel;

public class JsonRenderer implements ITemplateRenderer, ILogger {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String render(URI uri, ViewModel model) throws IOException {
		return objectMapper.writeValueAsString(model);
	}

	@Override
	public void render(OutputStream os, URI uri, ViewModel model) throws IOException {
		objectMapper.writeValue(os, model);
	}

}

package com.schibsted.webapp.server.handler;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.IOException;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.model.ViewModel;
import com.schibsted.webapp.server.template.ITemplateRenderer;
import com.schibsted.webapp.server.template.JsonRenderer;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class RestHandler extends MVCHandler implements ILogger {
	
	private final ITemplateRenderer templateRenderer=inject(JsonRenderer.class);

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		log(ex);
		ViewModel model = execute(ex);
		setContentType(ex, CONTENT_TYPE.JSON);
		writeResponseString(ex, model, templateRenderer);
		/*writeResponseOutputStream(ex, model);*/
	}

}

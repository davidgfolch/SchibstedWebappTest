package com.schibsted.webapp.server.handler;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.IOException;

import javax.inject.Named;

import com.schibsted.webapp.server.ILogger;
import com.schibsted.webapp.server.helper.HttpServerHelper;
import com.schibsted.webapp.server.model.ViewModel;
import com.schibsted.webapp.server.template.ITemplateRenderer;
import com.schibsted.webapp.server.template.JTwigTemplateRenderer;
import com.sun.net.httpserver.HttpExchange;

@Named
@SuppressWarnings("restriction")
public class WebHandler extends MVCHandler implements ILogger {

	private final ITemplateRenderer templateRenderer=inject(JTwigTemplateRenderer.class);
	private final HttpServerHelper httpServerHelper = inject(HttpServerHelper.class);

	@Override
	public void doHandle(HttpExchange ex) throws IOException {
		log(ex);
		ViewModel model = execute(ex);
		setRedirect(httpServerHelper.isRedirect(ex));
		if (isRedirect())
			return;
		writeResponseString(ex, model, templateRenderer);
		/*writeResponseOutputStream(ex, model);*/
	}

}

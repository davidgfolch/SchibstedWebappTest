package com.schibsted.webapp.server.template;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.io.OutputStream;
import java.net.URI;

import javax.inject.Named;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.model.ViewModel;

@Named
public class JTwigTemplateRenderer implements ITemplateRenderer {
	
	private final Config config=inject(Config.class);
	private final String templateBasePath = config.get("templates.folder");
	private final String templateExtension = config.get("templates.extension");

	@Override
	public void render(OutputStream os, URI uri, ViewModel model) {
		getTemplate(uri).render(getModel(model), os);
	}

	@Override
	public String render(URI uri, ViewModel model) {
		return getTemplate(uri).render(getModel(model));
	}

	private JtwigModel getModel(ViewModel model) {
		JtwigModel wigModel = JtwigModel.newModel();
		model.entrySet().forEach(entry -> 
			wigModel.with(entry.getKey(), entry.getValue())
		);
		return wigModel;
	}

	private JtwigTemplate getTemplate(URI uri) {
		String templatePath = templateBasePath + uri.getPath() + templateExtension;
		return JtwigTemplate.classpathTemplate(templatePath);
	}

}

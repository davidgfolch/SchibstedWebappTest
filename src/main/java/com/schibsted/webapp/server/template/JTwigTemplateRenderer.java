package com.schibsted.webapp.server.template;

import static com.schibsted.webapp.di.DIFactory.inject;

import java.net.URI;
import java.util.Map.Entry;

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
	public String render(URI uri, ViewModel model) {
		String templatePath = templateBasePath + uri.getPath() + templateExtension;
		JtwigTemplate template = JtwigTemplate.classpathTemplate(templatePath);
		JtwigModel wigModel = JtwigModel.newModel();
		for (Entry<String, Object> entry : model.entrySet()) {
			wigModel.with(entry.getKey(), entry.getValue());
		}
		return template.render(wigModel);
	}

}

package com.schibsted.webapp.server.template;

import java.net.URI;
import java.util.Map.Entry;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import com.schibsted.webapp.server.Config;
import com.schibsted.webapp.server.model.ViewModel;

public class JTwigTemplateRenderer implements ITemplateRenderer {
	
	private String templateBasePath="templates";
	private String templateExtension="html";

	public JTwigTemplateRenderer(String basePath,String ext) {
		this.templateBasePath=basePath;
		this.templateExtension=ext;
	}

	public JTwigTemplateRenderer(Config config) {
		this.templateBasePath=config.get("templates.folder");
		this.templateExtension=config.get("templates.extension");
	}

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

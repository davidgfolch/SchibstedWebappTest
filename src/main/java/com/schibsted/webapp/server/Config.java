package com.schibsted.webapp.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.webapp.server.exception.ConfigurationException;

public class Config {

	public static final String CONTROLLER = "controller";
	private static final String PROPERTIES_EXT = ".properties";
	private static final Logger LOG = LogManager.getLogger(Config.class);
	
	private Properties props = new Properties();

	private Config(Class<?> claz) throws ConfigurationException {
		super();
		load(claz);
	}

	public static Config getConfig(Class<?> claz) throws ConfigurationException {
		return new Config(claz);
	}

	/**
	 * Load resource from class name f.ex.: Server.class -> server.properties
	 * 
	 * @param claz
	 * @throws IOException
	 */
	private void load(Class<?> claz) throws ConfigurationException {
		InputStream in;
		String file = claz.getSimpleName().toLowerCase() + PROPERTIES_EXT;
		LOG.info("Loading config {}", file);
		try {
			in = getClass().getClassLoader().getResourceAsStream(file);
			if (in==null)
				throw new ConfigurationException("Resource not found in class loader: "+file);
			props.load(in);
			in.close();
		} catch (IOException e) {
			String errMsg = "Cant load properties from " + file;
			throw new ConfigurationException(errMsg, e);
		}
	}

	public int getInt(String key) {
		return Integer.valueOf(props.getProperty(key));
	}

	public String get(String key, String defaultValue) {
		String res = props.getProperty(key);
		return res == null ? defaultValue : res;
	}

	public String get(String key) {
		return props.getProperty(key);
	}

}

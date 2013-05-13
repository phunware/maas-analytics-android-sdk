package com.phunware.core;

import java.io.IOException;
import java.util.Properties;

public final class Config {

	/**
	 * Descriptor of which environment this SDK is built for
	 */
	protected static final String ENVIRONMENT;
	/**
	 * Base URL for internal API calls
	 */
	protected static final String BASE_URL;

	static {
		Properties prop = new Properties();
		
		String t_env = "prod";
		String t_base_url = "https://core-api.phunware.com/v1.0";
		try {
			// load a properties file
			prop.load(Config.class.getClassLoader().getResourceAsStream("com/phunware/core/internal/config.properties"));

			t_env = prop.getProperty("environment");
			t_base_url = prop.getProperty("baseUrl");

		} catch(NullPointerException e) {
			//This should only happen on a prod build
		} catch (IOException ex) {
		}
		
		ENVIRONMENT = t_env;
		BASE_URL = t_base_url;
	}

}

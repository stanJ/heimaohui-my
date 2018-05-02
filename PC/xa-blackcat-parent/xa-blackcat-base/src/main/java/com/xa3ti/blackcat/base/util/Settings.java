/**
 * 
 */
package com.xa3ti.blackcat.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Settings {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Settings.class);

	private static Settings instance;

	private Properties settings = new Properties();
	
	private Properties database = new Properties();

	public Settings() {
		LOGGER.info("Loading settings.properties...");
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("settings.properties");
		
		InputStream stream2 = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("database.properties");

		try {
			settings.load(stream);
			database.load(stream2);
			LOGGER.info("settings.properties loaded");
		} catch (IOException e) {
			LOGGER.error("Failed to load settings.properties", e);
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}

	public synchronized static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}

		return instance;
	}

	public String getString(String key) {
		return settings.getProperty(key);
	}
	
	public String getDbString(String key) {
		return database.getProperty(key);
	}
}

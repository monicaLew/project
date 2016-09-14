package com.pctrade.price.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

	public static Properties loadDbProperties() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = classLoader.getResourceAsStream("app.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Problems occured during loading DB Properties!");
		}
		return properties;
	}
}

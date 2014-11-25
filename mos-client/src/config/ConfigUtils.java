package config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import util.LogUtils;

public class ConfigUtils {

	private static Properties prop;

	static {
		prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("config/config.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

	public static String getProperty(String key, String def) {
		String val = prop.getProperty(key);
		return val == null ? def : val;
	}

	public static void setProperty(String key, String value) {
		prop.setProperty(key, value);
		String file = Thread.currentThread().getContextClassLoader()
				.getResource("config/config.properties").getPath();
		try {
			prop.store(new FileOutputStream(file), "´æ´¢");
		} catch (Exception e) {
			LogUtils.error(e.getMessage(), e);
		}
	}

}

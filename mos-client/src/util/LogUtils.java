/**
 * 
 */
package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * 
 */
public class LogUtils {

	private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

	public static void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	public static void info(String msg) {
		logger.info(msg);
	}

	public static void error(String msg, Throwable t) {
		logger.error(msg, t);
	}

	public static void error(String msg) {
		logger.error(msg);
	}

	public static void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	public static void debug(String msg) {
		logger.debug(msg);
	}

}

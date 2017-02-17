package com.gt.common;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * 
 * @author hujun 2017-02-09
 */
public class LogUtil {
	public static Logger logger = Logger.getLogger("autotest");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogUtil.log("testlogs");
	}

	/**
	 * ־
	 * 
	 * @param msg
	 */
	public static void log(String msg) {
		logger.debug(msg);
	}

	/**
	 * 
	 * 
	 * @param msg
	 */
	public static void logInfo(String msg) {
		logger.info(msg);
	}
}

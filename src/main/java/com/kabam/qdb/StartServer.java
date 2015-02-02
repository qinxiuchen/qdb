package com.kabam.qdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.kabam.qdb.util.Config;

public class StartServer {
	private static final Log log = LogFactory.getLog(StartServer.class);
	static {
		PropertyConfigurator.configure("config/log4j.properties");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configureAndWatch("config/log4j.properties", 5000);
		Qdb.newInstance(Integer.parseInt(Config.getSetting("port")));
		log.info("running at port " + Config.getSetting("port"));
	}

}

package org.iot.filler.gateway;

import org.apache.log4j.Logger;
import org.iot.filler.gateway.transport.COAPServer;

public class GatewayApplication {
	private static Logger logger = Logger.getLogger(GatewayApplication.class);

	public GatewayApplication() {
	}

	public static void main(String[] args) throws InterruptedException {

		logger.info("Starting Gateway Application");

		COAPServer server = new COAPServer();
		server.start();
	}
}

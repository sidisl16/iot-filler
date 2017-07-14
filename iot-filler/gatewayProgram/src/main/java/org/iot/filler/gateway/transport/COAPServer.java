package org.iot.filler.gateway.transport;

import org.apache.log4j.Logger;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.iot.filler.gateway.coap.resources.MobileAppSwitchResource;

public class COAPServer extends CoapServer {

	private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);
	private static final Logger logger = Logger.getLogger(COAPServer.class);
	
	private COAPServer server;

	public COAPServer() {
		logger.info("Initializing COAP server");
		NetworkConfig.getStandard().setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, 64)
				.setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, 64)
				.setInt(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_COUNT, 4)
				.setInt(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_TIME, 30000)
				.setString(NetworkConfig.Keys.HEALTH_STATUS_PRINT_LEVEL, "INFO");
		//adding resource
		add(new MobileAppSwitchResource());		
	}

	public void startCoapSrever() {
		logger.info("Starting COAP server");
		server = new COAPServer(); 
		server.start();
	}
	
	public void stopCoapSrever() {
		logger.info("Stopping COAP server");
		server.stop();
	}
}

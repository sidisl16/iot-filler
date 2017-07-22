package org.iot.filler.gateway.coap.resources;

import org.apache.log4j.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.iot.filler.gateway.device.BulbSwitch;
import org.iot.filler.gateway.device.Switch;

public class MobileAppSwitchResource extends CoapResource {

	private static final String MOBILE_APP_SWITCH_RESOURCE = "mobileSwitch";
	private static final Logger logger = Logger.getLogger(MobileAppSwitchResource.class);
	private Switch lightSwitch;
	private static final String[] OPERATIONS = { "ON", "OFF", "BLINKON", "BLINKOFF" };

	public MobileAppSwitchResource() {
		super(MOBILE_APP_SWITCH_RESOURCE);
		lightSwitch = new BulbSwitch();
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond("HELLO IOT");
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		String message = exchange.getRequestText();
		logger.info("POST request recieved message:[" + message + "]");
		try {
			processMessage(message);
			exchange.respond(ResponseCode.VALID);
			logger.info("POST response success [" + ResponseCode.VALID + "]");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("POST response failed [" + ResponseCode.INTERNAL_SERVER_ERROR + "]");
			exchange.respond(ResponseCode.INTERNAL_SERVER_ERROR);
		}
	}

	private void processMessage(String message) throws Exception {
		if (message.equalsIgnoreCase(OPERATIONS[0])) {
			lightSwitch.On();
		} else if (message.equalsIgnoreCase(OPERATIONS[1])) {
			lightSwitch.off();
		} else if (message.equalsIgnoreCase(OPERATIONS[2])) {
			lightSwitch.blinkOn();
		} else if (message.equalsIgnoreCase(OPERATIONS[3])) {
			lightSwitch.blinkOff();
		} else {
			logger.warn("Invalid Message [" + message + "]");
			throw new Exception("Invalid Message [" + message + "]");
		}
	}
}

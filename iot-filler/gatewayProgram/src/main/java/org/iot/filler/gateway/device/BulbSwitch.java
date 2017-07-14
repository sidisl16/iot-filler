package org.iot.filler.gateway.device;

import org.apache.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class BulbSwitch implements Switch {

	private final GpioController gpio;
	private final GpioPinDigitalOutput pin;
	private static Logger logger = Logger.getLogger(BulbSwitch.class);

	public BulbSwitch() {
		logger.info("Initializing GPIO");
		gpio = GpioFactory.getInstance();
		pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "BULB", PinState.LOW);
		pin.setShutdownOptions(true, PinState.LOW);
	}

	public void On() {
		if (!pin.isHigh()) {
			logger.info("Switch ON Bulb!");
			pin.high();
		} else {
			logger.warn("Bulb already switched ON!");
		}
	}

	public void off() {
		if (!pin.isLow()) {
			logger.info("Switch OFF Bulb!");
			pin.low();
		} else {
			logger.warn("Bulb already switched OFF!");
		}
	}

	public void blink(int count) {
		count = count * 2;
		logger.info("Bulb blinking: [" + (count/2) + " times]");
		for (int i = 0; i < count; i++) {
			pin.toggle();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void GPIOShutdown() {
		gpio.shutdown();
		logger.info("GPIO ShuttDown Completed!");
	}

}

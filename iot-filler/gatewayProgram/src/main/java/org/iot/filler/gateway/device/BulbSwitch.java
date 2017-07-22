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
	private BlinkThread blinkThread;

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

	public void blinkOn() {
		blinkExecutor(true);
	}

	public void blinkOff() {
		blinkExecutor(false);
	}

	public void GPIOShutdown() {
		gpio.shutdown();
		logger.info("GPIO ShuttDown Completed!");
	}

	private void blinkExecutor(boolean running) {
		if (running) {
			if (blinkThread != null && blinkThread.isAlive() && blinkThread.isRunning()) {
				logger.warn("Bulb already blinking!");
			} else {
				logger.info("Bulb blinking!");
				blinkThread = new BlinkThread();
				blinkThread.setRunning(true);
				blinkThread.start();
			}
		} else {
			logger.info("Stop Bulb blinking!");
			if (blinkThread.isRunning()) {
				blinkThread.setRunning(false);
				blinkThread = null;
			} else {
				logger.warn("Bulb is not blinking!");
			}
		}
	}

	private class BlinkThread extends Thread {

		private boolean running;

		@Override
		public void run() {
			while (running) {
				pin.toggle();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (pin.isHigh()) {
				pin.low();
			}

		}

		public boolean isRunning() {
			return running;
		}

		public void setRunning(boolean running) {
			this.running = running;
		}

	}

}

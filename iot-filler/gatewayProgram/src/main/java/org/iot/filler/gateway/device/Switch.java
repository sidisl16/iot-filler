package org.iot.filler.gateway.device;

public interface Switch {
	public void On();

	public void off();

	public void blinkOn();
	
	public void blinkOff();

	public void GPIOShutdown();
}

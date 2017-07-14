package org.iot.filler.gateway.device;

public interface Switch {
	public void On();

	public void off();

	public void blink(int count);

	public void GPIOShutdown();
}

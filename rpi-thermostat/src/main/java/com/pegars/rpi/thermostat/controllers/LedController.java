package com.pegars.rpi.thermostat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import com.pi4j.component.temperature.TemperatureSensor;

@RestController
public class LedController {
	public static GpioPinDigitalOutput pin;

	@RequestMapping("/")
	public String greeting() {
		return "Hello World!!";
	}

	@RequestMapping("/light")
	public String light() {
		if (pin == null) {
			GpioController gpio = GpioFactory.getInstance();
			pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "My LED", PinState.LOW);
		}
		pin.toggle();
		return "Response from light!!";
	}
	
	@RequestMapping("/temp")
	public String temp() {
		W1Master w1Master = new W1Master();
		String temp = "";

        System.out.println(w1Master);

        for (TemperatureSensor device : w1Master.getDevices(TemperatureSensor.class)) {
            temp = String.format("%-20s %3.1f°C %3.1f°F\n", device.getName(), device.getTemperature(),
                    device.getTemperature(TemperatureScale.FARENHEIT)) + "\r\n";
        }

        return temp;
	}
	
}

package com.station.taxi.logger;

import com.station.taxi.IPassenger;
import com.station.taxi.events.PassengerEventListener;

public class PassengerLogger extends PassengerEventListener {

	@Override
	public void update(int type, IPassenger passenger) {
		switch (type) {
		case START:
			LoggerWrapper.addPassengerLogger(passenger);
			logPassenger(passenger, "Passanger is ready and running...");
			break;
		case ARRIVED:
			String destination = passenger.getDestination();
			double price = passenger.getPaidPrice();
			logPassenger(passenger, "Arrived at " + destination + " paid " + price);
			break;
		case INTERRUPT:
			logPassenger(passenger, "Passanger interupt requested...");		
			break;
		case EXIT_QUEUE:
			logPassenger(passenger, "Waited too long leaving line angrily");
			break;
		case TRANSIT:
			logPassenger(passenger, "Took cab starting transit");
			break;
		default:
			break;
		}

	}

	/**
	 * Write a passenger action
	 * @param p
	 * @param message
	 */
	private static void logPassenger(IPassenger p,String message)
	{
		LoggerWrapper.log(String.format(PassengerFilter.PATTERN,p.getPassangerName()) + " " + message);
	}
}

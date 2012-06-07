package com.station.taxi.events;

import com.station.taxi.Passenger;

abstract public class PassengerEventListener {


	public static final int ARRIVED = 0;
	public static final int INTERRUPT = 1;
	public static final int START = 2;
	public static final int EXIT_QUEUE = 3;

	abstract public void update(int type, Passenger passenger);

}

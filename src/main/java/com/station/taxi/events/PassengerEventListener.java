package com.station.taxi.events;

import com.station.taxi.IPassenger;
/**
 * Passenger Event listener interface
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
abstract public class PassengerEventListener {


	public static final int ARRIVED = 0;
	public static final int INTERRUPT = 1;
	public static final int START = 2;
	public static final int EXIT_QUEUE = 3;
	public static final int TRANSIT = 4;

	abstract public void update(int type, IPassenger passenger);

}

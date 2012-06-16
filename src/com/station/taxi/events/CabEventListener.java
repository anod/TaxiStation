package com.station.taxi.events;

import com.station.taxi.Cab;
/**
 * Cab Event listener interface
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
abstract public class CabEventListener {

	public static final int INTERRUPT = 0;
	public static final int DRIVE_DESTINATION = 1;
	public static final int ARRIVED_DESTINATION = 2;
	public static final int START = 3;
	public static final int GOTO_BREAK = 4;
	public static final int WAITING = 6;
	public static final int INBREAK = 7;
	public static final int DRIVING = 8;	

	abstract public void update(int type, Cab cab);
}

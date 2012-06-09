package com.station.taxi.events;

import com.station.taxi.Cab;

abstract public class CabEventListener {

	public static final int INTERRUPT = 0;
	public static final int DRIVE_DESTINATION = 1;
	public static final int ARRIVED_DESTINATION = 2;
	public static final int START = 3;
	public static final int GOTO_BREAK = 4;
	public static final int FINISH_BREAK = 5;
	public static final int WAITING = 6;

	abstract public void update(int type, Cab cab);
}

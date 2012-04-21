package com.station.taxi;
/**
 * 
 * @author alex
 * updated to taxi so i can add some more interfaces for passenger.
 *
 */
public interface ITaxiEventListener {
	/**
	 * Cab request break event
	 * @param cab
	 */
	public void onBreakRequest(Cab cab);
	/**
	 * Cab request waiting event
	 * @param cab
	 */
	public void onWaitingRequest(Cab cab);
	/**
	 * Passenger request Exit event
	 * @param p
	 */
	public void onExitRequest(Passenger p);
}

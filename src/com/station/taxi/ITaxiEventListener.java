package com.station.taxi;
/**
 * 
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
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

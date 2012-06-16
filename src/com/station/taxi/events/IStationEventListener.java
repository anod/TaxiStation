package com.station.taxi.events;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;

/**
 * 
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 */
public interface IStationEventListener {
	/**
	 * Called when cab thread is ready
	 * @param cab
	 */
	public void onCabReady(Cab cab);
	/**
	 * Cab request break event
	 * @param cab
	 */
	public void onBreakRequest(Cab cab);
	/**
	 * Cab request waiting event after arriving to the destination
	 * @param cab
	 */
	public void onWaitingRequest(Cab cab);
	/**
	 * Passenger request Exit event
	 * @param p
	 */
	public void onExitRequest(Passenger p);
	/**
	 * @param p
	 */
	public void onPassengerReady(Passenger p);
	public void onPassengerUpdate(Passenger passenger);
}

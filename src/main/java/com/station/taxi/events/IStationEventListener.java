package com.station.taxi.events;

import com.station.taxi.ICab;
import com.station.taxi.Passenger;

/**
 * 
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
public interface IStationEventListener {
	/**
	 * Called when cab thread is ready
	 * @param Cab cab
	 */
	public void onCabReady(ICab cab);
	/**
	 * Cab request break event
	 * @param cab
	 */
	public void onBreakRequest(ICab cab);
	/**
	 * Cab request waiting event after arriving to the destination
	 * @param Cab cab
	 */
	public void onWaitingRequest(ICab cab);
	/**
	 * Passenger request Exit event
	 * @param Passenger p
	 */
	public void onExitRequest(Passenger p);
	/**
	 * called when Passenger object is ready
	 * @param Passenger p
	 */
	public void onPassengerReady(Passenger p);
	/**
	 * called when a Passenger object is updated
	 * @param Passenger p
	 */
	public void onPassengerUpdate(Passenger p);
}

package com.station.taxi.events;

import com.station.taxi.ICab;

/**
 * Passage arrival event listener
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 *
 */
public interface IArrivalEventListener {

	/**
	 * Passenger arrived to destination
	 * @param cab
	 * @param price
	 */
	public void onArrival(ICab cab, double price);
}

package com.station.taxi.events;

import com.station.taxi.Cab;

/**
 * Passage arrival event listener
 * @author alex
 *
 */
public interface IArrivalEventListener {

	/**
	 * Passenger arrived to destination
	 * @param cab
	 * @param price
	 */
	public void onArrival(Cab cab, double price);
}

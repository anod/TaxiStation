package com.station.taxi;
/**
 * Passage arrival event listener
 * @author alex
 *
 */
public interface IArrivalEventListener {

	/**
	 * Passanger arrivede to destination
	 * @param cab
	 * @param price
	 */
	public void onArrival(Cab cab, double price);
}

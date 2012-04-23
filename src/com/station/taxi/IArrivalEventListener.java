package com.station.taxi;
/**
 * 
 * @author alex
 *
 */
public interface IArrivalEventListener {

	/**
	 * 
	 * @param cab
	 * @param price
	 */
	public void onArrival(Cab cab, double price);
}

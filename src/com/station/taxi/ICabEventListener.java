package com.station.taxi;
/**
 * 
 * @author alex
 *
 */
public interface ICabEventListener {
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
}

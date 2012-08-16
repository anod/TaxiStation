package com.station.taxi.model;

import com.station.taxi.events.PassengerEventListener;
import com.station.taxi.events.StationEventListener;

/**
 * Passenger interface
 * @author alex
 */
public interface Passenger extends Runnable {

	/**
	 * @return name of passenger
	 */
	String getPassangerName();
	
	/**
	 * @return Time left in line of passenger
	 */
	int getTimeLeft();
	
	/**
	 * @return destination of passenger
	 */
	String getDestination();
	
	/**
	 * 
	 * @return time to wait in queue
	 */
	int getExitTime() ;
	
	/**
	 * Passenger enters cab
	 */
	void enterCab();

	/**
	 * Changes the state of passenger to waiting
	 */
	void enterWaitLine();

	/* (non-Javadoc)
	 * @see java.lang.Thread#interrupt()
	 */
	void interrupt();

	/**
	 * Checks whether the passenger is still in line
	 * @return true if the passenger is not in line
	 */
	boolean leftLine();

	/**
	 * Passenger arrived to destination
	 * @param (Cab)cab
	 * @param (double)price
	 */
	void onArrival(Cab cab, double price);

	/**
	 * Register passenger at station listener
	 * @param StationEventListener
	 */
	void setStationEventListener(StationEventListener listener);
	
	/**
	 * Register passenger event listener
	 * @param PassengerEventListener
	 */
	void addPassengerEventListener(PassengerEventListener listener);
	
	/**
	 * @return Price passenger paid for drive
	 */
	public double getPaidPrice();
	
	/**
	 * Exit from waiting queue
	 * 
	 */
	public void exitQueue();
}

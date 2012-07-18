/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi;

import com.station.taxi.events.IStationEventListener;
import com.station.taxi.events.PassengerEventListener;

/**
 *
 * @author alex
 */
public interface IPassenger extends Runnable {

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
	void onArrival(ICab cab, double price);

	/**
	 * Register passenger at station listener
	 * @param IStationEventListener
	 */
	void setStationEventListener(IStationEventListener listener);
	
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

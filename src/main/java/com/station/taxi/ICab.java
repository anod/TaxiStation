/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi;

import com.station.taxi.events.CabEventListener;
import com.station.taxi.events.IStationEventListener;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alex
 */
public interface ICab extends Runnable {


	/**
	 * Register cab event listener
	 * @param listener
	 */
	void addCabEventListener(CabEventListener listener);

	/**
	 * Add passenger to Cab
	 * @param passenger
	 * @throws Exception
	 */
	void addPassenger(Passenger passenger) throws Exception;

	/**
	 * Tells to cab that it arrived to destination
	 */
	void arrive();

	/**
	 * Start Driving
	 *
	 */
	void drive();

	/**
	 * Current break time
	 * @return
	 */
	int getBreakTime();

	/**
	 * Driving destination
	 * @return
	 */
	String getDestination();

	/**
	 * Get driving time in seconds
	 * @return
	 */
	int getDrivingTime();

	/**
	 * Return instance of current TaxiMeter
	 * @return
	 */
	TaxiMeter getMeter();

	/**
	 * Cab Id Number
	 * @return
	 */
	int getNumber();

	/**
	 * List of passengers inside the cab
	 * @return
	 */
	List<Passenger> getPassegners();

	double getTotalEarning();

	double getTotalEarning(Date start, Date end);

	/**
	 * While waiting action
	 * @return
	 */
	String getWhileWaiting();

	/**
	 * Go to break
	 */
	void goToBreak();

	/**
	 * Go to waiting state
	 */
	void goToWaiting();

	void interrupt();

	/**
	 * Return true if cab driving
	 * @return
	 */
	boolean isDriving();

	/**
	 * Check if cab is full
	 * @return
	 */
	boolean isFull();

	/**
	 * Return true if cab on break
	 * @return
	 */
	boolean isOnBreak();

	/**
	 * Return true if cab waiting for passengers
	 * @return
	 */
	boolean isWaiting();

	/**
	 * Set TaxiMeter instance
	 * @param meter
	 */
	void setMeter(TaxiMeter meter);

	/**
	 * Register station listener
	 * @param listener
	 */
	void setStationEventListener(IStationEventListener listener);

	String toString();
	
}

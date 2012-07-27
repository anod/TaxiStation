/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.model;

import com.station.taxi.model.TaxiStation.StateChangeListener;
import java.util.List;

/**
 *
 * @author alex
 */
public interface Station extends Runnable {

	/**
	 * Get threads that needs to initiated
	 * @return 
	 */
	public List<Runnable> getInitThreads();
	
	/**
	 * Add a cab to the station
	 * @param cab
	 */
	void addCab(Cab cab);

	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	void addPassenger(Passenger p);

	/**
	 * Get all taxi cabs in station
	 * @return
	 */
	List<Cab> getCabs();

	/**
	 * @return default TaxiMeter
	 */
	TaxiMeter getDefaultTaxiMeter();

	/**
	 * @return Number of taxi cabs in driving state
	 */
	int getDrivingTaxiCount();

	/**
	 * @return Number of passengers that left the line angry
	 */
	int getExitPassengersCount();

	/**
	 *
	 * @return maximum number of waiting taxi cabs
	 */
	int getMaxWaitingCount();

	/**
	 *
	 * @return All passengers in station
	 */
	List<Passenger> getPassengers();

	/**
	 * @return TaxiStation name
	 */
	String getStationName();

	/**
	 * @return Number of passengers waiting in line
	 */
	int getWaitingPassengersCount();

	/**
	 * @return Number of taxi cabs in waiting state
	 */
	int getWaitingTaxiCount();

	/**
	 * Initialize TaxiStation with cabs and passengers before station is running
	 * @param initCabs
	 * @param initPassengers
	 */
	void init(List<Cab> initCabs, List<Passenger> initPassengers);

	/**
	 * Stop station thread
	 */
	void interrupt();
	/**
	 *
	 * @param listener
	 */
	void registerStateListener(StateChangeListener listener);

	
}

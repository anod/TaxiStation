/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi;

import com.station.taxi.Station.IStateChangeListener;
import java.util.List;

/**
 *
 * @author alex
 */
public interface IStation extends Runnable {

	/**
	 * Get threads that needs to initiated
	 * @return 
	 */
	public List<Runnable> getInitThreads();
	
	/**
	 * Add a cab to the station
	 * @param cab
	 */
	void addCab(ICab cab);

	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	void addPassenger(IPassenger p);

	/**
	 * Get all taxi cabs in station
	 * @return
	 */
	List<ICab> getCabs();

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
	List<IPassenger> getPassengers();

	/**
	 * @return Station name
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
	 * Initialize Station with cabs and passengers before station is running
	 * @param initCabs
	 * @param initPassengers
	 */
	void init(List<ICab> initCabs, List<IPassenger> initPassengers);

	/**
	 * Stop station thread
	 */
	void interrupt();
	/**
	 *
	 * @param listener
	 */
	void registerStateListener(IStateChangeListener listener);

	
}

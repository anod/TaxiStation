package com.station.taxi;

import java.util.ArrayList;
/**
 * Taxi cab station
 * @author alex
 *
 */
public class Station {
	private ArrayList<Cab> mTaxiWaiting;
	private ArrayList<Cab> mTaxiDriving;
	private ArrayList<Cab> mTaxiBreak;
	private ArrayList<Passanger> mPassangerQueue;
	private ArrayList<Passanger> mPassangerExit;

	private String mName;
	private String mMaxWaitingCount;
	
	public Station(String name, String maxWaitingCount) {
		mName = name;
		mMaxWaitingCount = maxWaitingCount;
	}

	/**
	 * @return Station name
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * @return Number of taxi cabs in waiting state
	 */
	public int getWaitingTaxiCount() {
		return mTaxiWaiting.size();
	}

	/**
	 * @return Number of taxi cabs in driving state
	 */
	public int getDrivingTaxiCount() {
		return mTaxiDriving.size();
	}

	/**
	 * @return Number of taxi cabs on break
	 */
	public int getBreakTaxiCount() {
		return mTaxiBreak.size();
	}
	

	/**
	 * @return Number of passangers in waiting queue
	 */
	public int getBreakTaxiCount() {
		return mTaxiBreak.size();
	}


}

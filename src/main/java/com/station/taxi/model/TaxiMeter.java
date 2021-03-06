package com.station.taxi.model;

import java.util.Date;

/**
 * Taxi Meter calculate price for driven distance
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 *
 */
public class TaxiMeter implements Cloneable {

	final private double mStartPrice;
	final private double mOneSecPrice;
	private double mCurrentValue;
	private Date mRideStart;
		
	/**
	 * @param startPrice min price for every ride
	 * @param onSecPrice price for one second of a ride
	 */
	public TaxiMeter(double startPrice, double oneSecPrice) {
		mStartPrice = startPrice;
		mOneSecPrice = oneSecPrice;
	}
	
	/**
	 * Get price to pay
	 * @return
	 */
	public double getCurrentValue() {
		return mCurrentValue;
	}
	
	/**
	 * Prepare taximeter for a new ride
	 */
	public void reset() {
		mCurrentValue = mStartPrice;
	}
	
	/**
	 * Calculate price of the ride
	 * @param seconds
	 */
	public void increase() {
		mCurrentValue += mOneSecPrice;
	}

	@Override
	protected TaxiMeter clone() throws CloneNotSupportedException {
		return new TaxiMeter(mStartPrice, mOneSecPrice);
	}

	/**
	 * 
	 * @param numOfPassengers
	 * @return 
	 */
	public Receipt createReciept(int numOfPassengers) {
		return new Receipt(mRideStart, new Date(), mCurrentValue, numOfPassengers);
	}

	public void start() {
		mRideStart = new Date();
	}

	public double getPricePerSecond() {
		return mOneSecPrice;
	}

	public double getStartPrice() {
		return mStartPrice;
	}	
	
}

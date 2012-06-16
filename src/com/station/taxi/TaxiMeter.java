package com.station.taxi;
/**
 * Taxi Meter calculate price for driven distance
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 *
 */
import java.util.Date;
/**
 * TaxiMeter object
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
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
	 * Calc price of the ride
	 * @param seconds
	 */
	public void calc(int seconds) {
		mCurrentValue += mOneSecPrice * seconds;
	}

	@Override
	protected TaxiMeter clone() throws CloneNotSupportedException {
		return new TaxiMeter(mStartPrice, mOneSecPrice);
	}

	public Receipt stop(int numOfPassengers) {
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

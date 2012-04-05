package com.station.taxi;
/**
 * Taxi Meter calculate price for driven distance
 * @author alex
 *
 */
public class TaxiMeter {
	final private double mInitPrice;
	final private double mOneSecPrice;
	private double mCurrentValue;
	
	public TaxiMeter(double initPrice, double oneSecPrice) {
		mInitPrice = initPrice;
		mOneSecPrice = oneSecPrice;
	}
	
	public double getCurrentValue() {
		return mCurrentValue;
	}
	
	public void reset() {
		mCurrentValue = mInitPrice;
	}
	
	public void increase() {
		mCurrentValue += mOneSecPrice;
	}	
	
}

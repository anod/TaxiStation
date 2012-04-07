package com.station.taxi;
/**
 * Taxi Meter calculate price for driven distance
 * @author alex
 *
 */
public class TaxiMeter implements Cloneable {
	final private double mStartPrice;
	final private double mOneSecPrice;
	private double mCurrentValue;
	
	public TaxiMeter(double startPrice, double oneSecPrice) {
		mStartPrice = startPrice;
		mOneSecPrice = oneSecPrice;
	}
	
	public double getCurrentValue() {
		return mCurrentValue;
	}
	
	public void reset() {
		mCurrentValue = mStartPrice;
	}
	
	public void calc(int seconds) {
		mCurrentValue += mOneSecPrice * seconds;
	}

	@Override
	protected TaxiMeter clone() throws CloneNotSupportedException {
		return new TaxiMeter(mStartPrice, mOneSecPrice);
	}	
	
	
}

package com.station.taxi;
/**
 * 
 * 
 * @author alex
 *
 */
public class Cab {
	private static final int STATE_BREAK=0;
	private static final int STATE_DRIVING=1;
	private static final int STATE_WAITING=2;
	
	private String mBreakAction;
	private int mNumber;
	private TaxiMeter mMeter;
	private int mState;
	private int mDrivingTime = 0;
	
	public Cab(int num, String breakAction, TaxiMeter meter) {
		mNumber = num;
		mBreakAction = breakAction;
		mMeter = meter;
	}

	public int getNumer() {
		return mNumber;
	}
	
	public String getBreakAction() {
		return mBreakAction;
	}
	
	
	public void drive(Integer drivingTime) {
		mMeter.reset();
		if (drivingTime != null) {
			mDrivingTime = drivingTime;
		} else {
			// TODO fill mDrivingTime
		}
		mMeter.start();
		mState = STATE_DRIVING;
	}
	
	/**
	 * Go to break
	 */
	public void requestBreak() {
		//TODO
		mState = STATE_BREAK;
	}
	
	/**
	 * Go to waiting queue
	 */
	public void requestWaiting() {
		//TODO
		mState = STATE_WAITING;
	}
	
}

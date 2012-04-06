package com.station.taxi;

import java.util.ArrayList;

/**
 *  Tax cab object
 * @author alex
 *
 */
public class Cab extends Thread {
	private static final int ONE_SECOND = 1000;
	public static final int MAX_PASSANGERS = 4;
	private static final int STATE_BREAK=0;
	private static final int STATE_DRIVING=1;
	private static final int STATE_WAITING=2;

	private ArrayList<Passanger> mPassangers = new ArrayList<Passanger>(MAX_PASSANGERS);
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

	/**
	 * Add passanger to Cab
	 * @param passanger
	 * @throws Exception
	 */
	public void addPassanger(Passanger passanger) throws Exception {
		if (mPassangers.size() > 0) {
			Passanger first = mPassangers.get(0);
			if (!first.getDestination().equals(passanger.getDestination())) {
				throw new Exception("Wrong destination");
			}
		}
		mPassangers.add(passanger);
	}
	
	/**
	 * Check if cab is full
	 * @return
	 */
	public boolean isFull() {
		return mPassangers.size() == MAX_PASSANGERS;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(ONE_SECOND);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param drivingTime
	 * @throws Exception 
	 */
	public void drive(Integer drivingTime) throws Exception {
		if (mPassangers.size() == 0) {
			throw new Exception("Empty cab");
		}
		mMeter.reset();
		if (drivingTime != null) {
			mDrivingTime = drivingTime;
		} else {
			// TODO fill mDrivingTime
		}
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

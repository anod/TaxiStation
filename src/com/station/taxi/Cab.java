package com.station.taxi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.station.taxi.logger.LoggerWrapper;

/**
 *  Tax cab object
 * @author alex
 *
 */
public class Cab extends Thread {
	public static final int MAX_PASSANGERS = 4;

	private static final int ONE_SECOND = 1000;
	private static final int STATE_BREAK=0;
	private static final int STATE_DRIVING=1;
	private static final int STATE_WAITING=2;

	private List<Passenger> mPassangers;
	private String mWhileWaiting;
	private int mNumber;
	private TaxiMeter mMeter;
	private int mState;
	private int mDrivingTime = 0;
	private boolean mKeepRunning = true;
	
	public Cab(int num, String whileWaiting) {
		mNumber = num;
		mWhileWaiting = whileWaiting;
		// safe for threads
		mPassangers = Collections.synchronizedList(new ArrayList<Passenger>(MAX_PASSANGERS));
	}
	/**
	 * Cab Id Number
	 * @return
	 */
	public int getNumer() {
		return mNumber;
	}
	/**
	 * Action while waiting
	 * @return
	 */
	public String getWhileWaitingAction() {
		return mWhileWaiting;
	}
	/**
	 * Add passanger to Cab
	 * @param passenger
	 * @throws Exception
	 */
	public void addPassanger(Passenger passenger) throws Exception {
		if (mPassangers.size() > 0) {
			Passenger first = mPassangers.get(0);
			if (!first.getDestination().equals(passenger.getDestination())) {
				throw new Exception("Wrong destination");
			}
		}
		mPassangers.add(passenger);
	}
	/**
	 * Set TaxiMeter instance
	 * @param meter
	 */
	public void setMeter(TaxiMeter meter) {
		mMeter = meter;
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
		while ( mKeepRunning ) {
			switch(mState) {
				case STATE_DRIVING:
					driving();
				break;
				case STATE_WAITING:
					//
				break;
				case STATE_BREAK:
					//
				break;								
			}
			
	        try {
	        	sleep(50); 
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private void driving() {
		try {
			sleep(ONE_SECOND * mDrivingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		mMeter.calc(mDrivingTime); 
		notifyArrival();
		mState = STATE_WAITING;
		Passenger first = mPassangers.get(0);
		LoggerWrapper.logCab(this,"Arrived to desitnation '"+first.getDestination()+"' with "+mPassangers.size()+" passengers");		
		mPassangers.clear();
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
			Random rand = new Random();
			mDrivingTime = rand.nextInt(10)+5;			
		}
		mState = STATE_DRIVING;
		Passenger first = mPassangers.get(0);
		LoggerWrapper.logCab(this,"Start driving to '"+first.getDestination()+"' with "+mPassangers.size()+" passengers");
	}
	
	private void notifyArrival() {
		for(Passenger p: mPassangers) {
			p.onArrival(this, mMeter.getCurrentValue());
		}
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

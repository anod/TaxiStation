package com.station.taxi;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.station.taxi.logger.LoggerWrapper;

/**
 * Represents passenger of taxi cab
 * @author alex  
 * @author Eran Zimbler
 * @version 0.1 
 */
public class Passenger extends Thread {
	private static final int STATE_WAITING = 0;
	private static final int STATE_TRANSIT = 1;
	private static final int STATE_EXIT = 2;

	private ITaxiEventListener mStationListener;
	private static final int ONE_SECOND = 1000;
	private int mExitTime = 0;
	private String mName;
	private String mDestination;
	private boolean mKeepRunning = true;
	private int mState = 0;
	private int mTimeLeft = 0;
	
	public Passenger(String name, String destination) {
		mName = name;
		mDestination = destination;
	}

	/**
	 * @return 
	 */
	public String getPassangerName() {
		return mName;
	}

	/**
	 * @return 
	 */
	public String getDestination() {
		return mDestination;
	}
	public int getExitTime() {
		return mExitTime;
	}
	
	public void register(ITaxiEventListener stationListener) {
		mStationListener = stationListener;
	}
	@Override
	public void run() {
		while ( mKeepRunning  ) {
			if(mState == STATE_WAITING)
			{
				inWaitQueue();
			}
	        try {
	        	sleep(50); 
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

	private void inWaitQueue() {
		if(mTimeLeft >=0) { // using >= since it is possible the passenger is picked up when he is about to leave
			try {
				sleep(ONE_SECOND);
				mTimeLeft--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}else {
			LoggerWrapper.logPassenger(this, "Waited too long leaving line angrily");
			
			mStationListener.onExitRequest(this);
			mState = STATE_EXIT;
		}
	}
	
	public void enterWaitLine() {
		mState = STATE_WAITING;
		Random rand = new Random();	
		mExitTime  = rand.nextInt(5);
		mTimeLeft = mExitTime;
	}
	public void enterCab() {
		mState = STATE_TRANSIT;
		mExitTime -= mTimeLeft; 
	}
	public void onArrival(Cab cab, double price,int splitBy) {
		LoggerWrapper.logPassenger(this, "Arrived at " + mDestination + " with " + (splitBy-1) + " people paid " + price/splitBy);
		mState =  STATE_EXIT;
		//mStationListener.onExitRequest(this);
	}	
}

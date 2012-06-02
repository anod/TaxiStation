package com.station.taxi;

import java.util.Random;

import com.station.taxi.logger.LoggerWrapper;

/**
 * Represents passenger of taxi cab
 * @author alex  
 * @author Eran Zimbler
 * @version 0.1 
 */
public class Passenger extends Thread {
	private static final int STATE_INIT = 0;
	private static final int STATE_WAITING = 1;
	private static final int STATE_TRANSIT = 2;
	private static final int STATE_EXIT = 3;

	private IStationEventListener mStationListener;
	private static final int ONE_SECOND = 1000;
	private int mExitTime = 0;
	private String mName;
	private String mDestination;
	private boolean mThreadRunning = false;
	private int mState = STATE_INIT;
	private int mTimeLeft = 0;
	
	public Passenger(String name, String destination) {
		mName = name;
		mDestination = destination;
	}

	/**
	 * @return name of passenger
	 */
	public String getPassangerName() {
		return mName;
	}

	/**
	 * @return destination of passenger
	 */
	public  String getDestination() {
		return mDestination;
	}
	/**
	 * 
	 * @return time to wait in queue
	 */
	public int getExitTime() {
		return mExitTime;
	}
	/**
	 * Register passenger at station listener
	 * @param stationListener
	 */
	public void register(IStationEventListener stationListener) {
		mStationListener = stationListener;
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		LoggerWrapper.logPassenger(this, "Passanger interupt requested...");		
		mThreadRunning = false;
		super.interrupt();
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// Tell station that Passenger thread is started and running
		LoggerWrapper.logPassenger(this, "Passanger is ready and running...");
		mStationListener.onPassengerReady(this);
		mThreadRunning = true;
		
		while ( mThreadRunning  ) {
	        try {
				if(mState == STATE_WAITING)
				{
					inWaitQueue();
				}	        	
	        	sleep(50); 
	        } catch (InterruptedException e) {
	        	/* Allow thread to exit */
			}			
		}
	}
	
	/**
	 * While in waiting queue
	 * @throws InterruptedException 
	 */
	private synchronized void inWaitQueue() throws InterruptedException {
		if(mTimeLeft >=0) { // using >= since it is possible the passenger is picked up when he is about to leave
			sleep(ONE_SECOND);
			mTimeLeft--;
		}else {
			LoggerWrapper.logPassenger(this, "Waited too long leaving line angrily");
			
			mState = STATE_EXIT;
			mStationListener.onExitRequest(this);
		}
	}
	/**
	 * Change state of passanger to waiting
	 */
	public void enterWaitLine() {
		Random rand = new Random();	
		mExitTime  = rand.nextInt(5);
		mTimeLeft = mExitTime;
		mState = STATE_WAITING;
	}
	/**
	 * Passenger enters cab
	 */
	public void enterCab() {
		mState = STATE_TRANSIT;
		mExitTime -= mTimeLeft; 
	}
	/**
	 * Passaenger arrived to destination
	 * @param cab
	 * @param price
	 * @param splitBy
	 */
	public void onArrival(Cab cab, double price,int splitBy) {
		LoggerWrapper.logPassenger(this, "Arrived at " + mDestination + " with " + (splitBy-1) + " people paid " + price/splitBy);
		mState = STATE_EXIT;
		//mStationListener.onExitRequest(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#toString()
	 */
	@Override
	public String toString() {
		return "["+mName+","+mDestination+"]";
	}
	
}

package com.station.taxi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.station.taxi.events.IStationEventListener;
import com.station.taxi.events.PassengerEventListener;

/**
 * Represents passenger of taxi cab
 * @author alex  
 * @author Eran Zimbler
 * @version 0.2 
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
	private int mTimeLeft = 25;
	
	private List<PassengerEventListener> mEventListeners = new ArrayList<PassengerEventListener>();
	private double mPaidPrice = .0;

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
	 * @return Time left in line of passenger
	 */
	public int getTimeLeft() {
		return mTimeLeft;
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
	 * @param IStationEventListener
	 */
	public void setStationEventListener(IStationEventListener listener) {
		mStationListener = listener;
	}
	/**
	 * Register passenger event listener
	 * @param PassengerEventListener
	 */
	public void addPassengerEventListener(PassengerEventListener listener) {
		mEventListeners.add(listener);
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#interrupt()
	 */
	@Override
	public void interrupt() {
		notify(PassengerEventListener.INTERRUPT);
		mThreadRunning = false;
		super.interrupt();
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		notify(PassengerEventListener.START);
		// Tell station that Passenger thread is started and running
		mStationListener.onPassengerReady(this);
		mThreadRunning = true;
		
		while ( mThreadRunning  ) {
	        try {
				if(mState == STATE_WAITING)
				{
					inWaitQueue();
				}	        	
				sleep(ONE_SECOND);
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
			mTimeLeft--;
			mStationListener.onPassengerUpdate(this);
		}else {
			notify(PassengerEventListener.EXIT_QUEUE);			
			mState = STATE_EXIT;
			mStationListener.onExitRequest(this);
		}
	}
	/**
	 * Changes the state of passenger to waiting
	 */
	public void enterWaitLine() {
		Random rand = new Random();	
		mExitTime = rand.nextInt(25)+5;
		//mExitTime = 300; 
		mTimeLeft = mExitTime;
		mState = STATE_WAITING;
	}
	/**
	 * Passenger enters cab
	 */
	public void enterCab() {
		mState = STATE_TRANSIT;
		mExitTime -= mTimeLeft; 
		notify(PassengerEventListener.TRANSIT);
	}
	/**
	 * Passenger arrived to destination
	 * @param (Cab)cab
	 * @param (double)price
	 */
	public void onArrival(ICab cab, double price) {
		mPaidPrice  = price;
		notify(PassengerEventListener.ARRIVED);
		mState = STATE_EXIT;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#toString()
	 */
	@Override
	public String toString() {
		return "["+mName+","+mDestination+"]";
	}
	
	/**
	 * Notify event listeners
	 * @param (int)type
	 */
	private void notify(int type) {
		for(PassengerEventListener listener: mEventListeners) {
			listener.update(type, this);
		}
	}
	/**
	 * @return Price passenger paid for drive
	 */
	public double getPaidPrice() {
		return mPaidPrice;
	}
	/**
	 * Checks whether the passenger is still in line 
	 * @return true if the passenger is not in line 
	 */
	public boolean leftLine() {
		if(mState == STATE_EXIT || mState == STATE_TRANSIT)
		{
		return true;
		}
		else
		{
			return false;
		}
	}

}

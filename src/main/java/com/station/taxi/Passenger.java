package com.station.taxi;

import com.station.taxi.events.IStationEventListener;
import com.station.taxi.events.PassengerEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents passenger of taxi cab
 * @author alex  
 * @author Eran Zimbler
 * @version 0.2 
 */
public class Passenger implements IPassenger {
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
	
	private List<PassengerEventListener> mEventListeners = new ArrayList<>();
	private double mPaidPrice = .0;

	private IPassenger mAopProxy;
	
	public Passenger(String name, String destination) {
		mName = name;
		mDestination = destination;
	}

	public void setAopProxy(IPassenger proxy) {
		mAopProxy = proxy;
	}
	
	/**
	 * @return name of passenger
	 */
	@Override
	public String getPassangerName() {
		return mName;
	}
	/**
	 * @return Time left in line of passenger
	 */
	@Override
	public int getTimeLeft() {
		return mTimeLeft;
	}
	/**
	 * @return destination of passenger
	 */
	@Override
	public  String getDestination() {
		return mDestination;
	}
	/**
	 * 
	 * @return time to wait in queue
	 */
	@Override
	public int getExitTime() {
		return mExitTime;
	}
	/**
	 * Register passenger at station listener
	 * @param IStationEventListener
	 */
	@Override
	public void setStationEventListener(IStationEventListener listener) {
		mStationListener = listener;
	}
	/**
	 * Register passenger event listener
	 * @param PassengerEventListener
	 */
	@Override
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
		//super.interrupt();
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		notify(PassengerEventListener.START);
		// Tell station that Passenger thread is started and running
		mStationListener.onPassengerReady(mAopProxy);
		mThreadRunning = true;
		
		while ( mThreadRunning  ) {
	        try {
				if(mState == STATE_WAITING)
				{
					inWaitQueue();
				}        	
				Thread.sleep(ONE_SECOND);
	        } catch (InterruptedException e) {
	        	/* Allow thread to exit */
			}			
		}
	}
	
	/**
	 * Exit from waiting queue
	 * 
	 */
	@Override
	public void exitQueue() {
		if (mState != STATE_WAITING) {
			throw new RuntimeException("Passenger not in queue");
		}
		mState = STATE_EXIT;
		mStationListener.onExitRequest(mAopProxy);
		notify(PassengerEventListener.EXIT_QUEUE);
	}
	
	/**
	 * While in waiting queue
	 */
	private synchronized void inWaitQueue() {
		if(mTimeLeft >=0) { // using >= since it is possible the passenger is picked up when he is about to leave
			mTimeLeft--;
			mStationListener.onPassengerUpdate(mAopProxy);
		}else {
			mAopProxy.exitQueue();
		}
	}
	/**
	 * Changes the state of passenger to waiting
	 */
	@Override
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
	@Override
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
	@Override
	public void onArrival(ICab cab, double price) {
		mPaidPrice  = price;
		mState = STATE_EXIT;
		notify(PassengerEventListener.ARRIVED);
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
	@Override
	public double getPaidPrice() {
		return mPaidPrice;
	}
	/**
	 * Checks whether the passenger is still in line 
	 * @return true if the passenger is not in line 
	 */
	@Override
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

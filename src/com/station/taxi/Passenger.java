package com.station.taxi;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.station.taxi.logger.LoggerWrapper;

/**
 * Represents passenger of taxi cab
 * @author alex
 *
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
	private CyclicBarrier mTaxiCab;
	private boolean mKeepRunning = true;
	private int mState = 0;
	
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
	public void register(ITaxiEventListener stationListener) {
		mStationListener = stationListener;
	}
//	public void accept(final CyclicBarrier cab) {
//		mTaxiCab = cab;
//	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while ( mKeepRunning  ) {
//			switch( mState ) { // Not sure case is needed also not sure all states are needed.
//				case STATE_TRANSIT:
////					driving();
//				break;
//				case STATE_WAITING:
//					inWaitQueue();
//				break;
//				case STATE_EXIT:
//					// TODO
//				break;
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
		// TODO should be called when in waiting state
		if(mExitTime >=0) {
			try {
				sleep(ONE_SECOND);
				mExitTime--;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			LoggerWrapper.logPassenger(this, "Waited too long leaving line angrily");
			mStationListener.onExitRequest(this);
			mState = STATE_EXIT;
		}
	}
	
//	private void driving() {
//		if (mTaxiCab!=null) {
//			try {
//				mTaxiCab.await();
//			} catch (BrokenBarrierException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	public void enterWaitLine() {
		mState = STATE_WAITING;
		Random rand = new Random();	
		mExitTime  = rand.nextInt(5);	
	}
	public void enterCab() {
		mState = STATE_TRANSIT;
		mExitTime  = 0;	
	}
	public void onArrival(Cab cab, double price,int splitBy) {
		LoggerWrapper.logPassenger(this, "Arrived at " + mDestination + " with " + (splitBy-1) + " people paid " + price/splitBy);
		mState =  STATE_EXIT;
		mStationListener.onExitRequest(this);
	}	
}

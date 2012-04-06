package com.station.taxi;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Represents passanger of taxi cab
 * @author alex
 *
 */
public class Passanger extends Thread implements IArrivalEventListener {
	private static final int STATE_WAITING = 0;
	private static final int STATE_DRIVING = 1; //TODO: better name
	private static final int STATE_EXIT = 2;

	private int mExitTime = 0;
	private String mName;
	private String mDestination;
	private CyclicBarrier mTaxiCab;
	
	public Passanger(String name, String destination) {
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

	public void accept(final CyclicBarrier cab) {
		mTaxiCab = cab;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		if (mTaxiCab!=null) {
			try {
				mTaxiCab.await();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void onWaitingState() {
		// TODO assign mExitTime
	}

	@Override
	public void onArrival(Cab cab, double price) {
		
	}	
}

package com.station.taxi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.station.taxi.events.CabEventListener;
import com.station.taxi.events.IStationEventListener;

/**
 * Tax cab object
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 */
public class Cab extends Thread {
	public static final int MAX_PASSANGERS = 4;

	private static final int ONE_SECOND = 1000;
	private static final int STATUS_INIT=0;
	private static final int STATUS_BREAK=1;
	private static final int STATUS_DRIVING=2;
	private static final int STATUS_WAITING=3;

	private IStationEventListener mStationListener;
	private List<Passenger> mPassangers;
	private String mWhileWaiting;
	private int mNumber;
	private TaxiMeter mMeter;
	private int mCabStatus = STATUS_INIT;
	private int mDrivingTime = 0;
	private List<Receipt> mReciptsList;
	private boolean mThreadRunning = false;

	private int mBreakTime;
	private List<CabEventListener> mEventListeners = new ArrayList<>();
	
	/**
	 * 
	 * @param num Cab number
	 * @param whileWaiting action while waiting?
	 */
	public Cab(int num, String whileWaiting) {
		mNumber = num;
		mWhileWaiting = whileWaiting;
		// safe for threads
		mPassangers = Collections.synchronizedList(new ArrayList<Passenger>(MAX_PASSANGERS));
		mReciptsList = Collections.synchronizedList(new ArrayList<Receipt>());
	}
	/**
	 * Cab Id Number
	 * @return
	 */
	public int getNumber() {
		return mNumber;
	}
	public String getWhileWaiting()
	{
		return mWhileWaiting;
	}
	/**
	 * Return true if cab driving
	 * @return
	 */
	public boolean isDriving() {
		return mCabStatus == STATUS_DRIVING;
	}
	/**
	 * Return true if cab on break
	 * @return
	 */
	public boolean isOnBreak() {
		return mCabStatus == STATUS_BREAK;
	}
	/**
	 * Return true if cab waiting for passengers
	 * @return
	 */
	public boolean isWaiting() {
		return mCabStatus == STATUS_WAITING;
	}
	/**
	 * Register station listener
	 * @param listener
	 */
	public void setStationEventListener(IStationEventListener listener) {
		mStationListener = listener;
	}
	/**
	 * Register cab event listener
	 * @param listener
	 */
	public void addCabEventListener(CabEventListener listener) {
		mEventListeners.add(listener);
	}
	public double getTotalEarning(){
		double total = 0;
		for (Receipt r : mReciptsList) { //a map function like in most scripting and functional lanugage would be great here
			total += r.getPrice();
		}
		return total;
	}
	public double getTotalEarning(Date start,Date end){
		double total =0;
		for (Receipt r : mReciptsList) {
			if(r.getStartTime().after(start) && r.getStartTime().before(end))
				total += r.getPrice();
		}
		return total;
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
		passenger.enterCab();
	}
	public List<Passenger> getPassegners() {
		return mPassangers;
	}
	public int getBreakTime() {
		return mBreakTime;
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

	@Override
	public void interrupt() {
		notify(CabEventListener.INTERRUPT);
		mThreadRunning = false;
		super.interrupt();
	}	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// Tell station that cab thread is started and running
		notify(CabEventListener.START);
		mStationListener.onCabReady(this);
		mThreadRunning = true;
		
		while ( mThreadRunning ) {
	        try {			
				switch(mCabStatus) {
					case STATUS_DRIVING:
						driving();
					break;
					case STATUS_WAITING:
						waiting();
					break;
					case STATUS_BREAK:
						onBreak();
					break;								
				}
			
				sleep(50); 
	        } catch (InterruptedException e) {
	        	/* Allow thread to exit */
			}
		}
	}

	private synchronized void waiting() {
		Random rand = new Random();
		int value = rand.nextInt(100);
		if (value < 5) {
			requestBreak();
		}
	}
	/**
	 * Do a whileWaiting action for mBreakTime 
	 * @throws InterruptedException 
	 */
	private synchronized void onBreak() throws InterruptedException {
		notify(CabEventListener.GOTO_BREAK);
		sleep(ONE_SECOND * mBreakTime);
		notify(CabEventListener.FINISH_BREAK);
		mStationListener.onWaitingRequest(this);
	}
	/**
	 * Drive to the destination
	 * @throws InterruptedException 
	 */
	private synchronized void driving() throws InterruptedException {
		int size = mPassangers.size();
		mMeter.start();
		notify(CabEventListener.DRIVE_DESTINATION);		
		
		sleep(ONE_SECOND * mDrivingTime);

		mMeter.calc(mDrivingTime); 
		mReciptsList.add(mMeter.stop(size));
		notifyArrival();
		mPassangers.clear();
	}
	/**
	 * 
	 * @param drivingTime
	 * @throws Exception 
	 */
	public void drive() throws Exception {
		if (mPassangers.size() == 0) {
			throw new Exception("Empty cab");
		}
		mMeter.reset();
		Random rand = new Random();
		mDrivingTime = rand.nextInt(10)+5;			
		mCabStatus = STATUS_DRIVING;
	}
	/**
	 * Go to waiting state
	 */
	public void goToWaiting() {
		notify(CabEventListener.WAITING);		
		mCabStatus = STATUS_WAITING;
	}
	/**
	 * Go to break
	 */
	public void goToBreak() {
		Random rand = new Random();	
		mBreakTime  = rand.nextInt(10)+5;		
		mCabStatus = STATUS_BREAK;	
	}

	/**
	 * Notify passengers and station about end of the trip...
	 */
	private void notifyArrival() {
		notify(CabEventListener.ARRIVED_DESTINATION);		
		for(Passenger p: mPassangers) {
			p.onArrival(this, mMeter.getCurrentValue() / mPassangers.size());
		}
		mStationListener.onWaitingRequest(this);
	}
	
	/**
	 * Request break from station
	 */
	private void requestBreak() {
		mStationListener.onBreakRequest(this);
	}

	/**
	 * Notify event listeners
	 * @param type
	 */
	private void notify(int type) {
		for(CabEventListener listener: mEventListeners) {
			listener.update(type, this);
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[");
		sb.append(mNumber);
		sb.append(": ");
		switch(mCabStatus) {
		case STATUS_BREAK:
			sb.append("Break");
			break;
		case STATUS_WAITING:
			sb.append("Waiting");
			break;
		case STATUS_DRIVING:
			sb.append("Driving");
			break;
		}
		sb.append(", ");
		if (mPassangers.size() > 0) {
			sb.append("Passangers: ");
			for(Passenger p : mPassangers) {
				sb.append(p);
				sb.append(" ");
			}
		} else {
			sb.append("No passengers ");
		}
		sb.append("]");
		return sb.toString();
	}
	
}

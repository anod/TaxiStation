package com.station.taxi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.station.taxi.logger.LoggerWrapper;

/**
 * Tax cab object
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 */
public class Cab extends Thread {
	public static final int MAX_PASSANGERS = 4;

	private static final int ONE_SECOND = 1000;
	private static final int STATUS_BREAK=0;
	private static final int STATUS_DRIVING=1;
	private static final int STATUS_WAITING=2;

	private IStationEventListener mStationListener;
	private List<Passenger> mPassangers;
	private String mWhileWaiting;
	private int mNumber;
	private TaxiMeter mMeter;
	private int mCabStatus;
	private int mDrivingTime = 0;
	private List<Receipt> mReciptsList;
	private boolean mKeepRunning = true;

	private int mBreakTime;
	
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
	 * Register station listener
	 * @param stationListener
	 */
	public void register(IStationEventListener stationListener) {
		mStationListener = stationListener;
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
			
	        try {
	        	sleep(50); 
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void waiting() {
		Random rand = new Random();
		int value = rand.nextInt(100);
		if (value < 5) {
			requestBreak();
		}
	}
	/**
	 * Do a whileWaiting action for mBreakTime 
	 */
	private void onBreak() {
		LoggerWrapper.logCab(this,"Goto break for "+mBreakTime+" seconds to "+mWhileWaiting);
		try {
			sleep(ONE_SECOND * mBreakTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		LoggerWrapper.logCab(this,"Finished break");
		mStationListener.onWaitingRequest(this);
	}
	/**
	 * Drive to the destination
	 */
	private void driving() {
		String destination = mPassangers.get(0).getDestination();
		int size = mPassangers.size();
		mMeter.start();
		LoggerWrapper.logCab(this,"Start driving to '"+destination+"' with "+size+" passengers. Estimated time: +"+mDrivingTime+" seconds");
		try {
			sleep(ONE_SECOND * mDrivingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		mMeter.calc(mDrivingTime); 
		mReciptsList.add(mMeter.stop(size));
		LoggerWrapper.logCab(this,"Arrived to desitnation '"+destination+"' with "+size+" passengers");		
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
		for(Passenger p: mPassangers) {
			p.onArrival(this, mMeter.getCurrentValue(),mPassangers.size());
		}
		mStationListener.onWaitingRequest(this);
	}
	
	/**
	 * Request break from station
	 */
	private void requestBreak() {
		mStationListener.onBreakRequest(this);
	}
	
}

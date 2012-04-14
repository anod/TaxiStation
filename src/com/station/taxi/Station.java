package com.station.taxi;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.station.taxi.logger.LoggerWrapper;
/**
 * Taxi cab station
 * @author alex
 *
 */
public class Station extends Thread {
	/**
	 * ArrayBlockingQueue is a queue of a fixed size. 
	 * So if you set the size at 5, and attempt to insert an 6th element,
	 * the insert statement will block until another thread removes an element. 
	 *
	 * The most important difference between LinkedBlockingQueue and ConcurrentLinkedQueue 
	 * is that if you request an element from a LinkedBlockingQueue and the queue is empty,
	 * your thread will wait until there is something there.
	 * A ConcurrentLinkedQueue will return right away with the behavior of an empty queue.
	 */
	private ArrayBlockingQueue<Cab> mTaxiWaiting;
	private Vector<Cab> mTaxiDriving;
	private LinkedBlockingQueue<Cab> mTaxiBreak;
	private Vector<Passenger> mPassangersList;
	private LinkedBlockingQueue<Passenger> mPassangerExit;

	private String mStationName;
	private int mMaxWaitingCount;
	private TaxiMeter mDefaultTaxiMeter;
	private boolean mKeepRunning = true;
	
	public Station(String name, int maxWaitingCount, TaxiMeter defaultTaxiMeter) {
		mStationName = name;
		mMaxWaitingCount = maxWaitingCount;
		mTaxiWaiting = new ArrayBlockingQueue<Cab>(maxWaitingCount);
		mTaxiBreak = new LinkedBlockingQueue<Cab>();
		mTaxiDriving = new Vector<Cab>();
		mPassangersList = new Vector<Passenger>();
		mPassangerExit = new LinkedBlockingQueue<Passenger>();
		mDefaultTaxiMeter = defaultTaxiMeter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		for(Cab cab: mTaxiWaiting) {
			cab.start();
		}
		for(Cab cab: mTaxiBreak) {
			cab.start();
		}
		for(Passenger p: mPassangersList) {
			p.start();
		}		
		while ( mKeepRunning ) {
			fillTaxi();
	        try {
	        	sleep(50); 
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return Station name
	 */
	public String getStationName() {
		return mStationName;
	}
	/**
	 * @return Number of taxi cabs in waiting state
	 */
	public int getWaitingTaxiCount() {
		return mTaxiWaiting.size();
	}
	/**
	 * @return Number of taxi cabs in driving state
	 */
	public int getDrivingTaxiCount() {
		return mTaxiDriving.size();
	}
	/**
	 * Add a cab to the station
	 * @param cab
	 */
	public void addCab(Cab cab) {
		cab.setMeter(createTaxiMeter());
		try {
			LoggerWrapper.addCabLogger(cab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO decide which queue, mMaxWaitingCount
		if (mTaxiWaiting.size() >= mMaxWaitingCount) {
			mTaxiBreak.add(cab);
		} else {
			mTaxiWaiting.add(cab);
		}
	}
	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	public void addPassenger(Passenger passenger) {
		mPassangersList.add(passenger);
	}	
	/**
	 * Creates new instance of taxi meter
	 * @return
	 */
	private TaxiMeter createTaxiMeter() {
		try {
			return mDefaultTaxiMeter.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Fill cab with passengers
	 */
	private void fillTaxi() {
		// Get first cab in queue, if there is no cabs in waiting queue
		// waits until new cab will be added
		Cab cab = getNextWaitingCab();
		addPassangersToCab(cab);
		
		mTaxiDriving.add(cab);
		try {
			cab.drive(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param cab
	 */
	private void addPassangersToCab(Cab cab) {
		synchronized (cab) {
			Passenger firstPassenger = mPassangersList.get(0);
			mPassangersList.remove(0);
			try {
				cab.addPassanger(firstPassenger);
				String dest = firstPassenger.getDestination();
				for(Passenger passenger : mPassangersList) {
					if (passenger.getDestination().equals(dest)) {
						cab.addPassanger(passenger);
					}
					if (cab.isFull()) {
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	private Cab getNextWaitingCab() {
		Cab cab;
		synchronized (mTaxiWaiting) {
			cab = mTaxiWaiting.poll();
			if (cab == null) {
				try {
					mTaxiWaiting.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cab = mTaxiWaiting.poll();
			}
		}
		return cab;
	}
	

}

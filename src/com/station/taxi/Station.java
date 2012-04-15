package com.station.taxi;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import com.station.taxi.logger.LoggerWrapper;
/**
 * Taxi cab station
 * @author alex
 *
 */
public class Station extends Thread implements ICabEventListener {
    /**
     * Lock used when maintaining queue of requested updates.
     */
    public static Object sLock = new Object();
    
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
	private ArrayList<Cab> mTaxiDriving;
	private ArrayList<Cab> mTaxiBreak;
	private ArrayList<Passenger> mPassengersList;
	private ArrayList<Passenger> mPassengerExit;


	
	private String mStationName;
	private int mMaxWaitingCount;
	private TaxiMeter mDefaultTaxiMeter;
	private boolean mKeepRunning = true;
	
	public Station(String name, int maxWaitingCount, TaxiMeter defaultTaxiMeter) {
		mStationName = name;
		mMaxWaitingCount = maxWaitingCount;
		mTaxiWaiting = new ArrayBlockingQueue<Cab>(maxWaitingCount);
		mTaxiBreak = new ArrayList<Cab>();
		mTaxiDriving = new ArrayList<Cab>();
		mPassengersList = new ArrayList<Passenger>();
		mPassengerExit = new ArrayList<Passenger>();
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
		for(Passenger p: mPassengersList) {
			p.start();
		}		
		while ( mKeepRunning ) {
			try {
				
				synchronized (sLock) {
					fillTaxi();
				}
				
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
		//Set taxi meter
		cab.setMeter(createTaxiMeter());
		cab.register(this);

		if (mTaxiWaiting.size() >= mMaxWaitingCount) {
			cab.goToBreak();
			mTaxiBreak.add(cab);
		} else {
			cab.goToWaiting();
			mTaxiWaiting.add(cab);
		}
		
		LoggerWrapper.addCabLogger(cab);
	}
	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	public void addPassenger(Passenger passenger) {
		mPassengersList.add(passenger);
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
	 * @throws InterruptedException 
	 */
	private void fillTaxi() throws InterruptedException {
		// Get first cab in queue, if there is no cabs in waiting queue
		// waits until new cab will be added
		synchronized (sLock) {
			Cab cab = mTaxiWaiting.take();
			addPassangersToCab(cab);
			mTaxiDriving.add(cab);
			try {
				cab.drive();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param cab
	 */
	private void addPassangersToCab(Cab cab) {
		synchronized (cab) {
			Passenger firstPassenger = mPassengersList.get(0);
			mPassengersList.remove(0);
			try {
				cab.addPassanger(firstPassenger);
				String dest = firstPassenger.getDestination();
				for(Passenger passenger : mPassengersList) {
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

	@Override
	public void onBreakRequest(Cab cab) {
		synchronized (sLock) {
			if (cab.isDriving()) {
				mTaxiDriving.remove(cab);
			} else {
				mTaxiWaiting.remove(cab);
			}
			mTaxiBreak.add(cab);
			cab.goToBreak();
		}
	}

	@Override
	public void onWaitingRequest(Cab cab) {
		synchronized (sLock) {
			mTaxiDriving.remove(cab);
			mTaxiWaiting.add(cab);
			cab.goToWaiting();
		}
	}
	

}

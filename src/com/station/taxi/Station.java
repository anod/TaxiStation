package com.station.taxi;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Taxi cab station
 * @author alex
 *
 */
public class Station {
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
	private ConcurrentLinkedQueue<Cab> mTaxiWaiting;
	private ConcurrentLinkedQueue<Cab> mTaxiDriving;
	private ConcurrentLinkedQueue<Cab> mTaxiBreak;
	private ConcurrentLinkedQueue<Passenger> mPassangerQueue;
	private ConcurrentLinkedQueue<Passenger> mPassangerExit;

	private String mName;
	private int mMaxWaitingCount;
	private TaxiMeter mDefaultTaxiMeter;
	
	public Station(String name, int maxWaitingCount, TaxiMeter defaultTaxiMeter) {
		mName = name;
		mMaxWaitingCount = maxWaitingCount;
		mTaxiWaiting = new ConcurrentLinkedQueue<Cab>();
		mDefaultTaxiMeter = defaultTaxiMeter;
	}

	/**
	 * @return Station name
	 */
	public String getName() {
		return mName;
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
		// TODO decide which queue, mMaxWaitingCount
		mTaxiWaiting.add(cab);
	}
	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	public void addPassenger(Passenger passenger) {
		mPassangerQueue.add(passenger);
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
		// Check example from 04-threads.pptx
		//ExecutorService lineManager = Executors.newFixedThreadPool(Cab.MAX_PASSANGERS);
	}
	


}

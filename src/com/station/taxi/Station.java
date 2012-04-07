package com.station.taxi;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
	private ArrayBlockingQueue<Cab> mTaxiWaiting;
	private ConcurrentLinkedQueue<Cab> mTaxiDriving;
	private ConcurrentLinkedQueue<Cab> mTaxiBreak;
	private ArrayList<Passanger> mPassangerQueue;
	private ConcurrentLinkedQueue<Passanger> mPassangerExit;

	private String mName;
	private int mMaxWaitingCount;
	private TaxiMeter mDefaultTaxiMeter;
	
	public Station(String name, int maxWaitingCount, TaxiMeter defaultTaxiMeter) {
		mName = name;
		mTaxiWaiting = new ArrayBlockingQueue<Cab>(maxWaitingCount);
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

	public TaxiMeter createTaxiMeter() {
		try {
			return mDefaultTaxiMeter.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return Number of taxi cabs in driving state
	 */
	public int getDrivingTaxiCount() {
		return mTaxiDriving.size();
	}

	public void fillTaxi() {
		// Check example from 04-threads.pptx
		//ExecutorService lineManager = Executors.newFixedThreadPool(Cab.MAX_PASSANGERS);
	}
	


}

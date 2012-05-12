package com.station.taxi;

import java.util.ArrayList;

import com.station.taxi.configuration.StationConfigStorage;
import com.station.taxi.logger.LoggerWrapper;
/**
 * Taxi cab station object
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 */
public class Station extends Thread implements IStationEventListener {
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
	private ArrayList<Cab> mTaxiWaiting;
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
		mTaxiWaiting = new ArrayList<Cab>();
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
				fillTaxi();
	        	sleep(50); 
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void save(String fileName){
		ArrayList<Cab> totalC = mTaxiDriving;
		totalC.addAll(mTaxiWaiting);
		totalC.addAll(mTaxiBreak);
		ArrayList<Passenger> totalP = mPassengersList;
		totalP.addAll(mPassengerExit);
		TaxiMeter meter = createTaxiMeter();
		StationConfigStorage newConfig = new StationConfigStorage(fileName);
		newConfig.SaveStation(meter, mStationName, mMaxWaitingCount, totalC, totalP);
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
		synchronized (sLock) {
			return mTaxiWaiting.size();
		}
	}
	/**
	 * @return Number of taxi cabs in driving state
	 */
	public int getDrivingTaxiCount() {
		synchronized (sLock) {
			return mTaxiDriving.size();
		}
	}
	/**
	 * @return Number of passengers waiting in line
	 */
	public int getWaitingPassengersCount()
	{
		synchronized (sLock) {
			return mPassengersList.size();
		}
	}
	/**
	 * @return Number of passengers that left the line angry
	 */
	public int getExitPassengersCount()
	{
		synchronized (sLock) {
			return mPassengerExit.size();
		}
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
	public void addPassenger(Passenger p) {
		p.register(this);
		mPassengersList.add(p);
		p.enterWaitLine();
		LoggerWrapper.addPassengerLogger(p);
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
			if (mTaxiWaiting.isEmpty() || mPassengersList.isEmpty()) {
				return;
			}
			Cab cab = mTaxiWaiting.remove(0);
			addPassengersToCab(cab);
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
	private void addPassengersToCab(Cab cab) {
		synchronized (cab) {
			Passenger firstPassenger = mPassengersList.remove(0);
			mPassengerExit.add(firstPassenger);
			try {
				cab.addPassanger(firstPassenger);
				String dest = firstPassenger.getDestination();
				for(int i=0; i<mPassengersList.size(); i++) {
					Passenger p = mPassengersList.get(i);
					if (p.getDestination().equals(dest)) {
						cab.addPassanger(p);
						mPassengersList.remove(i);
						mPassengerExit.add(p);
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
	 * Called when cab requests for a break;
	 * @param Cab instance of Cab
	 */
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

	/**
	 * When cab arrived to destination it returns to the station
	 * @param Cab instance of cab 
	 */
	@Override
	public void onWaitingRequest(Cab cab) {
		synchronized (sLock) {
			mTaxiDriving.remove(cab);
			mTaxiWaiting.add(cab);
			cab.goToWaiting();
		}
	}
	
	/**
	 * Passenger request to exit from the queue
	 */
	@Override
	public void onExitRequest(Passenger p)
	{
		synchronized (sLock) {
			if(mPassengersList.contains(p))
			{
				mPassengersList.remove(p);
			}
			mPassengerExit.add(p);
		}
	}	
}

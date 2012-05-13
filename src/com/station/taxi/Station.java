package com.station.taxi;

import java.util.ArrayList;
import java.util.List;

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
     * List of taxi cab in waiting state
     */
	private List<Cab> mTaxiWaiting;
	/**
	 * List of taxi cab currently driving
	 */
	private List<Cab> mTaxiDriving;
	/**
	 * List of taxi cab on break
	 */
	private List<Cab> mTaxiBreak;
	/**
	 * List of passengers waiting in queue
	 */
	private List<Passenger> mPassengersList;
	/**
	 * List of passengers who exit the queue 
	 */
	private List<Passenger> mPassengerExit;
	
	private String mStationName;
	private int mMaxWaitingCount;
	private TaxiMeter mDefaultTaxiMeter;
	private boolean mKeepRunning = true;
	
	/**
	 * 
	 * @param name Station name
	 * @param maxWaitingCount maximum number of waiting taxi cabs
	 * @param defaultTaxiMeter taxi meter with defaults
	 */
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
		while ( mKeepRunning ) {
			try {
				fillTaxi();
	        	sleep(50); 
	        } catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Get all taxi cabs in station
	 * @return
	 */
	public List<Cab> getCabs() {
		synchronized (sLock) {		
			List<Cab> allCabs = mTaxiDriving;
			allCabs.addAll(mTaxiWaiting);
			allCabs.addAll(mTaxiBreak);
			return allCabs;
		}
	}
	/**
	 * 
	 * @return All passengers in station
	 */
	public List<Passenger> getPassengers() {
		synchronized (sLock) {	
			List<Passenger> allPassengers = mPassengersList;
			allPassengers.addAll(mPassengerExit);
			return allPassengers;
		}
	}
	/**
	 * @return Station name
	 */
	public String getStationName() {
		return mStationName;
	}
	/**
	 * @return default TaxiMeter
	 */
	public TaxiMeter getDefaultTaxiMeter() {
		return mDefaultTaxiMeter;		
	}
	/**
	 * 
	 * @return maximum number of waiting taxi cabs
	 */
	public int getMaxWaitingCount() {
		return mMaxWaitingCount;
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
		LoggerWrapper.addCabLogger(cab);		
		cab.start();
	}
	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	public void addPassenger(Passenger p) {
		p.register(this);
		LoggerWrapper.addPassengerLogger(p);
		p.start();
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
		Passenger firstPassenger = mPassengersList.remove(0);
		try {
			cab.addPassanger(firstPassenger);
			String dest = firstPassenger.getDestination();
			for(int i=0; i<mPassengersList.size(); i++) {
				Passenger p = mPassengersList.get(i);
				if (p.getDestination().equals(dest)) {
					cab.addPassanger(p);
					mPassengersList.remove(i);
				}
				if (cab.isFull()) {
					break;
				}					
			}
		} catch (Exception e) {
			e.printStackTrace();
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
//			if(mPassengersList.contains(p))
//			{
//				mPassengersList.remove(p);
//			}
//			mPassengerExit.add(p);
		}
	}
	@Override
	public void onCabReady(Cab cab) {
		synchronized (sLock) {
			if (mTaxiWaiting.size() >= mMaxWaitingCount) {
				cab.goToBreak();
				mTaxiBreak.add(cab);
			} else {
				cab.goToWaiting();
				mTaxiWaiting.add(cab);
			}
		}
	}
	@Override
	public void onPassengerReady(Passenger p) {
		mPassengersList.add(p);
		p.enterWaitLine();
	}

}

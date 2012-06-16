package com.station.taxi;

import java.util.ArrayList;
import java.util.List;

import com.station.taxi.events.IStationEventListener;
import com.station.taxi.logger.CabLogger;
import com.station.taxi.logger.PassengerLogger;
/**
 * Taxi cab station object
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 */
public class Station extends Thread implements IStationEventListener {
	
	/**
	 * TODO: Still not called
	 * TODO: think how it will be used
	 */
	public interface IStateChangeListener {
		public void onStationStart(Station station);
		public void onCabUpdate(Cab cab, int oldState);
		public void onPassengerUpdate(Passenger p);
		public void onCabAdd(Cab cab);
		public void onPassengerAdd(Passenger p);
	}

	public static final int CAB_DRIVE = 0;
	public static final int CAB_BREAK = 1;
	public static final int CAB_WAITING = 2;
	
    /**
     * Lock used when maintaining queue of requested updates.
     */
	private static Object sLock = new Object();

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
	private boolean mThreadRunning = false;
	
	private List<Thread> mInitThreads;
	
	private IStateChangeListener mStateListener;
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
		mInitThreads = new ArrayList<Thread>();
	}
	
	/**
	 * Initialize Station with cabs and passengers before station is running
	 * @param initCabs
	 * @param initPassengers
	 */
	public void init(List<Cab> initCabs, List<Passenger> initPassengers) {
		for(Cab cab: initCabs) {
			initCab(cab);
			mInitThreads.add(cab);
		}
		for(Passenger p: initPassengers) {
			initPassenger(p);
			mInitThreads.add(p);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#start()
	 */
	@Override
	public synchronized void start() {
		for(Thread t: mInitThreads) {
			t.start();
		}
		super.start();
	}

	@Override
	public void interrupt() {
		mThreadRunning = false;
		List<Cab> cabs = getCabs();
		for(Cab cab: cabs) {
			cab.interrupt();
		}
		List<Passenger> passengers = getPassengers();
		for(Passenger p: passengers) {
			p.interrupt();
		}
		super.interrupt();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {	
		mThreadRunning = true;
		mStateListener.onStationStart(this);	
		while ( mThreadRunning ) {
			try {
				fillCab();
	        	sleep(50); 
	        } catch (InterruptedException e) {
	        	/* Allow thread to exit */
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
		if (!mThreadRunning) {
			throw new UnsupportedOperationException("Cab can by added only to running stataion");
		}
		initCab(cab);		
		cab.start();
	}

	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	public void addPassenger(Passenger p) {
		if (!mThreadRunning) {
			throw new UnsupportedOperationException("Passenger can by added only to running stataion");
		}		
		initPassenger(p);
		p.start();
	}
	
	/**
	 * Init a new cab in the station
	 * @param cab
	 */
	private void initCab(Cab cab) {
		//Set taxi meter
		cab.setMeter(createTaxiMeter());
		cab.setStationEventListener(this);
		cab.addCabEventListener(new CabLogger(cab));
	}

	/**
	 * Init a new Passenger in the station
	 * @param p
	 */
	private void initPassenger(Passenger p) {
		p.setStationEventListener(this);
		p.addPassengerEventListener(new PassengerLogger(p));
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
	private void fillCab() throws InterruptedException {
		// Get first cab in queue, if there is no cabs in waiting queue
		// waits until new cab will be added
		synchronized (sLock) {
			if (mTaxiWaiting.isEmpty() || mPassengersList.isEmpty()) {
				return;
			}		
			Cab cab = mTaxiWaiting.remove(0);
			int oldState = detectCabState(cab);			
			addPassengersToCab(cab);
			mTaxiDriving.add(cab);
			cab.drive();
			mStateListener.onCabUpdate(cab, oldState);
		}
	
	}
	/**
	 * @param cab
	 */
	private void addPassengersToCab(Cab cab) {
		Passenger firstPassenger = mPassengersList.remove(0);
		try {
			cab.addPassenger(firstPassenger);
			mStateListener.onPassengerUpdate(firstPassenger);
			String dest = firstPassenger.getDestination();
			for(int i=0; i<mPassengersList.size(); i++) {
				Passenger p = mPassengersList.get(i);
				if (p.getDestination().equals(dest)) {
					cab.addPassenger(p);
					mStateListener.onPassengerUpdate(p);
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
				throw new RuntimeException("Invalid cab ["+ cab.getNumber() +"] state while request break");
			}
			int oldState = detectCabState(cab);
			mTaxiWaiting.remove(cab);
			mTaxiBreak.add(cab);
			cab.goToBreak();
			mStateListener.onCabUpdate(cab, oldState);
		}
	}

	/**
	 * When cab arrived to destination it returns to the station
	 * @param Cab instance of cab 
	 */
	@Override
	public void onWaitingRequest(Cab cab) {
		synchronized (sLock) {
			int oldState = detectCabState(cab); 
			mTaxiDriving.remove(cab);
			mTaxiWaiting.add(cab);
			cab.goToWaiting();
			mStateListener.onCabUpdate(cab, oldState);
		}
	}
	/**
	 * 
	 * @param cab
	 * @return
	 */
	private int detectCabState(Cab cab) {
		if (cab.isDriving()) {
			return CAB_DRIVE;
		}
		if (cab.isWaiting()) {
			return CAB_WAITING;
		}
		return CAB_BREAK;
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
			mStateListener.onPassengerUpdate(p);
		}
	}
	
	/**
	 * Registered cab thread notify that it's ready
	 */
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
			mStateListener.onCabAdd(cab);
		}
	}
	/**
	 * Registered passenger thread notify that it's ready
	 */
	@Override
	public void onPassengerReady(Passenger p) {
		mPassengersList.add(p);
		mStateListener.onPassengerAdd(p);
		p.enterWaitLine();
	}

	/**
	 * 
	 * @param listener
	 */
	public void registerStateListener(IStateChangeListener listener) {
		mStateListener = listener;
	}

	@Override
	public void onPassengerUpdate(Passenger passenger) {
		mStateListener.onPassengerUpdate(passenger);
	}

}

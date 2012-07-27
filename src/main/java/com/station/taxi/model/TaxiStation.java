package com.station.taxi.model;

import com.station.taxi.db.repositories.ReceiptRepository;
import com.station.taxi.events.StationEventListener;
import com.station.taxi.logger.LoggerWrapper;
import com.station.taxi.spring.StationContext;
import java.util.ArrayList;
import java.util.List;
/**
 * Taxi cab station object
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
public class TaxiStation implements Station, StationEventListener {
	/**
	 * Cab states
	 */
	public static final int CAB_DRIVE = 0;
	public static final int CAB_BREAK = 1;
	public static final int CAB_WAITING = 2;

	/**
	 * Events of station state change:
	 * 	Initialization
	 *  Adding or changing state of cabs
	 *  Adding or changing state of passengers
	 */
	public interface StateChangeListener {
		/**
		 * TaxiStation is initialized and running
		 * @param station
		 */
		public void onStationStart(TaxiStation station);
		/**
		 * Cab update its state
		 * @param cab
		 * @param oldState
		 */
		public void onCabUpdate(Cab cab, int oldState);
		/**
		 * Passenger update its state
		 * @param p
		 */
		public void onPassengerUpdate(Passenger p);
		/**
		 * New cab added to the station
		 * @param cab
		 */
		public void onCabAdd(Cab cab);
		/**
		 * New passenger added to the station
		 * @param p
		 */
		public void onPassengerAdd(Passenger p);
	}
	
    /**
     * Lock used when maintaining queue of requested updates.
     */
	private static final Object sLock = new Object();

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
	
	private final StationContext mContext;

	private final String mStationName;
	private final int mMaxWaitingCount;
	private final TaxiMeter mDefaultTaxiMeter;
	
	private boolean mThreadRunning = false;
	
	private List<Runnable> mInitThreads;
	
	private StateChangeListener mStateListener;
	/**
	 * 
	 * @param name TaxiStation name
	 * @param maxWaitingCount maximum number of waiting taxi cabs
	 * @param defaultTaxiMeter taxi meter with defaults
	 */
	public TaxiStation(StationContext context, String name, int maxWaitingCount, TaxiMeter defaultTaxiMeter) {
		mContext = context;
		mStationName = name;
		mMaxWaitingCount = maxWaitingCount;
		mTaxiWaiting = new ArrayList<>();
		mTaxiBreak = new ArrayList<>();
		mTaxiDriving = new ArrayList<>();
		mPassengersList = new ArrayList<>();
		mPassengerExit = new ArrayList<>();
		mDefaultTaxiMeter = defaultTaxiMeter;
		mInitThreads = new ArrayList<>();
	}
	
	/**
	 * Initialize TaxiStation with cabs and passengers before station is running
	 * @param initCabs
	 * @param initPassengers
	 */
	@Override
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
	
	/**
	 * Get threads that needs to initiated
	 * @return 
	 */
	@Override
	public List<Runnable> getInitThreads() {
		return mInitThreads;
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
	        	Thread.sleep(300); 
	        } catch (InterruptedException e) {
	        	/* Allow thread to exit */
			}
		}
	}
	/**
	 * Get all taxi cabs in station
	 * @return
	 */
	@Override
	public List<Cab> getCabs() {
		synchronized (sLock) {		
			List<Cab> allCabs = new ArrayList<>();
			allCabs.addAll(mTaxiDriving);
			allCabs.addAll(mTaxiWaiting);
			allCabs.addAll(mTaxiBreak);
			return allCabs;
		}
	}
	/**
	 * 
	 * @return All passengers in station
	 */
	@Override
	public List<Passenger> getPassengers() {
		synchronized (sLock) {	
			List<Passenger> allPassengers = mPassengersList;
			allPassengers.addAll(mPassengerExit);
			return allPassengers;
		}
	}
	/**
	 * @return TaxiStation name
	 */
	@Override
	public String getStationName() {
		return mStationName;
	}
	/**
	 * @return default TaxiMeter
	 */
	@Override
	public TaxiMeter getDefaultTaxiMeter() {
		return mDefaultTaxiMeter;		
	}
	/**
	 * 
	 * @return maximum number of waiting taxi cabs
	 */
	@Override
	public int getMaxWaitingCount() {
		return mMaxWaitingCount;
	}		
	/**
	 * @return Number of taxi cabs in waiting state
	 */
	@Override
	public int getWaitingTaxiCount() {
		synchronized (sLock) {
			return mTaxiWaiting.size();
		}
	}
	/**
	 * @return Number of taxi cabs in driving state
	 */
	@Override
	public int getDrivingTaxiCount() {
		synchronized (sLock) {
			return mTaxiDriving.size();
		}
	}
	/**
	 * @return Number of passengers waiting in line
	 */
	@Override
	public int getWaitingPassengersCount()
	{
		synchronized (sLock) {
			return mPassengersList.size();
		}
	}
	/**
	 * @return Number of passengers that left the line angry
	 */
	@Override
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
	@Override
	public void addCab(Cab cab) {
		if (!mThreadRunning) {
			throw new UnsupportedOperationException("Cab can by added only to running stataion");
		}
		initCab(cab);
		new Thread(cab).start();
	}

	/**
	 * Add a passenger to the station
	 * @param cab
	 */
	@Override
	public void addPassenger(Passenger p) {
		if (!mThreadRunning) {
			throw new UnsupportedOperationException("Passenger can by added only to running stataion");
		}		
		initPassenger(p);
		new Thread(p).start();
	}
	
	/**
	 * Init a new cab in the station
	 * @param cab
	 */
	private void initCab(Cab cab) {
		//Set taxi meter
		cab.setMeter(createTaxiMeter());
		cab.setStationEventListener(this);
	}

	/**
	 * Init a new Passenger in the station
	 * @param p
	 */
	private void initPassenger(Passenger p) {
		p.setStationEventListener(this);
	}	
	/**
	 * Creates new instance of taxi meter
	 * @return
	 */
	private TaxiMeter createTaxiMeter() {
		try {
			return mDefaultTaxiMeter.clone();
		} catch (CloneNotSupportedException e) {
			LoggerWrapper.logException(TaxiStation.class.getName(), e);
		}
		return null;
	}
	/**
	 * Fill cab with passengers
	 */
	private void fillCab() {
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
			LoggerWrapper.logException(TaxiStation.class.getName(), e);
		}
	}

	/**
	 * Called when cab requests for a break;
	 * @param Cab instance of Cab
	 */
	@Override
	public void onBreakRequest(Cab cab) {
		synchronized (sLock) {
			int oldState = detectCabState(cab);
			mTaxiWaiting.remove(cab);
			cab.goToBreak();
			mTaxiBreak.add(cab);
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
			if (oldState == CAB_DRIVE) {
				mTaxiDriving.remove(cab);
			} else {
				mTaxiBreak.remove(cab);
			}
			cab.goToWaiting();
			mTaxiWaiting.add(cab);
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
	@Override
	public void registerStateListener(StateChangeListener listener) {
		mStateListener = listener;
	}

	@Override
	public void onPassengerUpdate(Passenger passenger) {
		mStateListener.onPassengerUpdate(passenger);
	}

	@Override
	public void onCabArrival(Cab cab, Receipt receipt) {
		ReceiptRepository receiptDao = mContext.getReceiptRepository();
		// TODO receiptDao.save(receipt);
	}
}

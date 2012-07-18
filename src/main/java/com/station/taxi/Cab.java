package com.station.taxi;

import com.station.taxi.events.CabEventListener;
import com.station.taxi.events.IStationEventListener;
import java.util.*;

/**
 * Tax cab object
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
public class Cab implements ICab { 
    /**
     * Lock used when maintaining queue of requested updates.
     */
	final private static Object sLock = new Object();
	
	public static final int MAX_PASSANGERS = 4;
	private static final int ONE_SECOND = 1000;
	/**
	 * Status constants
	 */
	private static final int STATUS_INIT=0;
	private static final int STATUS_BREAK=1;
	private static final int STATUS_DRIVING=2;
	private static final int STATUS_WAITING=3;
	/**
	 * Wait status possible actions
	 */
	public static final String WAIT_NEWSPAPPER = "readNewsPaper";
	public static final String WAIT_EAT = "eat";	
	public static final String WAIT_DRINK = "drink";
	
	private IStationEventListener mStationListener;
	private List<IPassenger> mPassangers;
	private String mWhileWaiting;
	private int mNumber;
	private TaxiMeter mMeter;
	private int mCabStatus = STATUS_INIT;
	/**
	 * Time in milliseconds
	 */
	private int mDrivingTime = 0;
	private List<Receipt> mReciptsList;
	private boolean mThreadRunning = false;
	private String mDestination;	
	/**
	 * Time in milliseconds
	 */
	private int mBreakTime;
	private List<CabEventListener> mEventListeners = new ArrayList<>();
	
	private ICab mAopProxy;
	
	/**
	 * 
	 * @param num Cab number
	 * @param whileWaiting action while waiting?
	 */
	public Cab(int num, String whileWaiting) {
		init(num, whileWaiting);
	}
	
	public void setAopProxy(ICab proxy) {
		mAopProxy = proxy;
	}

	/**
	 * 
	 * @param num
	 * @param whileWaiting 
	 */
	private void init(int num, String whileWaiting) {
		mNumber = num;
		mWhileWaiting = whileWaiting;
		// safe for threads
		mPassangers = Collections.synchronizedList(new ArrayList<IPassenger>(MAX_PASSANGERS));
		mReciptsList = Collections.synchronizedList(new ArrayList<Receipt>());
	}

	/**
	 * Cab Id Number
	 * @return
	 */
	@Override
	public int getNumber() {
		return mNumber;
	}
	/**
	 * While waiting action
	 * @return 
	 */
	@Override
	public String getWhileWaiting()
	{
		return mWhileWaiting;
	}
	/**
	 * Return true if cab driving
	 * @return
	 */
	@Override
	public boolean isDriving() {
		synchronized (sLock) {
			return mCabStatus == STATUS_DRIVING;
		}
	}
	/**
	 * Get driving time in seconds
	 * @return
	 */
	@Override
	public int getDrivingTime() {
		return mDrivingTime;
	}
	/**
	 * Return true if cab on break
	 * @return
	 */
	@Override
	public boolean isOnBreak() {
		synchronized (sLock) {
			return mCabStatus == STATUS_BREAK;
		}
	}
	/**
	 * Return true if cab waiting for passengers
	 * @return
	 */
	@Override
	public boolean isWaiting() {
		synchronized (sLock) {
			return mCabStatus == STATUS_WAITING;
		}
	}
	/**
	 * Register station listener
	 * @param listener
	 */
	@Override
	public void setStationEventListener(IStationEventListener listener) {
		mStationListener = listener;
	}
	/**
	 * Register cab event listener
	 * @param listener
	 */
	@Override
	public void addCabEventListener(CabEventListener listener) {
		synchronized (sLock) {
			mEventListeners.add(listener);
		}
	}
	
	@Override
	public double getTotalEarning(){
		double total = 0;
		for (Receipt r : mReciptsList) { //a map function like in most scripting and functional lanugage would be great here
			total += r.getPrice();
		}
		return total;
	}
	
	@Override
	public double getTotalEarning(Date start,Date end){
		double total =0;
		for (Receipt r : mReciptsList) {
			if(r.getStartTime().after(start) && r.getStartTime().before(end)) {
				total += r.getPrice();
			}
		}
		return total;
	}
	/**
	 * Add passenger to Cab
	 * @param passenger
	 * @throws Exception
	 */
	@Override
	public void addPassenger(IPassenger passenger) throws Exception {
		synchronized (sLock) {
			if (mPassangers.size() > 0) {
				IPassenger first = mPassangers.get(0);
				if (!first.getDestination().equals(passenger.getDestination())) {
					throw new Exception("Wrong destination");
				}
			}
			mPassangers.add(passenger);
		}
		passenger.enterCab();
	}
	/**
	 * Driving destination
	 * @return
	 */
	@Override
	public String getDestination() {
		synchronized (sLock) {
			if (mCabStatus != STATUS_DRIVING) {
				throw new RuntimeException("Cab is not driving anywhere");
			}
			return mDestination;
		}
	}
	/**
	 * List of passengers inside the cab
	 * @return
	 */
	@Override
	public List<IPassenger> getPassegners() {
		synchronized (sLock) {
			return mPassangers;
		}
	}
	/**
	 * Current break time
	 * @return
	 */
	@Override
	public int getBreakTime() {
		return mBreakTime;
	}
	/**
	 * Return instance of current TaxiMeter
	 * @return
	 */
	@Override
	public TaxiMeter getMeter() {
		synchronized (mMeter) {
			return mMeter;			
		}
	}	
	/**
	 * Set TaxiMeter instance
	 * @param meter
	 */
	@Override
	public void setMeter(TaxiMeter meter) {
		mMeter = meter;
	}
	/**
	 * Check if cab is full
	 * @return
	 */
	@Override
	public boolean isFull() {
		synchronized (sLock) {
			return mPassangers.size() == MAX_PASSANGERS;
		}
	}

	@Override
	public void interrupt() {
		mThreadRunning = false;
		notify(CabEventListener.INTERRUPT);
	//	super.interrupt();
	}	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
	// Tell station that cab thread is started and running
		notify(CabEventListener.START);
		
		mStationListener.onCabReady(mAopProxy);
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
			
				Thread.sleep(ONE_SECOND);
	        } catch (InterruptedException e) {
	        	/* Allow thread to exit */
			}
		}
	}
	
	/**
	 * Waiting in station for passengers
	 * @throws InterruptedException
	 */
	private void waiting() throws InterruptedException {
		Random rand = new Random();
		int value = rand.nextInt(100);
		if (value < 10) {		
			mStationListener.onBreakRequest(mAopProxy);
		}
	}
	/**
	 * Do a whileWaiting action for mBreakTime 
	 * @throws InterruptedException 
	 */
	private void onBreak() throws InterruptedException {
		mBreakTime -= ONE_SECOND;
		notify(CabEventListener.INBREAK);
		if (mBreakTime <=0) {
			mStationListener.onWaitingRequest(mAopProxy);
		}
	}
	/**
	 * Drive to the destination
	 * @throws InterruptedException 
	 */
	private void driving() throws InterruptedException {
		mDrivingTime += ONE_SECOND;
		synchronized (mMeter) {
			mMeter.increase();
		}
		notify(CabEventListener.DRIVING);
	}
	/**
	 * Tells to cab that it arrived to destination
	 */
	@Override
	public void arrive() {
		synchronized (sLock) {
			if (mCabStatus != STATUS_DRIVING) {
				throw new RuntimeException("Cab is not driving");
			}
			notify(CabEventListener.ARRIVED_DESTINATION);		
			int size = mPassangers.size();
			mReciptsList.add(mMeter.reciept(size));
			notifyArrival();
			mPassangers.clear();
			mStationListener.onWaitingRequest(mAopProxy);
		}
	}
	/**
	 * Start Driving
	 * 
	 */
	@Override
	public void drive(){
		synchronized (sLock) {
			if (mPassangers.isEmpty()) {
				throw new RuntimeException("Empty cab");
			}
			mDestination = mPassangers.get(0).getDestination();
			mMeter.reset();
			mMeter.start();		
			mCabStatus = STATUS_DRIVING;
			notify(CabEventListener.DRIVE_DESTINATION);
		}
	}
	/**
	 * Go to waiting state
	 */
	@Override
	public void goToWaiting() {
		synchronized (sLock) {		
			mCabStatus = STATUS_WAITING;
			notify(CabEventListener.WAITING);		
		}
	}
	/**
	 * Go to break
	 */
	@Override
	public void goToBreak() {
		synchronized (sLock) {
			Random rand = new Random();	
			mBreakTime  = rand.nextInt(10)+5 * 1000;		
			mCabStatus = STATUS_BREAK;
			notify(CabEventListener.GOTO_BREAK);
		}
	}

	/**
	 * Notify passengers and station about end of the trip...
	 */
	private void notifyArrival() {
		for(IPassenger p: mPassangers) {
			p.onArrival(mAopProxy, mMeter.getCurrentValue() / mPassangers.size());
		}
	}
	
	/**
	 * Notify event listeners
	 * @param type
	 */
	private void notify(int type) {
		synchronized (sLock) {		
			for(CabEventListener listener: mEventListeners) {
				listener.update(type, this);
			}
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Thread#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
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
			for (Iterator<IPassenger> it = mPassangers.iterator(); it.hasNext();) {
				IPassenger p = it.next();
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

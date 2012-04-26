package com.station.taxi;

import java.util.Date;
/**
 * Receipt is used to keep the receipts in the cab object.
 * @author alex
 * @author Eran Zimbler
 * @version 0.1
 *
 */
public class Receipt {
	private double mPrice;
	private Date mStartTime;
	private Date mEndTime;
	private int mPassengersCount;
	
	
	public Receipt(Date start,Date end,double price,int passengers) {
		mStartTime = start;
		mEndTime = end;
		mPrice = price;
		mPassengersCount = passengers;
	}
	
	public double getPrice() {
		return mPrice;
	}
	public Date getStartTime() {
		return mStartTime;
	}
	public Date getEndTime() {
		return mEndTime;
	}
	public int getPassengersCount() {
		return mPassengersCount;
	}
	/**
	 * Calculates the time of the ride in seconds
	 * @return duration of ride in seconds as long
	 */
	public long getRideDuration(){
		return (mEndTime.getTime()-mStartTime.getTime())/1000; 
	}
}

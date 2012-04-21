package com.station.taxi;

import java.util.Date;

import javax.xml.datatype.Duration;

public class Recipt {
	private double mPrice;
	private Date mStartTime;
	private Date mEndTime;
	private int mPassengersCount;
	
	
	public Recipt() {
		mStartTime = new Date();
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
	public void setRidePrice(double price){
		mPrice = price;
	}
	public void setPassengersAmount(int num){
		mPassengersCount = num;
	}
	public void setEndTime(){
		mEndTime = new Date();
	}
	/**
	 * Calculates the time of the ride in seconds
	 * @return duration of ride in seconds as long
	 */
	public long getRideDuration(){
		return (mEndTime.getTime()-mStartTime.getTime())/1000; 
	}
}

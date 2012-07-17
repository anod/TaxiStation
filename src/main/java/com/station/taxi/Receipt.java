package com.station.taxi;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * Receipt is used to keep the receipts in the cab object.
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 *
 */
@Entity
public class Receipt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private long mId;

	@Column(name="price")
	private double mPrice;

	@Column(name="start_time")
	@Temporal(TemporalType.DATE)
	private Date mStartTime;

	@Column(name="end_time")
	@Temporal(TemporalType.DATE)
	private Date mEndTime;

	@Column(name="passenger_count")
	private int mPassengersCount;

	public Receipt() {
	}
	
	
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

	/**
	 * @param mId the mId to set
	 */
	public void setId(long id) {
		this.mId = id;
	}

	/**
	 * @param price the mPrice to set
	 */
	public void setPrice(double price) {
		this.mPrice = price;
	}

	/**
	 * @param startTime the mStartTime to set
	 */
	public void setStartTime(Date startTime) {
		this.mStartTime = startTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.mEndTime = endTime;
	}

	/**
	 * @param mPassengersCount the mPassengersCount to set
	 */
	public void setPassengersCount(int passengersCount) {
		this.mPassengersCount = passengersCount;
	}
}

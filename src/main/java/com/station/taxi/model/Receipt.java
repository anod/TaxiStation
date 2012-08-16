package com.station.taxi.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * Receipt is used to keep the receipts in the cab object.
 *
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
@Entity
@Table(name = "RECEIPTS")
public class Receipt implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long mId;
	@Column(name = "price")
	private double mPrice;
	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mStartTime;
	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date mEndTime;
	@Column(name = "passenger_count")
	private int mPassengersCount;
	@Column(name = "cabID")
	private int mCabID;

	public Receipt() {
	}

	/**
	 *
	 * @param start
	 * @param end
	 * @param price
	 * @param passengers
	 */
	public Receipt(Date start, Date end, double price, int passengers) {
		mStartTime = start;
		mEndTime = end;
		mPrice = price;
		mPassengersCount = passengers;
	}

	public Long getId() {
		return mId;
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
	 *
	 * @return duration of ride in seconds as long
	 */
	public long getRideDuration() {
		return (mEndTime.getTime() - mStartTime.getTime()) / 1000;
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

	public int getCabID() {
		return mCabID;
	}

	public void setCabID(int cabID) {
		mCabID = cabID;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Receipt #");
		sb.append(mId);
		sb.append(", start time: ");
		sb.append(mStartTime.toGMTString());
		sb.append(", end time: ");
		sb.append(mEndTime.toGMTString());
		sb.append(", price: ");
		sb.append(mPrice);
		sb.append(", count: ");
		sb.append(mPassengersCount);

		return sb.toString();

	}
}

package com.station.taxi.db;

import com.station.taxi.model.Receipt;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * Receipts storage interface
 * @author alex
 */
public interface ReceiptStorage {
	
	/**
	 * Load receipts
	 * @return 
	 */
	public Iterable<Receipt> load();
	
	/**
	 * Save a new receipt
	 * @param receipt 
	 */
	public void save(Receipt receipt);
	
	/**
	 * List receipts by passengers count
	 * @param i
	 * @return 
	 */
	public Iterable<Receipt> findByPassengersCount(int i);

	/**
	 * List receipts by cab number
	 * @param i
	 * @return 
	 */
    public Iterable<Receipt> findByCabID(int i);

	/**
	 * Find receipts by cab num and start time
	 * @param i
	 * @param three
	 * @return 
	 */
    public Iterable<Receipt> findByCabIDandStartTime(int i, Date three);  
}

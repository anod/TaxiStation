package com.station.taxi.db;

import com.station.taxi.model.Receipt;

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
}

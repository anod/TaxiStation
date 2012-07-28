/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.db;

import com.station.taxi.model.Receipt;

/**
 *
 * @author alex
 */
public interface ReceiptStorage {
	
	public Iterable<Receipt> load();
	
	public void save(Receipt receipt);
}

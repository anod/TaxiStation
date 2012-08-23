package com.station.taxi.db;

import com.station.taxi.model.Receipt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SQL JDBC implementation of Receipt Storage
 *
 * @author Eran Zimbler
 */
public class SQLReceiptStorage implements ReceiptStorage {

	@Override
	public Iterable<Receipt> load() {

		return DBHandler.selectQuery("SELECT * FROM RECEIPTS");
	}

	@Override
	public void save(Receipt receipt) {
		
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
		
		String insertQuery = String.format(
			"INSERT INTO RECEIPTS (START_TIME,END_TIME,PRICE,PASSENGER_COUNT,CABID) VALUES('%s','%s',%f,%d,%d)",
			df.format(receipt.getStartTime()), 
			df.format(receipt.getEndTime()), 
			receipt.getPrice(), 
			receipt.getPassengersCount(), 
			receipt.getCabID()
		);
		DBHandler.insertQuery(insertQuery);
	}



    public Iterable<Receipt> findByPassengersCount(int i) {
        return DBHandler.selectQuery(
			String.format("SELECT * from RECEIPTS WHERE PASSENGER_COUNT = %d", i)
		);
    }

    public Iterable<Receipt> findByCabID(int i) {
       return DBHandler.selectQuery(
			String.format("SELECT * from RECEIPTS WHERE CABID = %d", i)
		);
    }

    public Iterable<Receipt> findByCabIDandStartTime(int i, Date three) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
	
        return DBHandler.selectQuery(
			String.format("SELECT * from RECEIPTS WHERE CABID = %d AND START_TIME >= '%s'",i,df.format(three))
		);
    }
}

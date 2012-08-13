/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.db;

import com.station.taxi.model.Receipt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author srgrn
 */
public class SQLRecieptStorage implements ReceiptStorage {
	private ArrayList<Receipt> list = null;
    @Override
	public Iterable<Receipt> load() {

            ArrayList<ResultSet> results = DBHandler.selectQuery("Select * from RECEIPT");
            for (ResultSet r : results)
            {
                list.add(convert(r));
            }

            return (Iterable<Receipt>) list.iterator();
	}

	@Override
	public void save(Receipt receipt) {
            ArrayList<ResultSet> results = DBHandler.selectQuery("Select max(id) as OUTPUT from RECEIPT");
        int id = 0;
            try {
             id = (results.get(0)).getInt("OUTPUT");
        } catch (SQLException ex) {
            Logger.getLogger(SQLRecieptStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
            String insertQuery = String.format("insert into RECIEPTS (ID,START_TIME,END_TIME,PRICE,PASSENGER_COUNT,CABID) VALUES({0},{1},{2},{3},{4},{5})",
                                        id+1,receipt.getStartTime(),receipt.getEndTime(),receipt.getPrice(),receipt.getPassengersCount(),receipt.getCabID());
            DBHandler.InsertQuery(insertQuery);
	}
        
        private Receipt convert(ResultSet rs)
        {
        Receipt r = null;
            try {
            r = new Receipt(rs.getDate("START_TIME"), rs.getDate("END_TIME"), rs.getDouble("PRICE"), rs.getInt("PASSENGER_COUNT"));
            r.setCabID(rs.getInt("CABID"));
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLRecieptStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
            return r;
        }
}

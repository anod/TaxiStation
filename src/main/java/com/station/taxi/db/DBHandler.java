package com.station.taxi.db;

import com.station.taxi.model.Receipt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handle communication with DB
 * @author Eran Zimbler
 */
public class DBHandler {
    private static Connection conn = null;
    private static String connectionString = "jdbc:derby://localhost:1527/TaxiStation;user=root;password=toor;";
	
	/**
	 * Connect to db
	 * @param db 
	 */
    private static void createConnection(String db)
    {
        if (conn!= null) {
            return;
		}
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(db); 
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException except)
        {
        }
    };
	
	/**
	 * Initialize db connection
	 */
    public static void initDB()
    {
        if(conn != null) {
			return;
		}
		createConnection(connectionString + "create=true;");
		try {
			   Statement createTable = conn.createStatement();
			   String s = "create table RECEIPTS ("
					   + " \"ID\" INTEGER not null primary key generated always as identity (start with 1 increment by 1),"
					   + "\"START_TIME\" TIMESTAMP not null,"
					   + "\"END_TIME\" TIMESTAMP,"
					   + "\"PASSENGER_COUNT\" INTEGER,"
					   + "\"PRICE\" DOUBLE,"
					   + "\"CABID\" INTEGER not null"
					   + ");";
			   createTable.execute(s);
		} catch (SQLException e) {
		}
    }
	
	/**
	 * Run insert query
	 * @param query
	 * @return 
	 */
    public static boolean insertQuery(String query)
    {
        boolean ret = false;
        if(conn == null)
        {
            createConnection(connectionString);
        }
        try {
            Statement selectStatement = conn.createStatement();
            ret = selectStatement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
	
	/**
	 * Run select query
	 * @param query
	 * @return 
	 */
    public static ArrayList<Receipt> selectQuery(String query) 
    {
        ArrayList<Receipt> ret = new ArrayList<>();
        if(conn == null)
        { createConnection(connectionString); }
        try {
            Statement selectStatement = conn.createStatement();
            ResultSet rs = selectStatement.executeQuery(query);
            while(rs.next())
            {
               ret.add(convert(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
		
        return ret;
    }
	
	/**
	 * Convert ResultSet into Receipt
	 * @param rs
	 * @return 
	 */
	private static Receipt convert(ResultSet rs) {
		Receipt r = null;
		try {
			r = new Receipt(
				rs.getDate("START_TIME"), 
				rs.getDate("END_TIME"), 
				rs.getDouble("PRICE"), 
				rs.getInt("PASSENGER_COUNT")
			);
			r.setCabID(rs.getInt("CABID"));

		} catch (SQLException ex) {
			Logger.getLogger(SQLReceiptStorage.class.getName()).log(Level.SEVERE, null, ex);
		}
		return r;
	}
}
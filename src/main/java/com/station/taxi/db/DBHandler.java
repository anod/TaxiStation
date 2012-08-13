
package com.station.taxi.db;

/**
 *
 * @author srgrn
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
    private static Connection conn = null;
    private static String connectionString = "jdbc:derby://localhost:1527/TaxiStation;user=root;password=toor;";
    private static void createConnection(String db)
    {
        if (conn!= null)
            return;
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
    public static void InitDB()
    {
        if(conn != null)
        {}
        else
        {
            createConnection(connectionString + "create=true;");
            try {
                   Statement createTable = conn.createStatement();
                   String s = "create table RECIPT ("
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
    }
    public static boolean InsertQuery(String Query)
    {
        boolean ret = false;
        if(conn == null)
        {
            createConnection(connectionString);
        }
        try {
            Statement selectStatement = conn.createStatement();
            ret = selectStatement.execute(Query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    public static ArrayList<ResultSet> selectQuery(String Query) 
    {
        ArrayList<ResultSet> ret = new ArrayList<>();
        if(conn == null)
        { createConnection(connectionString); }
        try {
            Statement selectStatement = conn.createStatement();
            selectStatement.execute(Query);
            while( ((selectStatement.getMoreResults() != false) && (selectStatement.getUpdateCount() != -1)))
            {
               ret.add(selectStatement.getResultSet());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
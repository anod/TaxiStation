
package com.station.taxi.db;

/**
 *
 * @author srgrn
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class handler {
    private static Connection conn = null;
    private static String connectionString = "jdbc:derby://localhost:1527/taxi;";
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
    private static void InitDB()
    {
        if(conn != null)
        {}
        else
        {
            createConnection(connectionString + "create=true;");
            try {
                   Statement createTable = conn.createStatement();
                   String s = "create table RECIPT ("
                           + " \"id\" INTEGER not null primary key generated always as identity (start with 1 increment by 1),"
                           + "\"starttime\" TIMESTAMP not null,"
                           + "\"endtime\" TIMESTAMP,"
                           + "\"passengerscount\" INTEGER,"
                           + "\"price\" DOUBLE,"
                           + "\"cabno\" INTEGER not null"
                           + ");";
                   createTable.execute(s);
            } catch (SQLException e) {
            }
            
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ameen
 */
public class DBHandler {
        private static final String dbURL = "jdbc:derby:database/base;create=true";
		//    private static final String dbURL = "jdbc:derby://localhost:1527/la;create=true";
        Connection conn;
    Statement stmt;
    String Table1="AllSongs";

    DBHandler()
	{
          createConnection();
	}
    private void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(dbURL, "root", "root");
            String query="CREATE TABLE "+Table1+"(TrackName string,Artist string,Album string,Location string);";
            execute(query);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private  void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public Boolean execute(String query) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }
    
}

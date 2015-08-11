/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;

/**
 *
 * @author biendltb
 */
public class DAO {
    
    // Declare connection
    Connection con;
    Statement sta;
    
    /*
    * Init connection and statement
    */
    public DAO() {
        try {
            con = ConnectionDatabase.getConnection();
            sta = con.createStatement();
        } catch (SQLException ex) {
        }
    }
    
    
    /*
    * Execute a query to SELECT
    * @param a SQL query string
    * @return A Result set contain the result of query
    */
    public ResultSet query(String query) throws SQLException {
        ResultSet result = null;
        
        result = sta.executeQuery(query);
        
        return result;
    }
    
    /*
    * Execute a query to UPDATE, INSERT, DELETE,...
    */
    public void update(String query) {
        try {
            sta.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() throws SQLException {
        if(con != null)
            con.close();
        if(sta != null)
            sta.close();
    }
}

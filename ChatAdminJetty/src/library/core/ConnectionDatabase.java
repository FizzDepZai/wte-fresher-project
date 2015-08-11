package library.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.ConnectionPoolDataSource;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tript
 */
public class ConnectionDatabase {

  
    public static Connection getConnection(){
//        Connection con =null;
//        try {
//            //Use connection pool
//            Context initialContext = new InitialContext();
//            Context envContext
//                    = (Context) initialContext.lookup("java:/comp/env");
//            DataSource ds =(DataSource) envContext.lookup("jdbc/ChatAdmin");
//            con = ds.getConnection();
//            
//        } catch (NamingException ex) {
//            Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
//        }

        Connection con=null;
        String user="root";
        String pass="123456";
        String url = "jdbc:mysql://localhost:3306/ChatAdmin";
        String driver="com.mysql.jdbc.Driver";
        
        
        
        try {
            try {
                Class.forName(driver).newInstance();
                con=DriverManager.getConnection(url,user,pass);
            }  catch (IllegalAccessException ex) {
                Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
}

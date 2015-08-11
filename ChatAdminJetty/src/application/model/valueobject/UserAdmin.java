/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model.valueobject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import library.core.ConnectionDatabase;

/**
 *
 * @author ADmin
 */
public class UserAdmin {

    private String username;
    private String password;

    public UserAdmin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean checkLogin() {
        try {
            Connection conn = ConnectionDatabase.getConnection();
            String sql = "Select * from tbl_User where Username =? and Password =?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, username.toLowerCase());
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            boolean result = rs.next();   //neu co ban ghi thi result = true
            rs.close();
            stm.close();
            conn.close();
            if (result) {
                return true;    // tra ve true neu select ra dung gia tri da nhap vao
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
            return false;

    }
}

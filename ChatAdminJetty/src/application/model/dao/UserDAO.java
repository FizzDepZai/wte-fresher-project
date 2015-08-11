

package application.model.dao;

import application.model.valueobject.UserPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;

/**
 *
 * @author biendltb
 */
public class UserDAO {
    // Declare connection
    DAO dao;
    List<UserPOJO> listUser = new ArrayList<>();
    
    public UserDAO() {
        dao = new DAO();
    }
    /*
    * Get all user form tbl_User
    * @return List of User POJO
    */
    public List<UserPOJO> getUsersByRoomId(int RoomId) {
        // List contain user POJO
        ResultSet result = null;
        String query = "SELECT UserId, Username, FirstName, LastName, BirthDay, Email FROM tbl_User WHERE RoomId = " + Integer.toString(RoomId);
        try {
            // Execute query 
            result = dao.query(query);
        
            while(result.next()) {
                UserPOJO user = new UserPOJO(result.getInt(1), result.getString(2), // userId, username
                        result.getString(3), result.getString(4), // firstName, lastName
                        result.getString(5), result.getString(6)); // birthday, email
                listUser.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(result != null)
                    result.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listUser;
    }
    public boolean checkLogin(UserPOJO admin) {
       Connection con = null ;
       PreparedStatement stm = null ;
       ResultSet rs = null ;
        try {
             con = ConnectionDatabase.getConnection();
            String sql = "Select * from tbl_User where Username =? and Password =?";
             stm = con.prepareStatement(sql);
            stm.setString(1, admin.userName.toLowerCase());
            stm.setString(2, admin.password);
             rs = stm.executeQuery();
            boolean result = rs.next();   //neu co ban ghi thi result = true
         
            if (result) {
                return true;    // tra ve true neu select ra dung gia tri da nhap vao
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
            return false;
    }
    /*
    * Kich user out of room
    * @param userId
    */
    public void kickUserByUserId(int userId) {
        //set null --> kick khoi room
        String query = "UPDATE tbl_User SET RoomId= null WHERE UserId=" + Integer.toString(userId);
        dao.update(query);
    }
    
    /*
    * Get room id by user id
    * @param user id
    * @return room id
    */
    public int getRoomIdByUserId(int userId) {
        String query = "SELECT RoomId FROM tbl_User WHERE UserId = " + Integer.toString(userId);
        ResultSet result = null;
        int roomId = -1;
        try {
            result = dao.query(query);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(result.next())
                roomId = result.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return roomId;
    }
    
}


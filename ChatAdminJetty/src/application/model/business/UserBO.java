/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.business;

import application.middleware.ChatAdminHandler;
import application.model.dao.EmotionDAO;
import application.model.dao.UserDAO;
import application.model.valueobject.UserPOJO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author biendltb
 */
public class UserBO implements IUserBO {
    UserDAO userDAO;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserBO.class);
    ChatAdminHandler handler = new ChatAdminHandler();
    public UserBO() {
        userDAO = new UserDAO();
    }
    
    @Override
    public List<UserPOJO> getUsersByRoomId(int roomId) {
        return userDAO.getUsersByRoomId(roomId);
    }
    

    @Override
    public void kickUserByUserId(int userId) {
        userDAO.kickUserByUserId(userId);
    }

    @Override
    public int getRoomIdByUserId(int userId) {
        return userDAO.getRoomIdByUserId(userId);
    }

    @Override
    public boolean checkLogin(UserPOJO admin) {
//        try {
//            handler.checkAdmin(admin.userName, admin.password);
//        } catch (TException ex) {
//          logger.error("checkLogin:"+ex);
//        }
        return true;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.business;

import application.model.valueobject.UserPOJO;
import java.util.List;

/**
 *
 * @author biendltb
 */
public interface IUserBO {
    
    /*
    * Get all user by room id
    * @param room id
    * @return list of UserPOJO
    */
    List<UserPOJO> getUsersByRoomId(int roomId);
    
    /*
    * Kick user out of room by user id
    * @param user id
    */
    public void kickUserByUserId(int userId);
    
    /*
    * Get room id by user id
    * @param user id
    * @return room id
    */
    public int getRoomIdByUserId(int userId);
    
    public boolean checkLogin(UserPOJO admin);
}

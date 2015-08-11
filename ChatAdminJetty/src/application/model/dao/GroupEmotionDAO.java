/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model.dao;

import application.middleware.ChatAdminHandler;
import application.model.valueobject.EmotionPOJO;
import application.model.valueobject.GroupEmotionPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;
import org.apache.thrift.TException;
import org.eclipse.jetty.continuation.Continuation;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 *
 * @author tript
 */
public class GroupEmotionDAO {
    
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GroupEmotionDAO.class);
    ChatAdminHandler handler = new ChatAdminHandler();
    Connection con = null;
    ResultSet rs = null;
    
    public List<GroupEmotionPOJO> getAllGroupEmotion() {
        List<GroupEmotionPOJO> listGroupEmotion = new ArrayList<>();
        
        String strListGroupEmotion = "";
        try {
            strListGroupEmotion = handler.gelAllGroupEmotion();
        } catch (TException ex) {
            logger.error("getAllGroupEmotion:" + ex);
        }
        if("".equals(strListGroupEmotion)){
            return listGroupEmotion;
        }
        Object obj = JSONValue.parse(strListGroupEmotion);
        Map listGroupEmotionJSON = (Map) obj;
        Iterator iter = listGroupEmotionJSON.entrySet().iterator();
        while (iter.hasNext()) {
            
            Map.Entry entry = (Map.Entry) iter.next();
            GroupEmotionPOJO groupEmotion = new GroupEmotionPOJO();
            
            String groupIdStr = (String) entry.getKey();
            try {
                groupEmotion.id = Integer.parseInt(groupIdStr.trim());
            } catch (NumberFormatException ex) {
                logger.error("Format group id in getAllGroupEmotion:" + ex);
            }
            groupEmotion.name = (String) entry.getValue();
            listGroupEmotion.add(groupEmotion);
        }
        
//        listGroupEmotion.add(new GroupEmotionPOJO(1, "deault"));
        return listGroupEmotion;
        
    }
    
    public String addGroupEmotion(String groupEmotionName) {
        String groupEmotionId = "";
        try {
            groupEmotionId = handler.addGroupEmotion(groupEmotionName);
            
        } catch (TException ex) {
            logger.error("addGroupEmotion:" + ex);
        }
        return groupEmotionId;
    }
    
    public boolean deleteGroupEmotion(String groupId) {
        boolean isDelete = true;
        try {
            isDelete = handler.deleteGroupEmotion(groupId);
        } catch (TException ex) {
            logger.error("deleteGroupEmotion:" + ex);
        }
        return isDelete;
    }
    
    public boolean editGroupEmotion(GroupEmotionPOJO groupEmotion) {
        PreparedStatement pst = null;
        boolean isEdit = true;
        try {
            con = ConnectionDatabase.getConnection();
            
            String sql = "Update  tbl_GroupEmotion set Name = ? where GroupEmotionId = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, groupEmotion.name);
            pst.setInt(2, groupEmotion.id);
            
            int row = pst.executeUpdate();
            if (row == 0) {
                isEdit = false;
            }
            
        } catch (SQLException ex) {
            isEdit = false;
            Logger.getLogger(EmotionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return isEdit;
    }
}

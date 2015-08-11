/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model.dao;

import application.middleware.ChatAdminHandler;
import application.model.business.EmotionBOImpl;
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
import library.core.Registry;
import library.thrift.EmotionItem;
import org.apache.thrift.TException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import sun.awt.AWTAccessor;

/**
 *
 * @author tript
 */
public class EmotionDAO {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EmotionDAO.class);
    ChatAdminHandler handler = new ChatAdminHandler();

    Connection con = null;
    ResultSet rs = null;

    public List<EmotionPOJO> getAll() {
        List<EmotionPOJO> listEmotion = new ArrayList<>();

        String strListEmotion = null;
        try {
            strListEmotion="";
//            strListEmotion = handler.getEmotionWithGroup("group1");
//            EmotionBOImpl emotionImpl = new EmotionBOImpl();
//            List<GroupEmotionPOJO> listGroupEmotion = emotionImpl.getGroupEmotion();
//
//            strListEmotion = handler.loadAllEmotion();
//            for(int i =0;i<listGroupEmotion.size();i++){
//                strListEmotion += handler.getEmotionWithGroup(Integer.toString(listGroupEmotion.get(i).id));
//            }
          strListEmotion =   handler.loadAllEmotion();
        } catch (TException ex) {
            logger.error("loadAllEmotion:" + ex);
        }
        if ("".equals(strListEmotion) || strListEmotion == null) {
            return listEmotion;
        }
        Object obj = JSONValue.parse(strListEmotion);
        if (obj == null) {
            return listEmotion;
        }
        JSONArray listEmotionJSON = (JSONArray) obj;
        for (int i = 0; i < listEmotionJSON.size(); i++) {
            EmotionPOJO emotion = new EmotionPOJO();
            Map emotionObj = (Map) listEmotionJSON.get(i);
            Iterator iter = emotionObj.entrySet().iterator();
//            System.out.println("==iterate result==");
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                switch (entry.getKey().toString()) {
                    case "title":
                        emotion.description = (String) entry.getValue();
                        break;
                    case "style":
                        emotion.linkImage = (String) entry.getValue();
                        break;
                    case "alt":
                        emotion.emotionId = Integer.parseInt((String) entry.getValue());
                        break;
                    case "group":
                        //set tam la 1
                        Long groupId  = (Long)entry.getValue();
                        emotion.groupEmotionId =Integer.parseInt(Long.toString(groupId));

                }
            }
            listEmotion.add(emotion);

        }

        return listEmotion;

    }

    public String checkImageEmotionExist(String[] arrLinkImage, int groupEmotionId) {
        String linkImageExist = "";
        PreparedStatement pst = null;

        try {
            con = ConnectionDatabase.getConnection();
            String sql = "select Count(*) from tbl_Emotion where LinkImage like ? and GroupEmotionId = ?";
            pst = con.prepareStatement(sql);

            for (int i = 0; i < arrLinkImage.length; i++) {
                pst.setString(1, "%" + arrLinkImage[i] + "%");
                pst.setInt(2, groupEmotionId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(1) > 0) {
                        //if have in db set link equal empty
                        linkImageExist += arrLinkImage[i] + "; ";
                        arrLinkImage[i] = "";
                    }
                }

            }
            if (linkImageExist.length() > 1) {
                linkImageExist = linkImageExist.substring(0, linkImageExist.length() - 2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
        return linkImageExist;
    }

    public boolean addEmotion(String listEmotionValueJSON) {
        boolean isInsert = true;
        try {
            //gui xuong toi thieu 10 emotions
            handler.addEmotion(listEmotionValueJSON);
        } catch (TException ex) {
            logger.error("addEmotion: " + ex);
        }
        return isInsert;

    }

    public List<EmotionPOJO> getEmotionWithGroup(String groupId) {
        List<EmotionPOJO> listEmotion = new ArrayList<>();

        String strListEmotion = null;
        try {
//            strListEmotion = handler.getEmotionWithGroup("group1");
            strListEmotion = handler.getEmotionWithGroup(groupId);
        } catch (TException ex) {
            logger.error("loadAllEmotion:" + ex);
        }

        Object obj = JSONValue.parse(strListEmotion);
        if(obj ==null){
            return listEmotion;
        }
        JSONArray listEmotionJSON = (JSONArray) obj;
        for (int i = 0; i < listEmotionJSON.size(); i++) {
            EmotionPOJO emotion = new EmotionPOJO();
            Map emotionObj = (Map) listEmotionJSON.get(i);
            Iterator iter = emotionObj.entrySet().iterator();
//            System.out.println("==iterate result==");
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                switch (entry.getKey().toString()) {
                    case "title":
                        emotion.description = (String) entry.getValue();
                        break;
                    case "style":
                        emotion.linkImage = (String) entry.getValue();
                        break;
                    case "alt":
                        emotion.emotionId = Integer.parseInt((String) entry.getValue());
                        break;
                    case "group":
                        //set tam la 1
                        emotion.groupEmotionId = 1;

                }
            }
            listEmotion.add(emotion);

        }

        return listEmotion;
    }

    public boolean deleteEmotion(int id, String columnName) {
//        PreparedStatement pst = null;
        boolean isDelete = true;

        if (columnName.equals("EmotionId")) {
            try {
                handler.deleteEmotion(id);
            } catch (TException ex) {
                logger.error("deleteEmotion:" + ex);
            }
        } else if (columnName.equals("GroupEmotionId")) {
            try {
                handler.deleteAllEmotionInGroup(Integer.toString(id));
            } catch (TException ex) {
                logger.error("deleteAllEmotionInGroup:" + ex);
            }
        }

        return isDelete;
    }

    public boolean editEmotion(EmotionPOJO emotionSelected) {

        boolean isEdit = true;
        EmotionItem emotionDB = new EmotionItem(emotionSelected.emotionId, emotionSelected.keyInput,
                emotionSelected.linkImage, "http://static.me.zing.vn/v3/images/blank.gif", emotionSelected.description, Integer.toString(emotionSelected.groupEmotionId));
        try {
            handler.editEmotion(emotionSelected.emotionId, emotionDB);
        } catch (TException ex) {
            logger.error("editEmotion:" + ex);
        }

        return isEdit;
    }

}

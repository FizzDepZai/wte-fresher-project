/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Author: Thanh
 * Last Modified: 17/11/2013
 */
package application.model.dao;

import application.model.valueobject.OneEmotionPOJO;
import application.model.valueobject.RoomReportPOJO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;

/**
 *
 * @author root
 */
public class SpecificEmotionSetDAO {
    public static List<OneEmotionPOJO> getAllEmotionsData(String fromDate, String toDate, String timeType, String id){
        List<OneEmotionPOJO> result = new ArrayList<>();
        Connection con=null;
        Statement stm=null;
        ResultSet rs=null;  
        try {
            con = ConnectionDatabase.getConnection();
            stm=con.createStatement();
            if(timeType.equals("By Date")){
                String sql = "select tbl_Emotion.EmotionId, DayUsed, count(*) from ChatAdmin.tbl_GroupEmotion, ChatAdmin.tbl_Emotion, ChatAdmin.tbl_UsedEmotion " +
                        "where tbl_GroupEmotion.GroupEmotionId = tbl_Emotion.GroupEmotionId and "+
                        "tbl_Emotion.EmotionId = tbl_UsedEmotion.EmotionId and "+
                        "DayUsed >=" + "' "+fromDate + " '"+" and DayUsed <= " + " '"+toDate +"' and "+
                        "tbl_Emotion.GroupEmotionId= "+ "' " + id + " '" +
                        " group by tbl_Emotion.EmotionId, DayUsed order by DayUsed";
                System.out.println(sql);
                rs = stm.executeQuery(sql);
                while (rs.next()) {
                    OneEmotionPOJO oneEmotion = new OneEmotionPOJO(rs.getString(1), rs.getInt(3), rs.getString(2));
                    result.add(oneEmotion);
                    System.out.println(oneEmotion.getName() + " " +oneEmotion.getNumUses() + " " +oneEmotion.getTime());
                }
            }
            
             } catch (SQLException ex) {
            Logger.getLogger(RoomReportPOJO.class.getName()).log(Level.SEVERE, null, ex);
        }  finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stm!=null){
                    stm.close();
                }
                if(con!=null){
                    con.close();
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}

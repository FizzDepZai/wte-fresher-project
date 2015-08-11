/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.dao;

import application.model.valueobject.EmotionPOJO;
import application.model.valueobject.GroupEmotionByTimePOJO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;

/**
 *
 * @author root
 */
public class GroupEmotionByTimeDAO {
    public static GroupEmotionByTimePOJO getGroupEmotionByTime(int groupId, String time){
        //time cho biet la ngay, tuan, thang hay nam
        int timeValue = Integer.parseInt(time);
        
        Date currentDate = new Date();
        int dateIs = currentDate.getDate();
        int monthIs = currentDate.getMonth()+1;
        int yearIs = currentDate.getYear()+1900;
        System.out.println("date: " + dateIs + "month: " + monthIs + "year: " + yearIs);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("currentDate is: " + dt.format(currentDate) + " " + currentDate);
        GroupEmotionByTimePOJO emotionGroupByTime = null;
        Connection con=null;
        Statement stm=null;
        ResultSet rs=null;     
        try {
            con = ConnectionDatabase.getConnection();
            stm=con.createStatement();
            if(timeValue==1){
                rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId "
                        + "where DayUsed =" + "\"" +dt.format(currentDate) + "\" and GroupEmotionId = " + "\"" + groupId + "\"" );
                while(rs.next()){//thong ke theo ngay hien tai
                    emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), dt.format(currentDate));
                    System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                }
            } else if(timeValue==2){//thong ke theo tuan hien tai
                
                
                if(1 <= dateIs && dateIs<=7){
                    Date beginWeek = new Date(yearIs-1900, monthIs-1, 1);
                    Date endWeek = new Date(yearIs-1900, monthIs-1, 7);
                    rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId "
                            + "where DayUsed <= " + "\"" +dt.format(endWeek) +"\" and DayUsed >= \""+dt.format(beginWeek) +"\" and GroupEmotionId = " + "\"" + groupId + "\"" );
                    while(rs.next()){//thong ke theo ngay hien tai
                        emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), "1->7-"+monthIs+ "-" +yearIs);
                        System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                    }
                }else if(dateIs<=14){
                    Date beginWeek = new Date(yearIs-1900, monthIs-1, 8);
                    Date endWeek = new Date(yearIs-1900, monthIs-1, 14);
                    rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId "
                            + "where DayUsed <= " + "\"" +dt.format(endWeek) +"\" and DayUsed >= \""+dt.format(beginWeek) +"\" and GroupEmotionId = " + "\"" + groupId + "\"" );
                    while(rs.next()){//thong ke theo ngay hien tai
                        emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), "8->14-"+monthIs+ "-" +yearIs);
                        System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                    }
                }else if(dateIs<=21){
                    Date beginWeek = new Date(yearIs-1900, monthIs-1, 15);
                    Date endWeek = new Date(yearIs-1900, monthIs-1, 21);
                    rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId "
                            + "where DayUsed <= " + "\"" +dt.format(endWeek) +"\" and DayUsed >= \""+dt.format(beginWeek) +"\" and GroupEmotionId = " + "\"" + groupId + "\"" );
                    while(rs.next()){//thong ke theo ngay hien tai
                        emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), "15->21-"+monthIs+ "-" +yearIs);
                        System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                    }
                }else {
                    Date beginWeek = new Date(yearIs-1900, monthIs-1, 22);
                    rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId "
                            + "where DayUsed >= "+"\""+dt.format(beginWeek) +"\" and GroupEmotionId = " + "\"" + groupId + "\"" );
                    while(rs.next()){//thong ke theo ngay hien tai
                        emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), "21->-"+monthIs+ "-" +yearIs);
                        System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                    }
                }

            } else if(timeValue==3){//thong ke theo thang hien tai
                
                rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId where "
                        + "MONTH(DayUsed) = " +"\""+monthIs+"\" and GroupEmotionId = " + "\"" + groupId + "\"");
                while(rs.next()){//thong ke theo ngay hien tai
                   emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), monthIs+ "-" +yearIs);
                   System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                }
            } else if(timeValue==4){//thong ke theo nam
                System.out.println("year is:" + currentDate.getYear());
                rs=stm.executeQuery("select GroupEmotionId, count(*), DayUsed from tbl_UsedEmotion join tbl_Emotion on tbl_UsedEmotion.EmotionId=tbl_Emotion.EmotionId where "
                        + "YEAR(DayUsed) = " +"\""+yearIs+"\" and GroupEmotionId = " + "\"" + groupId + "\"");
                while(rs.next()){//thong ke theo ngay hien tai
                   emotionGroupByTime = new GroupEmotionByTimePOJO(rs.getInt(1), rs.getInt(2), yearIs +"");
                   System.out.println("number of used is: " + emotionGroupByTime.getNumberOfUsed());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmotionPOJO.class.getName()).log(Level.SEVERE, null, ex);
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
        return emotionGroupByTime;
    }
}

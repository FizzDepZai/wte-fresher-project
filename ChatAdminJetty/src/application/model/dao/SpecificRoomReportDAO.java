/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.dao;

import application.model.valueobject.EachRoomDataForReport;
import application.model.valueobject.RoomReportPOJO;
import application.model.valueobject.specificRoomReportPOJO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;

/**
 *
 * @author root
 */
public class SpecificRoomReportDAO {
    
    
    public static specificRoomReportPOJO getDataToDraw(String from, String to, int RoomId) throws ParseException{
        specificRoomReportPOJO result = new specificRoomReportPOJO();
        Connection con=null;
        Statement stm=null;
        ResultSet rs=null;  
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dt2 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            System.out.println("input is: " + from + to);
            con = ConnectionDatabase.getConnection();
            stm=con.createStatement();
            //neu to - from <=7 theo ngay
            //neu to - from <=30 theo tuan
            //neu to - from <=365 theo thang
            //theo nam
            Date fromDate = dt.parse(dt.format(dt2.parse(from)));
            Date toDate = dt.parse(dt.format(dt2.parse(to)));
            int elapseTime = (int) ((toDate.getTime()-fromDate.getTime())/(1000 * 60 * 60 * 24));
            System.out.println("elapseTime is: " + elapseTime);
            if(elapseTime <=7){
                String query = "select Name, DayUsed, count(*) from ChatAdmin.tbl_Room join ChatAdmin.tbl_UsedRoom on ChatAdmin.tbl_Room.RoomId = ChatAdmin.tbl_UsedRoom.RoomId "
                        + " where ChatAdmin.tbl_Room.RoomId = " + RoomId+ " and ChatAdmin.tbl_UsedRoom.DayUsed <= " + "'" + dt.format(toDate)+"'"+" and ChatAdmin.tbl_UsedRoom.DayUsed >= " + "'" 
                        + dt.format(fromDate)+"'"   +" group by ChatAdmin.tbl_UsedRoom.DayUsed";
                System.out.println(query);
                rs = stm.executeQuery(query);
                if(rs!=null) while(rs.next()){
                    EachRoomDataForReport atATime = new EachRoomDataForReport(rs.getString(2), rs.getInt(3));
                    System.out.println("kdkdkdkd" + atATime);
                    result.add(atATime);
                }
            }else if(elapseTime <=30){//tuan
                int numberWeek = elapseTime/7;
                Date tmpFrom = fromDate;         
                String query="";
                for(int i =0; i<numberWeek; i++){
                    Date tmpTo = i!=numberWeek?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 6)):toDate;
                    
                    query += "select Name, MONTH(DayUsed), YEAR(DayUsed), count(*) from ChatAdmin.tbl_Room join ChatAdmin.tbl_UsedRoom on ChatAdmin.tbl_Room.RoomId = ChatAdmin.tbl_UsedRoom.RoomId "
                        + " where ChatAdmin.tbl_Room.RoomId = " + RoomId+ " and ChatAdmin.tbl_UsedRoom.DayUsed >= " + "'" + dt.format(tmpFrom)+"'"+" and ChatAdmin.tbl_UsedRoom.DayUsed <= " + "'" 
                        + dt.format(tmpTo)+"'"   +" group by ChatAdmin.tbl_UsedRoom.DayUsed UNION ";
                    System.out.println(query);
                    //update fromdate
                    tmpFrom =  new Date(toDate.getTime() + (1000 * 60 * 60 * 24));

                }
                System.out.println("union query is: " + query);
                query = query.substring(0, query.length()+1-7);
                System.out.println(query);
                rs = stm.executeQuery(query);
                int i = 1;
                if(rs!=null) while(rs.next()){
                    EachRoomDataForReport atATime = new EachRoomDataForReport("week " + i+"/" +rs.getString(2) + "/"+rs.getString(3), rs.getInt(4));
                    System.out.println("kdkdkdkd" + atATime);
                    i++;
                    result.add(atATime);
                }
            }else if(elapseTime<=365){//thang
                int numberMonth = elapseTime%30==0?elapseTime/30:elapseTime/30+1;
                Date tmpFrom = fromDate;
                String query="";
                for(int i=0; i<numberMonth; i++){
                    Date tmpTo;
                    int year = fromDate.getYear() + 1900;
                    int month = fromDate.getMonth() +1;
//                    System.out.println(year + " " + month);
                    
                    System.out.println(new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)));
                    if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) 
                        tmpTo = i!=numberMonth-1?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 1)):toDate;
                    else if(month==4||month==6||month==9||month==11)
                        tmpTo = i!=numberMonth-1?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)):toDate;
                    else if(year%4==0){
                        tmpTo = i!=numberMonth-1?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 9)):toDate;
                    }else {
                        tmpTo = i!=numberMonth-1?new Date(fromDate.getTime() + + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 8)):toDate;
                    }
                    System.out.println(tmpFrom + " " +tmpTo);
                    query += "select Name, MONTH(DayUsed), YEAR(DayUsed), count(*) from ChatAdmin.tbl_Room join ChatAdmin.tbl_UsedRoom on ChatAdmin.tbl_Room.RoomId = ChatAdmin.tbl_UsedRoom.RoomId "
                        + " where ChatAdmin.tbl_Room.RoomId = " + RoomId+ " and ChatAdmin.tbl_UsedRoom.DayUsed >= " + "'" + dt.format(tmpFrom)+"'"+" and ChatAdmin.tbl_UsedRoom.DayUsed <= " + "'" 
                        + dt.format(tmpTo)+"'"   +" group by MONTH(ChatAdmin.tbl_UsedRoom.DayUsed) UNION ";
                    if(month==1||month==3||month==5||month==7||month==8||month==10||month==12) 
                        tmpFrom = i!=numberMonth-1?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 1)):toDate;
                    else if(month==4||month==6||month==9||month==11)
                        tmpFrom = i!=numberMonth-1?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)):toDate;
                    else if(year%4==0){
                        tmpFrom = i!=numberMonth-1?new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 9)):toDate;
                    }else {
                        tmpFrom = i!=numberMonth-1?new Date(fromDate.getTime() + + (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 10)+ (1000 * 60 * 60 * 24 * 8)):toDate;
                    }
                }
                System.out.println("union query is: " + query);
                query = query.substring(0, query.length()+1-7);
                System.out.println(query);
                rs = stm.executeQuery(query);
                int i = 1;
                if(rs!=null) while(rs.next()){
                    EachRoomDataForReport atATime = new EachRoomDataForReport(rs.getString(2) + "/"+rs.getString(3), rs.getInt(4));
                    i++;
                    result.add(atATime);
                }
            }else {//nam
                int numberYears = elapseTime%365==0?elapseTime/365:elapseTime/365+1;
                Date tmpFrom = fromDate;         
                String query="";
                for(int i =0; i<numberYears; i++){
                    Date tmpTo = null;
                    
                    if(i==numberYears-1) tmpTo = toDate;
                    else {
                        tmpTo = new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 5));
                    }
                    query += "select Name, MONTH(DayUsed), YEAR(DayUsed), count(*) from ChatAdmin.tbl_Room join ChatAdmin.tbl_UsedRoom on ChatAdmin.tbl_Room.RoomId = ChatAdmin.tbl_UsedRoom.RoomId "
                        + " where ChatAdmin.tbl_Room.RoomId = " + RoomId+ " and ChatAdmin.tbl_UsedRoom.DayUsed >= " + "'" + dt.format(tmpFrom)+"'"+" and ChatAdmin.tbl_UsedRoom.DayUsed <= " + "'" 
                        + dt.format(tmpTo)+"'"   +" group by ChatAdmin.tbl_UsedRoom.DayUsed UNION ";
                    System.out.println(query);
                    //update fromdate
                    if(i==numberYears-1) tmpFrom = toDate;
                    else {
                        tmpFrom = new Date(fromDate.getTime() + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)+ (1000 * 60 * 60 * 24 * 20)
                        + (1000 * 60 * 60 * 24 * 5));
                    }

                }
                System.out.println("union query is: " + query);
                query = query.substring(0, query.length()+1-7);
                System.out.println(query);
                rs = stm.executeQuery(query);
                int i = 1;
                if(rs!=null) while(rs.next()){
                    EachRoomDataForReport atATime = new EachRoomDataForReport("week " + i+"/" +rs.getString(2) + "/"+rs.getString(3), rs.getInt(4));
                    System.out.println("kdkdkdkd" + atATime);
                    i++;
                    result.add(atATime);
                }
            }
            
           
            if(rs!=null) while(rs.next()){
                EachRoomDataForReport atATime = new EachRoomDataForReport(rs.getString(2), rs.getInt(3));
                System.out.println("kdkdkdkd" + atATime);
                result.add(atATime);
                
            }
            System.out.println("rs is: " + result.getAtATime());
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

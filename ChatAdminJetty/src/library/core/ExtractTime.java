/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package library.core;

/**
 *
 * @author root
 */
public class ExtractTime {
    public String[] get(String time){
        String date="";
        String week="";
        String month="";
        String year="";
        
        String[] rs=  {date, week, month, year};
        return rs;
    }
}

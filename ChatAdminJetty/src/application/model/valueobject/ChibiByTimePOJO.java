/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.valueobject;

/**
 *
 * @author root
 */
public class ChibiByTimePOJO {
    int ChibiId;
    int NumberOfUsed;
    String date;
    public ChibiByTimePOJO(int id, int number, String date){
        ChibiId = id;
        NumberOfUsed = number;
        this.date = date;
    }
    public int getNumberOfUsed(){
        return NumberOfUsed;
    }
    public String getTimePeriod(){
        return date;
    }
}

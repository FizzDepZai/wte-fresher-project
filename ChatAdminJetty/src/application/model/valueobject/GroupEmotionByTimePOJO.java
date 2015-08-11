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
public class GroupEmotionByTimePOJO {
    int GroupId;
    int NumberOfUsed;
    String date;
    public GroupEmotionByTimePOJO(int id, int number, String date){
        GroupId = id;
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

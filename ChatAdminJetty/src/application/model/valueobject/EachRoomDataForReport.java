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
public class EachRoomDataForReport {

    public EachRoomDataForReport(String time, int uses) {
        this.time = time;
        this.uses = uses;
    }

    public String getTime() {
        return time;
    }

    public int getUses() {
        return uses;
    }
    String time;
    int uses;
    
}

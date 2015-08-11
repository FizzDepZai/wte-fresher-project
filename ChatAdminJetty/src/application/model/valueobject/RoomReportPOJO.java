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
public class RoomReportPOJO {
   String RoomName;

    public String getRoomName() {
        return RoomName;
    }

    public int getNumberUses() {
        return numberUses;
    }
   String time;
   int numberUses;
   public RoomReportPOJO(String roomName, String time, int number){
       this.RoomName = roomName;
       this.time = time;
       this.numberUses = number;
   }
}

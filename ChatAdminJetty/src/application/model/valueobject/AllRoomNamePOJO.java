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
public class AllRoomNamePOJO {
    String roomName;

    public String getRoomName() {
        return roomName;
    }

    public int getRoomID() {
        return roomID;
    }
    int roomID;
    public AllRoomNamePOJO(String name, int id){
        roomName = name;
        roomID = id;
    }
    
}

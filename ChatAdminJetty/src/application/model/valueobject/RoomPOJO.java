
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.valueobject;

/**
 *
 * @author biendltb
 */

/*
* 
*/
public class RoomPOJO {
    
    // Room id
    public int roomId;
    
    // Room name
    public String roomName;
    
    // Status Id of room
    public int statusId;
    
    // Number of online user
    public int onlineUser;
    
    public RoomPOJO (int roomId, String roomName, int statusId, int onlineUser) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.statusId = statusId;
        this.onlineUser = onlineUser;
    }
    
}


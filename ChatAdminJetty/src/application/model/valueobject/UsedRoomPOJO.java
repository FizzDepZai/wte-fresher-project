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
public class UsedRoomPOJO {
    
    // Used room id
    public int usedRoomId;
    
    // Room id
    public int roomId;
    
    // Day use
    public String day;
    
    // The views number
    public int views;
    
    public UsedRoomPOJO (int usedRoomId, int roomId, String day, int views) {
        this.usedRoomId = usedRoomId;
        this.roomId = roomId;
        this.day = day;
        this.views = views;
    }
}

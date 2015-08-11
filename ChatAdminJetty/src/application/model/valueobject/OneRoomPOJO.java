/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Author: Thanh
 * Last Modified: 17/11/2013
 */
package application.model.valueobject;

public class OneRoomPOJO {

    public OneRoomPOJO(String Name, int NumUses, String DateUsed) {
        this.Name = Name;
        this.DateUsed = DateUsed;
        this.NumUses = NumUses;
    }
    String Name;

    public OneRoomPOJO(String string, int aInt) {
        this.DateUsed = string;
        this.NumUses = aInt;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setDateUsed(String DateUsed) {
        this.DateUsed = DateUsed;
    }

    public void setNumUses(int NumUses) {
        this.NumUses = NumUses;
    }

    public String getName() {
        return Name;
    }

    public String getDateUsed() {
        return DateUsed;
    }

    public int getNumUses() {
        return NumUses;
    }
    String DateUsed;
    int NumUses;
}

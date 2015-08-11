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

public class OneEmotionPOJO {
    public OneEmotionPOJO(String name, int numUses, String time) {
        this.name = name;
        this.numUses = numUses;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumUses() {
        return numUses;
    }

    public void setNumUses(int numUses) {
        this.numUses = numUses;
    }
    String name; 
    int numUses;
    String time;

    public String getTime() {
        return time;
    }
}

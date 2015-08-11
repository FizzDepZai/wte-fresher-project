/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.valueobject;

import java.io.Serializable;

/**
 *
 * @author tript
 */
public class GroupEmotionPOJO implements Serializable{
    public int id;
    public String name;
    
    public GroupEmotionPOJO(int id, String name){
        this.id  =  id;
        this.name = name;
    }
    public GroupEmotionPOJO(){
        
    }
}

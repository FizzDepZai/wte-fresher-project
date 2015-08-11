/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.valueobject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.ConnectionDatabase;

/**
 *
 * @author tript
 */
public class EmotionPOJO implements Serializable{
    public int emotionId;
    public String linkImage;
    public String description;
    public int groupEmotionId;
    public String keyInput;
    
    public EmotionPOJO(int emotionId, String linkImage, String description, int groupEmotionId){
        this.emotionId = emotionId;
        this.linkImage = linkImage;
        this.description = description;
        this.groupEmotionId = groupEmotionId;
              
    }
    public EmotionPOJO(){
        
    }
    
}

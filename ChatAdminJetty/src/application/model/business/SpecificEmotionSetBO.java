/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * Author: Thanh
 * Last Modified: 17/11/2013
 */
package application.model.business;

import application.model.dao.SpecificEmotionSetDAO;
import application.model.valueobject.OneEmotionPOJO;
import java.util.List;

/**
 *
 * @author root
 */
public class SpecificEmotionSetBO {
    public static List<OneEmotionPOJO> getAllEmotionsData(String fromDate, String toDate, String timeType, String id){
        
        return SpecificEmotionSetDAO.getAllEmotionsData(fromDate, toDate, timeType, id);
    }
}

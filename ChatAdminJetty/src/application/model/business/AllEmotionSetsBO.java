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

import application.model.dao.AllEmotionSetsDAO;
import application.model.valueobject.OneEmotionSetPOJO;
import java.util.List;


public class AllEmotionSetsBO {
     public static List<OneEmotionSetPOJO> getAllEmotionSetsData(String fromDate, String toDate, String timeType){
        
        return AllEmotionSetsDAO.getAllEmotionSetsData(fromDate, toDate, timeType);
    }
}

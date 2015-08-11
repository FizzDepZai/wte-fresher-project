/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.business;

import application.model.dao.ChibiByTimeDAO;
import application.model.valueobject.ChibiByTimePOJO;

/**
 *
 * @author root
 */
public class ChibiByTimeBO {
    public ChibiByTimePOJO getEmotionSetByTime(int ChibiId, String time){
        return ChibiByTimeDAO.getChibiEmotionByTime(ChibiId, time);
    }
}

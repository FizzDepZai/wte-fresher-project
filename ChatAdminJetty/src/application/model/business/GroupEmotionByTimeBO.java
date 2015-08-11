/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.business;

import application.model.dao.GroupEmotionByTimeDAO;
import application.model.valueobject.GroupEmotionByTimePOJO;
import application.model.valueobject.GroupEmotionPOJO;

/**
 *
 * @author root
 */
public class GroupEmotionByTimeBO {
    public GroupEmotionByTimePOJO getEmotionSetByTime(int groupId, String time){
        return GroupEmotionByTimeDAO.getGroupEmotionByTime(groupId, time);
    }
}

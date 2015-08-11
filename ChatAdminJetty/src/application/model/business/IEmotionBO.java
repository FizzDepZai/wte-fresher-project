/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.model.business;

import application.model.valueobject.EmotionPOJO;
import application.model.valueobject.GroupEmotionPOJO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tript
 */
public interface IEmotionBO {
     
    /**
     * get all emotion
     * @return list emotions
     */
    public  List<EmotionPOJO> getAllEmotion();
    /**
     * get all group emotion
     * @return list group emotion
     */
    public List<GroupEmotionPOJO> getGroupEmotion();
    
    
    /**
     * check new image emotion is exist in dabase
     * @param arrLinkImage
     * @param groupEmotionId
     * @return path images exist
     */
    public String checkImageEmotionExist(String [] arrLinkImage,int groupEmotionId);
    
    /**
     * add list more emotion in group
     * @param arrEmotionAdd
     * @param arrLinkImage
     * @param groupEmotionId
     * @param description
     * @return true if success else return false
     * 
     */
    public boolean addEmotion(ArrayList<EmotionPOJO> arrEmotionAdd);
    
    /**
     * add emotion with exist link
     * @param arrEmotionAdd
     * @return 
     */
    public boolean addEmotionWithLink(String listEmotionValueJSON);
    
    
    
    public List<EmotionPOJO> getEmotionWithGroup(String groupId);
    
    
    /**
     * delete emotion selected
     * @param emotionId
     * @param listEmotionInGroup
     * @return true if success else return false
     */
    public boolean deleteEmotion(int emotionId,List<EmotionPOJO> listEmotionInGroup);
    
    /**
     * edit Emotion selected 
     * @param emotionSelected
     * @return true if success else return false
     */
    public boolean editEmotion(EmotionPOJO emotionSelected);

    /**
     * add new group emotion
     * @param groupEmotionName
     * @return true if success else return false
     */
    public boolean addGroupEmotion(String groupEmotionName);

    /**
     * delete group emotion selected by id and delete all emotion with group id
     * @param groupId
     * @return true if delete success else return false
     */
    public boolean deleteGroupEmotion(String groupId);
    
    /**
     * edit group emotion name 
     * @param groupEmotion
     * @return true if edit success else return false
     */
    public boolean editGroupEmotion(GroupEmotionPOJO groupEmotion); 
}
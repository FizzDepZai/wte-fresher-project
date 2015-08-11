/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model.business;

import application.model.valueobject.EmotionPOJO;
import application.model.dao.EmotionDAO;
import application.model.dao.GroupEmotionDAO;
import application.model.valueobject.GroupEmotionPOJO;
import application.views.assets.UploadConstant;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.core.Registry;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author tript
 */
public class EmotionBOImpl implements IEmotionBO {

    EmotionDAO emotionDAO = new EmotionDAO();
    GroupEmotionDAO groupEmotionDAO = new GroupEmotionDAO();

    @Override
    public List<EmotionPOJO> getAllEmotion() {
        return emotionDAO.getAll();
    }

    @Override
    public List<GroupEmotionPOJO> getGroupEmotion() {

        return groupEmotionDAO.getAllGroupEmotion();

    }

    @Override
    public String checkImageEmotionExist(String[] arrLinkImage, int groupEmotionId) {
        return emotionDAO.checkImageEmotionExist(arrLinkImage, groupEmotionId);

    }

    @Override
    public boolean addEmotion(ArrayList<EmotionPOJO> arrEmotionAdd) {
        String listEmotionValueJSON = "";
        boolean isAdd = emotionDAO.addEmotion(listEmotionValueJSON);
        if (isAdd) {
            for (EmotionPOJO emotion : arrEmotionAdd) {
                String linkImage = emotion.linkImage;
                String groupEmotionId = Integer.toString(emotion.groupEmotionId);
                //link empty when it is exist in database
                if (!"".equals(linkImage)) {

                    String sourcePath = Registry.get("imageHost") + "/emotions-image/"
                            + UploadConstant.UPLOAD_DIRECTORY + "/" + linkImage;

                    //item.getString --> groupEmotion chon
                    String desPath = Registry.get("imageHost")
                            + "/emotions-image/" + groupEmotionId + "/" + linkImage;

                    File sourceDir = new File(sourcePath);
                    File desDir = new File(desPath);
                    if (sourceDir.exists()) {
                        try {
                            FileUtils.copyFile(sourceDir, desDir);
                        } catch (IOException ex) {
                            Logger.getLogger(EmotionBOImpl.class.getName()).log(Level.SEVERE, null, ex);
                            isAdd = false;
                        }
                        sourceDir.delete();

                    }
                }
            }
        }
        return isAdd;
    }

    @Override
    public List<EmotionPOJO> getEmotionWithGroup(String groupId) {
        return emotionDAO.getEmotionWithGroup(groupId);
    }

    @Override
    public boolean deleteEmotion(int emotionId, List<EmotionPOJO> listEmotionInGroup) {
        //kiem tra emtotion co cung id se delete file image cua emotion do
        for (EmotionPOJO emotion : listEmotionInGroup) {
            if (emotion.emotionId == emotionId) {
                File file = new File(Registry.get("imageHost") + emotion.linkImage);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        return emotionDAO.deleteEmotion(emotionId, "EmotionId");
    }

    @Override
    public boolean editEmotion(EmotionPOJO emotionSelected) {
        return emotionDAO.editEmotion(emotionSelected);
    }

    @Override
    public boolean addGroupEmotion(String groupEmotionName) {
        if (groupEmotionName.equals("")) {
            return false;
        }
        String groupEmotionId = groupEmotionDAO.addGroupEmotion(groupEmotionName);
//        if (!groupEmotionId.equals("")) {
//            File groupEmotionDir = new File(Registry.get("imageHost") + "/emotions-image/" + groupEmotionId);
//            if (!groupEmotionDir.exists()) {
//                groupEmotionDir.mkdir();
//                return true;
//            }
//
//            return true;
//
//        }
        return true;
    }

    @Override
    public boolean deleteGroupEmotion(String groupId) {
//        boolean isDeleteSuccess = emotionDAO.deleteEmotion(Integer.parseInt(groupId), "GroupEmotionId")
//                || groupEmotionDAO.deleteGroupEmotion(groupId);
        boolean  isDeleteSuccess = groupEmotionDAO.deleteGroupEmotion(groupId);
//        if (isDeleteSuccess) {
//            File folderImage = new File(Registry.get("imageHost") + "/emotions-image/" + groupId);
//            if (folderImage.exists()) {
//                deleteFolder(folderImage);
//            }
//        }

        return isDeleteSuccess;
    }

    private void deleteFolder(File file) {
        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    deleteFolder(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();

                }
            }

        } else {
            //if file, then delete it
            file.delete();
        }
    }

    @Override
    public boolean editGroupEmotion(GroupEmotionPOJO groupEmotion) {
        return groupEmotionDAO.editGroupEmotion(groupEmotion);

    }

    @Override
    public boolean addEmotionWithLink(String listEmotionValueJSON) {
        boolean isAdd = emotionDAO.addEmotion(listEmotionValueJSON);
        return isAdd;
    }

}

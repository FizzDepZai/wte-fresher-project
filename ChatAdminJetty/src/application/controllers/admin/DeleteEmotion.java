/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.controllers.admin;

import application.model.business.EmotionBOImpl;
import application.model.valueobject.EmotionPOJO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.AdminController;
import library.memcached.Memcached;

/**
 *
 * @author tript
 */
public class DeleteEmotion extends AdminController {

    public DeleteEmotion() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {   
          super.doGet(request, response);
 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //dung listEmotionInGroup de biet dc link image cua emotions va delete image
           super.doPost(request, response);
          String idEmotion = request.getParameter("id");
          String groupId = request.getParameter("groupId");
          EmotionBOImpl emotionBoImpl =  new EmotionBOImpl();
            List<EmotionPOJO> listEmotionInGroup    = (List<EmotionPOJO>)Memcached.get("listEmotionInGroup"+groupId);
           boolean isDelete = emotionBoImpl.deleteEmotion(Integer.parseInt(idEmotion),listEmotionInGroup);
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          out.print(isDelete);
     }
}

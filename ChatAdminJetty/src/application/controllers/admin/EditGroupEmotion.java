/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.controllers.admin;

import application.model.business.EmotionBOImpl;
import application.model.valueobject.GroupEmotionPOJO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
public class EditGroupEmotion extends HttpServlet {

    public EditGroupEmotion() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          String groupId = request.getParameter("id");
          String groupName = request.getParameter("name");
          EmotionBOImpl emotionBoImpl =  new EmotionBOImpl();
          GroupEmotionPOJO groupEmotion = new GroupEmotionPOJO(Integer.parseInt(groupId),groupName );
          boolean isEdit=  emotionBoImpl.editGroupEmotion(groupEmotion);
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          out.print(isEdit);
    }
}
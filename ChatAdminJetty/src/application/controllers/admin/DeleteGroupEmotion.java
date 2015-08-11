/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.controllers.admin;

import application.model.business.EmotionBOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.Registry;
import library.core.View;

/**
 *
 * @author root
 */
public class DeleteGroupEmotion extends HttpServlet {

    public DeleteGroupEmotion() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          String groupId = request.getParameter("groupId");
          EmotionBOImpl emotionBoImpl =  new EmotionBOImpl();
          
           boolean isDelete =  emotionBoImpl.deleteGroupEmotion(groupId);
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          out.print(isDelete);
    }
}


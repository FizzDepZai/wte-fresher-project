/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.middleware.BusinessChatHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author biendltb
 */
public class FriendListController extends HttpServlet {
    Logger logger =Logger.getLogger(FriendListController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String userId = request.getParameter("userId");
        final String oauthCode = request.getParameter("oauthCode");

        //list friend la 1 chuoi json
        String listFriend = "";
          BusinessChatHandler handler = new BusinessChatHandler();
        try {
            listFriend = handler.getFriendListZalo(userId, oauthCode);
        } catch (TException ex) {
            logger.error("Get friend list: "+ ex);
        }
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println(listFriend);
     }

}

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
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 *
 * @author tript Create on 2:59:49 PM Dec 29, 2013
 */
public class LoadEmotionController extends HttpServlet {

    Logger logger = Logger.getLogger(LoadEmotionController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        final String userId = request.getParameter("userId");
//        final String oauthCode = request.getParameter("oauthCode");

        String listEmotion = "";
        BusinessChatHandler handler = new BusinessChatHandler();
        try {
            listEmotion = handler.loadAllEmotion();
        } catch (TException ex) {
            logger.error(ex);
        }
//        listEmotion = listEmotion.substring(1, listEmotion.length()-1);
//        listEmotion = "[" + listEmotion + "]";

        Object obj = JSONValue.parse(listEmotion);
        JSONArray friendList = (JSONArray) obj;
        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println(obj);
    }

}

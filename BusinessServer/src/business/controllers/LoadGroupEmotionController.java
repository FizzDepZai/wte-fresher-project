/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.middleware.BusinessChatHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
 * @author tript Create on 9:08:49 PM Jan 7, 2014
 */
public class LoadGroupEmotionController extends HttpServlet {

    Logger logger = Logger.getLogger(LoadEmotionController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        final String userId = request.getParameter("userId");
//        final String oauthCode = request.getParameter("oauthCode");

        String listGroupEmotion = "";
        BusinessChatHandler handler = new BusinessChatHandler();
        try {
            listGroupEmotion = handler.gelAllGroupEmotion();
        } catch (TException ex) {
            logger.error(ex);
        }
//        listEmotion = listEmotion.substring(1, listEmotion.length()-1);
//        listEmotion = "[" + listEmotion + "]";
        //do key chuyej
        Object obj = JSONValue.parse(listGroupEmotion);
//        Map listGroupEmotionJSON = (Map) obj;
        Map listGroupEmotionJSON = (Map) obj;
        Iterator iter = listGroupEmotionJSON.entrySet().iterator();
        Map mapGroupEmotionItem = new HashMap();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String groupIdStr = (String) entry.getKey();

            mapGroupEmotionItem.put(groupIdStr.trim(), (String) entry.getValue());
        }
//        JSONArray friendList = (JSONArray) obj;
        response.setCharacterEncoding("UTF-8");

        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println(JSONValue.toJSONString(mapGroupEmotionItem));
    }
}

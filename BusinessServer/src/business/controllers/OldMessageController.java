/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.middleware.BusinessChatHandler;
import business.library.thrift.MsgItem;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.json.simple.JSONValue;

/**
 *
 * @author tript Create on 10:51:22 AM Dec 21, 2013
 */
public class OldMessageController extends HttpServlet {

    Logger logger = Logger.getLogger(OldMessageController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentUserId = request.getParameter("userName");
        String targetUserId = request.getParameter("userNameOfReceiver");
        int totalMessage = Integer.parseInt(request.getParameter("totalMessageOfUser"));
        BusinessChatHandler handler = new BusinessChatHandler();
        List<MsgItem> listOldMessage = null;
        //lay list old message tu middleware
        try {
            listOldMessage = handler.getMessageList(currentUserId, targetUserId, totalMessage);
        } catch (TException ex) {
            logger.error(ex);
        }
        List listMessageJSONObject = new LinkedList();
        if (listOldMessage != null) {
            for (int i = 0; i < listOldMessage.size(); i++) {
                HashMap mapMessageItem = new HashMap();
                mapMessageItem.put("from", listOldMessage.get(i).sendingUserId);
                mapMessageItem.put("to", listOldMessage.get(i).receiveUserId);
                mapMessageItem.put("time", listOldMessage.get(i).time);
                mapMessageItem.put("content", listOldMessage.get(i).content);
                mapMessageItem.put("type", listOldMessage.get(i).type);
                listMessageJSONObject.add(mapMessageItem);
            }
        }
        //send message json to view
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println(JSONValue.toJSONString(listMessageJSONObject));

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controllers;

import chat.model.valueobject.MessagePOJO;
import chat.gearman.client.GMclient;
import chat.middleware.MiddlewareHandler;
import chat.model.valueobject.UserManager;
import chat.utility.SettingUtility;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.json.simple.JSONValue;

/**
 *
 * @author tript
 */
public class ChatController extends HttpServlet {

    Logger logger = Logger.getLogger(ChatController.class);

//    ManagerMessageId managerMessageId = new ManagerMessageId();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //check login
        MiddlewareHandler handler = new MiddlewareHandler();
        String sessionId = null;
        // Lay session id tu trong cookie
        Cookie[] cooks = request.getCookies();
        if (cooks != null) {
            for (int i = 0; i < cooks.length; i++) {
                Cookie cookie = cooks[i];
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
        }
        if (sessionId != null) {
            System.out.println("SessionId in cookie");
        } else {
            System.out.println("--------------------------->NULL");
            sessionId = request.getParameter("oauthCode");
        }
        try {
            if (handler.isLogin(sessionId)) {
                System.out.println("Check login is ok");
                String sendingUserId = request.getParameter("userName");
                String receiveUserId = request.getParameter("userNameOfReceiver");
                String content = request.getParameter("message");
                String typeMessage = request.getParameter("type");

        String dateTimeSend = SettingUtility.getTimeNow();
        int messageId = 0;
        MessagePOJO message = new MessagePOJO(sendingUserId, receiveUserId, content, dateTimeSend, messageId, typeMessage);
        //tao gearman client de notify den server ben kia
        // not find user with userID
//        if (UserManager.getUser(receiveUserId) == null) {
//            try {
//                GMclient.run(MessagePOJO.serialize(message));
//            } catch (InterruptedException | ClassNotFoundException ex) {
//                logger.error("German client: " + ex);
//            }
//        } else {
//            System.out.println("Message:"+ message.getContent());
//            UserManager.sendMessageToClient(message);
//        }
        UserManager.sendMessageToClient(message);

                response.setContentType("text/html");
                response.setHeader("Access-Control-Allow-Origin", "*");
                HashMap mapMessageItem = new HashMap();
                mapMessageItem.put("from", sendingUserId);
                mapMessageItem.put("time", dateTimeSend);
                mapMessageItem.put("content", content);
                response.getWriter().println(JSONValue.toJSONString(mapMessageItem));
            } else {
                // If user hasn't login, move to Zalo Authentication to login
                String newURL = "http://fresherchat.zapps.vn/index";
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", newURL);
                response.setContentType("text/html");
            }
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controllers;

import chat.gearman.client.GMclient;
import chat.model.valueobject.MessagePOJO;
import chat.model.valueobject.UserManager;
import chat.utility.ManagerMessageId;
import chat.utility.SettingUtility;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.simple.JSONValue;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tript
 */
public class UploadImageController extends HttpServlet {

    Logger logger =Logger.getLogger(UploadImageController.class);

    ManagerMessageId managerImageId = new ManagerMessageId();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //check login
        String sendingUserId = request.getParameter("userName");
        String receiveUserId = request.getParameter("userNameOfReceiver");
        String content = request.getParameter("message");
        String dateTimeSend = SettingUtility.getTimeNow();
        int messageId = 0;
        String typeMessage = request.getParameter("type");

        MessagePOJO message = new MessagePOJO(sendingUserId, receiveUserId, content, dateTimeSend, messageId, typeMessage);
        //tao gearman client de notify den server ben kia
        // not find user with userID
        if (UserManager.getUser(receiveUserId) == null) {
            GMclient gmClient = new GMclient();
            try {
                gmClient.run(MessagePOJO.serialize(message));
            } catch (InterruptedException | ClassNotFoundException ex) {
                logger.error("German client: " + ex);
            }
        }

        UserManager.sendMessageToClient(message);

        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
//        PrintWriter out = response.getWriter();
//        out.print(result);
        HashMap mapMessageItem = new HashMap();
        mapMessageItem.put("from", sendingUserId);
        mapMessageItem.put("time", dateTimeSend);
        mapMessageItem.put("content", content);
        response.getWriter().println(JSONValue.toJSONString(mapMessageItem));

          //upload image to browser for add emotions
        //upload image to browser for add emotions
    }

}

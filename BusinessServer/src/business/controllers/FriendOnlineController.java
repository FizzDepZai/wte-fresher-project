/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package business.controllers;

import business.middleware.BusinessChatHandler;
import business.library.thrift.UserItem;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
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
 * @author tript
 * Create on 2:33:37 PM  Dec 21, 2013 
 */
public class FriendOnlineController extends HttpServlet{
    Logger logger =Logger.getLogger(FriendListController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String friendListJSON = request.getParameter("friendList");
        
          //parse ra friendList
          Object obj=JSONValue.parse(friendListJSON);
          JSONArray friendList=(JSONArray)obj;
          BusinessChatHandler handler = new BusinessChatHandler();
          List<Boolean>  listFriendOnlineStatus = null;
        try {
            listFriendOnlineStatus = handler.checkFriendOnline(friendList);
        } catch (TException ex) {
            logger.error("Check friend online: " + ex);
        }
        
        response.setContentType("text/jsonp;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONArray listFriendOnlineJSON = new JSONArray();
        if(listFriendOnlineStatus!=null){
            for (int i = 0; i < listFriendOnlineStatus.size(); i++) {
                listFriendOnlineJSON.add(listFriendOnlineStatus.get(i));
            }
        }

        response.getWriter().println(listFriendOnlineJSON);
    }

  
    

    
}

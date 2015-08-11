/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controllers;

import chat.library.core.ConnectionChatHandler;
import chat.model.valueobject.UserChatPOJO;
import chat.model.valueobject.UserManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.json.simple.JSONValue;

/**
 *
 * @author tript
 */
public class PollController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");

        String userName = request.getParameter("userId");

        if (!userName.equals("")) {
            poll(request, response);
        }
    }

    private void poll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //after check login
        final String userId = request.getParameter("userId");
        final String oauthCode = request.getParameter("oauthCode");
        UserChatPOJO userChat = new UserChatPOJO();
        userChat.setUserId(userId);
        userChat.setOauthCode(oauthCode);
        //neu user hien tai ko co trong danh sach user cua server chat
        if (UserManager.getUser(userId) == null) {
            UserManager.putUser(userChat);
            //gui thong tin user online sang middleware
            ConnectionChatHandler handler = new ConnectionChatHandler();
            handler.notifyUserOnline(userId);
        }
        System.out.println("truoc " + userId);

        System.out.println("------------------");

        //if don't have any message yet
        //wait (only for this request)
        if (UserManager.getUser(userId).getUserId() != null) {
            //giu userId cua 1 object trong day ko cho dua khac co cung userId nay vao
            synchronized (UserManager.getUser(userId)) {
                if (UserManager.getUser(userId).getListMessageSize() > 0) {
                    //gui message offline den user
                    response.setContentType("text/json;charset=utf-8");
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    List listMessageJSONObject = new LinkedList();
                    for (int i = 0; i < UserManager.getUser(userId).getListMessageSize(); i++) {
                        HashMap mapMessageItem = new HashMap();
                        mapMessageItem.put("from", UserManager.getUser(userId).getMessage(i).getSendingUserId());
                        mapMessageItem.put("time", UserManager.getUser(userId).getMessage(i).getDateTimeSend());
                        mapMessageItem.put("content", UserManager.getUser(userId).getMessage(i).getContent());
                        mapMessageItem.put( "type", UserManager.getUser(userId).getMessage(i).getType());
                        listMessageJSONObject.add(mapMessageItem);
                    }
                    response.getWriter().println(JSONValue.toJSONString(listMessageJSONObject));
                    //clear all message sent
                    UserManager.getUser(userId).clearMessageList();

                } else {

                    response.setContentType("text/json;charset=utf-8");
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    Continuation continuation = ContinuationSupport.getContinuation(request);
                    System.out.println("Intial:" + continuation.isInitial());
                    //first time, isInitial() return true
                    if (continuation.isInitial()) {
                        //or someone will send a message later
                        UserManager.getUser(userId).setContinuation(continuation);

                        System.out.println("giua " + userId  + "total:"+ UserManager.getMapUser().size());
                        for (String key : UserManager.getMapUser().keySet()) {
                            System.out.println("key,val: " + key + "," + UserManager.getMapUser().get(key).getContinuation());

                        }
                        System.out.println("------------------");

                        continuation.setTimeout(20000);//we will wait in 20s or until resume() is called
                        //if 20s is over, this request will be called again
                        continuation.suspend();

                        //set for user connection polling
                    } else {
                        System.out.println("sau " + userId);
                        for (String key : UserManager.getMapUser().keySet()) {
                            System.out.println("key,val: " + key + "," + UserManager.getMapUser().get(key).getContinuation());

                        }
                        System.out.println("------------------");

//                        for(int i =0;i<UserManager.getMapUser().size();i++){
//                            System.out.println("user " + UserManager.getMapUser(). + " connection:" + UserManager.getUser(userId).getContinuation());
//                        }
                        if (UserManager.getUser(userId).getListMessageSize() > 0) {
                            List listMessageJSONObject = new LinkedList();
                            for (int i = 0; i < UserManager.getUser(userId).getListMessageSize(); i++) {
                                HashMap mapMessageItem = new HashMap();
                                mapMessageItem.put("from", UserManager.getUser(userId).getMessage(i).getSendingUserId());
                                mapMessageItem.put("time", UserManager.getUser(userId).getMessage(i).getDateTimeSend());
                                mapMessageItem.put("content", UserManager.getUser(userId).getMessage(i).getContent());
                                mapMessageItem.put( "type", UserManager.getUser(userId).getMessage(i).getType());
                                listMessageJSONObject.add(mapMessageItem);
                            }

                            //send message json to view
                            response.setContentType("text/json;charset=utf-8");
                            response.setHeader("Access-Control-Allow-Origin", "*");

                            response.getWriter().println(JSONValue.toJSONString(listMessageJSONObject));
                            //clear all message sent
                            UserManager.getUser(userId).clearMessageList();
                        } else {
                            response.setContentType("text/json;charset=utf-8");
                            response.setHeader("Access-Control-Allow-Origin", "*");
                            response.getWriter().print(JSONValue.toJSONString(""));
                        }

                    }

//                    for (String key : UserManager.getMapUser().keySet()) {
//                        System.out.println("key,val: " + key + "," + UserManager.getMapUser().get(key).getContinuation());
//                        if(UserManager.getMapUser().get(key).getContinuation()!=null){
//                        System.out.println("key,val: " + key + ","
//                                + "initial:" + UserManager.getMapUser().get(key).getContinuation().isInitial()+"\n"
//                                + "expired:" + UserManager.getMapUser().get(key).getContinuation().isExpired()+"\n"
//                                + "isResponseWrapped:" + UserManager.getMapUser().get(key).getContinuation().isResponseWrapped()+"\n"
//                                + "isResumed:" + UserManager.getMapUser().get(key).getContinuation().isResumed()+"\n"
//                                + "isSuspended:" + UserManager.getMapUser().get(key).getContinuation().isSuspended()+"\n");
//                        }
//                    }
                }
            }
        }
    }
}

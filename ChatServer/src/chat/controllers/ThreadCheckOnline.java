/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controllers;

import chat.library.core.ConnectionChatHandler;
import chat.model.valueobject.UserManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.continuation.Continuation;

/**
 *
 * @author tript Create on 10:05:03 AM Dec 20, 2013
 */
public class ThreadCheckOnline implements Runnable {

    public static final int PERIOD_TIME__CHECK_ONLINE = 1000;
    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ThreadCheckOnline.class);

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(PERIOD_TIME__CHECK_ONLINE);
                ArrayList<String> listUserIdOffline = new ArrayList<String>();
                for (String key : UserManager.getMapUser().keySet()) {
                    Continuation con = UserManager.getUser(key).getContinuation();
                    if (con != null && con.isSuspended() == false && con.isInitial() == true) {
                        //Initial Idle -->offline
                        System.out.println("User " + key + " offline");
                        //xoa khoi danh sach user trong server chat
                        UserManager.getMapUser().remove(key);
                        listUserIdOffline.add(key);
                    }
                }
                if (listUserIdOffline.size() > 0) {
                    //notify danh sach user offline cho middleware biet
                    ConnectionChatHandler handler = new ConnectionChatHandler();
                    System.out.println("List user offline:"+ listUserIdOffline);
                    handler.notifyListUserOffline(listUserIdOffline);
                }

            } catch (InterruptedException ex) {
                logger.error("Interrupt:"+ex);
            }
        }

    }

}

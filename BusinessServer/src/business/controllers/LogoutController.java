/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.middleware.BusinessChatHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.thrift.TException;

/**
 *
 * @author root
 */
public class LogoutController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String userId = request.getParameter("userId");
        final String oauthCode = request.getParameter("oauthCode");
        String sessionId = oauthCode;
        BusinessChatHandler handler = new BusinessChatHandler();
        try {
            if (handler.isLogin(sessionId)) {
                handler.setLogout(sessionId);
                Cookie cook_sid = new Cookie("sessionId", "");
                cook_sid.setMaxAge(0);
                response.addCookie(cook_sid);
            }
        } catch (TException ex) {
            Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

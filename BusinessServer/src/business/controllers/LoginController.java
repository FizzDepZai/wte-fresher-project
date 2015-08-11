/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.library.core.Registry;
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
 * @author biendltb
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        int session_exp = Registry.getInt("session_exp", 86400);

        final String oauthCode = request.getParameter("oauthCode");

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

        // Neu dang nhap lan dau
        // Kiem tra, khoi tao session, lay oauthCode lam sessionId
        if (sessionId == null || "".equals(sessionId) || !sessionId.equals(oauthCode)) {
            sessionId = oauthCode;

            // Them vao cookie
            Cookie cook = new Cookie("sessionId", sessionId);
            cook.setMaxAge(session_exp);
            response.addCookie(cook);

            // Tao session thong bao user online tren memcached
            BusinessChatHandler handler = new BusinessChatHandler();
            try {
                handler.setLogin(sessionId);
            } catch (TException ex) {

                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println("Success to set cookie");
    }
}

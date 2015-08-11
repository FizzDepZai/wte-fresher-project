/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.controllers;

import connection.library.core.Registry;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author biendltb
 */
public class LoginController extends HttpServlet {

    Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        int session_exp = Registry.getInt("session_exp", 86400);
        String userId = request.getParameter("uid");
        String oauthCode = request.getParameter("code");

        // Neu dang nhap lan dau
        // Kiem tra, khoi tao session, lay oauthCode lam sessionId
        // Them vao cookie
        Cookie cook_uid = new Cookie("userId", userId),
                cook_sid = new Cookie("sessionId", oauthCode);
        
        cook_uid.setMaxAge(session_exp);
        cook_sid.setMaxAge(session_exp);
        

        response.addCookie(cook_uid);
        response.addCookie(cook_sid);

        // Chuyen toi trang index
        String newURL = "http://fresherchat.zapps.vn/index";
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", newURL);
        response.setContentType("text/html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

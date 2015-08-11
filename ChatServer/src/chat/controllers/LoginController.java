/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controllers;

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
        
        final String oauthCode = request.getParameter("oauthCode");

        
        // Neu dang nhap lan dau
        // Kiem tra, khoi tao session, lay oauthCode lam sessionId
        // Them vao cookie
        Cookie cook_sid = new Cookie("sessionId", oauthCode);
        
        cook_sid.setMaxAge(86400);
        
        response.addCookie(cook_sid);
        
        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println("Success to set cookie");
    }
}

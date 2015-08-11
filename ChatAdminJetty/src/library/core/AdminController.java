/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.memcached.SessionClient;

/**
 *
 * @author tript
 */
public class AdminController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        setSession(request, response);
   
            auth(request, response);
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        setSession(request, response);
  
            auth(request, response);
      
    }

    protected void auth(HttpServletRequest request, HttpServletResponse response) {

        String sessionId = request.getAttribute("sessionIdAdmin").toString();
        String adminLogged = SessionClient.get(sessionId, "adminLogged");
        if (!"true".equals(adminLogged)) {
            if (!this.getClass().getSimpleName().equals("AdminLogin")) {
                //Chuyen trang, dang nhap
                response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                response.setHeader("Location", Registry.get("Host") + "/adminlogin");
                response.setContentType("text/html");
            }
        }

    }

    private void setSession(HttpServletRequest request, HttpServletResponse response) {
        //Lay session Id
        String sessionId = null;
        //lau cookies tu request
        Cookie[] cooks = request.getCookies();
        if (cooks != null) {
            for (Cookie cookie : cooks) {
                if ("sessionIdAdmin".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
        }
        // Kiem tra, khoi tao session
        if (sessionId == null) {
            String id = SessionClient.init();
            Cookie cook = new Cookie("sessionIdAdmin", id);
            request.setAttribute("sessionIdAdmin", id);
            response.addCookie(cook);
        } else {
            //khong bang null thi lay session id trong cookies de setAttribute
            request.setAttribute("sessionIdAdmin", sessionId);
        }
    }
}

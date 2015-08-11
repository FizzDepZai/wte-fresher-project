/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers.admin;

import application.model.valueobject.UserAdmin;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.Registry;
import library.core.View;

/**
 *
 * @author tript
 */
public class Index extends HttpServlet {

    public Index() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check login
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

        if (sessionId != null && !"".equals(sessionId)) {

            View layout = new View("Index.xtm");
            String resourceHost = "";

            resourceHost += Registry.get("Host");
            resourceHost = resourceHost + "/resources";
            layout.setVariable("hostResource", resourceHost);
            layout.setVariable("host", Registry.get("host"));
            layout.setVariable("sessionId", sessionId);

            //Tao view
            String content = layout.render();
            response.getWriter().write(content);
        } else {
            // If user hasn't login, move to Zalo Authentication to login
            String newURL = "http://localhost:8080/adminlogin";
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", newURL);
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Type", "text/html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}

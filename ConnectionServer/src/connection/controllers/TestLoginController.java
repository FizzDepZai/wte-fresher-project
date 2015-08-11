/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.controllers;

import com.vng.zalosdk.builder.ZaloServiceBuilder;
import com.vng.zalosdk.oauth.OAuthService;
import connection.library.core.Registry;
import connection.library.core.View;
import connection.utility.ZaloInApp;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tript Create on 2:00:20 AM Dec 21, 2013
 */
public class TestLoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Content-Type", "text/html");
        response.setCharacterEncoding("UTF-8");
        View layout = new View("connection/views/chat", "chat.xtm");
        //Tao view
//        String resourceHost = "http://";
//        resourceHost += Registry.get("connection.server.host", "localhost");
//        resourceHost = resourceHost + ":" + Registry.getInt("connection.server.port", 8000) + "/resources";
//        layout.setVariable("hostResource", resourceHost);

        //Tao view
        String content = layout.render();
        response.getWriter().write(content);

    }
}

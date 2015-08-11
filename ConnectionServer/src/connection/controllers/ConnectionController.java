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
import connection.utility.ManagerServerChat;
import connection.utility.ZaloInApp;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tript
 */
public class ConnectionController extends HttpServlet {

    ManagerServerChat managerServerChat = new ManagerServerChat();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check login
        String sessionId = null;
        String userId = null;
        //use for zalo api
        // Lay session id tu trong cookie
        Cookie[] cooks = request.getCookies();
        if (cooks != null) {
            for (int i = 0; i < cooks.length; i++) {
                Cookie cookie = cooks[i];
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                } else if ("userId".equals(cookie.getName())) {
                    userId = cookie.getValue();
                }
            }
        }
        String oauthCode = sessionId;

        if (sessionId != null && !"".equals(sessionId)) {
            response.addHeader("Content-Type", "text/html");
            View layout = new View("connection/views/chat", "index.xtm");
            View codeJSView = new View("connection/views/assets", "indexJS.xtm");

            //Tao view
            String resourceHost = "http://";
            resourceHost += Registry.get("connection.server.host", "localhost");
            resourceHost = resourceHost + ":" + Registry.getInt("connection.server.port", 8000) + "/resources";
            layout.setVariable("hostResource", resourceHost);

            layout.setVariable("userAvatar", "http://s240.avatar.talk.zdn.vn/default");
            layout.setVariable("userId", userId);
            layout.setVariable("oauthCode", oauthCode);

            layout.setVariable("CONNECTION_HOST", Registry.get("connection.server.host", "localhost"));

            //set username cho user tren view
            layout.setVariable("userName", "no name");
            codeJSView.setVariable("userName", "no name");
            codeJSView.setVariable("userId", userId);
            codeJSView.setVariable("oauthCode", oauthCode);
            codeJSView.setVariable("hostResource", resourceHost);
            codeJSView.setVariable("BUSINESS_HOST", Registry.get("business.server.host", "localhost"));

            codeJSView.setVariable("BUSINESS_PORT", Registry.get("business.server.port", "8003"));
            codeJSView.setVariable("CHAT_HOST", Registry.get("chat.server1.host", "localhost"));
            codeJSView.setVariable("CONNECTION_HOST", Registry.get("connection.server.host", "localhost"));
            codeJSView.setVariable("CONNECTION_PORT", Registry.get("connection.server.host", "80"));

            String codeJS = codeJSView.render();
            layout.setVariable("codejs", codeJS);
            String content = layout.render();
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(content);
        } else {
            // If user hasn't login, move to Zalo Authentication to login
            OAuthService service = new ZaloServiceBuilder().appID(ZaloInApp.appID).appSecret(ZaloInApp.appSecret).callback(ZaloInApp.urlCallBack).build();
            String urlCode = service.getAuthorizationUrl();
            String newURL = response.encodeRedirectURL(urlCode);
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", newURL);
            response.setContentType("text/html");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

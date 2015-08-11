/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.controllers;

import com.vng.zalosdk.builder.ZaloServiceBuilder;
import com.vng.zalosdk.oauth.OAuthService;
import connection.library.core.Registry;
import connection.library.core.SessionManager;
import connection.library.core.View;
import connection.utility.ZaloInApp;
import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Display information of profile
 *
 * @author biendltb
 */
public class DisplayProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                } else if("userId".equals(cookie.getName())) {
                    userId = cookie.getValue();
                }
            }
        }
        String oauthCode = sessionId;

        if (sessionId != null && !"".equals(sessionId)) {
        
            View layout = new View("connection/views/chat", "profile1.xtm");
        //View codeJSView = new View("connection/views/assets", "indexJS.xtm");

            // Set basic information
            String displayName = request.getParameter("displayName"),
                    userName = request.getParameter("userName"),
                    avatar = request.getParameter("avatar");

            String resourceHost = "http://";
            String hostConnectionStr = Registry.get("connection.server.host", "localhost");
            String portConnectionStr = Registry.get("connection.server.port", "8000");
            layout.setVariable("userId", userId);
            layout.setVariable("oauthCode", oauthCode);
            layout.setVariable("BUSINESS_PORT", Registry.get("business.server.port", "8003"));

            resourceHost += hostConnectionStr;
            resourceHost = resourceHost + ":" + portConnectionStr;
            resourceHost += "/resources";

            layout.setVariable("hostResource", resourceHost);
            layout.setVariable("displayName", displayName);
            layout.setVariable("userName", userName);
            layout.setVariable("userAvatar", avatar);

            // Set other information
            if (true) {
                String birthDate = request.getParameter("birthDate"),
                        gender = request.getParameter("userGender");
                String html_append = "<tr class='info-row'>"
                        + "<td class='info-td'>Ngày Sinh:</td>"
                        + "<td class='info-td'>" + birthDate + "</td>"
                        + "</tr>"
                        + "<tr class='info-row'>"
                        + "<td class='info-td'>Giới Tính:</td>"
                        + "<td class='info-td'>" + gender + "</td>"
                        + "</tr>";
                layout.setVariable("otherInfo", html_append);
            }

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.controllers;

import com.vng.zalosdk.builder.ZaloServiceBuilder;
import com.vng.zalosdk.oauth.OAuthService;
import connection.library.core.SessionManager;
import connection.utility.ZaloInApp;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author biendltb
 */
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie cook_uid = new Cookie("userId", ""),
                cook_sid = new Cookie("sessionId", "");

        cook_uid.setMaxAge(0);
        cook_sid.setMaxAge(0);

        response.addCookie(cook_uid);
        response.addCookie(cook_sid);

        OAuthService service = new ZaloServiceBuilder().appID(ZaloInApp.appID).appSecret(ZaloInApp.appSecret).callback(ZaloInApp.urlCallBack).build();
        String urlCode = service.getAuthorizationUrl();
        String newURL = response.encodeRedirectURL(urlCode);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", newURL);
        response.setContentType("text/html");
    }

}

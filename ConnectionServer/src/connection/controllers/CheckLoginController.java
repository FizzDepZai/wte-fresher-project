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
import connection.utility.ZaloInApp;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author biendltb
 */
public class CheckLoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check session
        HttpSession session = request.getSession();
        // Initialize session mananger 
        SessionManager sessionManager = new SessionManager(session);
        if (sessionManager.isLogin()) {
            // If user already has login, move to main page
            String newURL = "http://fresherchat.zapps.vn/index";
            RequestDispatcher rd = request.getRequestDispatcher(newURL);
            rd.forward(request, response);
        } else {
            // If user hasn't login, move to Zalo Authentication service to check login
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

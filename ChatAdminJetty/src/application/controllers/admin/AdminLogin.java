/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers.admin;

import application.model.business.UserBO;
import application.model.dao.UserDAO;
import application.model.valueobject.UserPOJO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.AdminController;
import library.core.Registry;
import library.core.SessionManager;
import library.core.View;
import library.memcached.SessionClient;

/**
 *
 * @author tript
 */
public class AdminLogin extends AdminController {

    public AdminLogin() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(request, response);
        response.setCharacterEncoding("UTF-8");
        View view = new View("admin", "AdminLogin.xtm");
        String resourceHost = "";
        resourceHost += Registry.get("Host");
        resourceHost = resourceHost + "/resources";
        view.setVariable("hostResource", resourceHost);

        response.addHeader("Content-Type", "text/html");

        //Tao view
        String content = view.render();
        response.getWriter().write(content);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //super.doPost(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserPOJO admin = new UserPOJO();
        admin.userName = username;
        admin.password = password;
        UserBO userBO = new UserBO();
        
        // check admin
        if (userBO.checkLogin(admin)) {
            int session_exp = Integer.parseInt(Registry.get("session_exp"));
            
            String sessionId = SessionManager.init();

            // Neu dang nhap lan dau
            // Kiem tra, khoi tao session, lay oauthCode lam sessionId
            // Them vao cookie
            Cookie cook_sid = new Cookie("sessionId", sessionId);

            cook_sid.setMaxAge(session_exp);

            response.addCookie(cook_sid);

            // Chuyen toi trang index
            String newURL = "http://localhost:8080/index";
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", newURL);
            response.setContentType("text/html");
        }
    }
}

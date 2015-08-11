/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.library.core.Registry;
import business.library.core.View;
import business.middleware.BusinessChatHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.eclipse.jetty.util.ajax.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author root
 */
public class UserProfileController extends HttpServlet {
    Logger logger =Logger.getLogger(UserProfileController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String userId = request.getParameter("userId");
        final String oauthCode = request.getParameter("oauthCode");

        // Option to get all profiles or only display name
        final String option = request.getParameter("option");

        String profile = null;
        BusinessChatHandler handler = new BusinessChatHandler();

        try {
            profile = handler.getProfileZalo(userId, oauthCode);
        } catch (TException ex) {
            logger.error("Get profile from zalo: "+ex);
            profile = "";
        }
        // If option to get only display name
        if ("displayName".equals(option)) {

            response.setContentType("text/json;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().println(profile);
        } 
//            else {
//            Object objProfile = JSONValue.parse(profile);
//            Map mapProfileItem = (HashMap) objProfile;
//            View layout = new View("business/views", "profile.xtm");
//
//            String resourceHost = "http://";
//            String indexPage = "http://";
//            String hostConnectionStr = Registry.get("connection.server.host", "localhost");
//            String portConnectionStr = Registry.get("connection.server.port", "8000");
//
//            resourceHost += hostConnectionStr;
//            resourceHost = resourceHost + ":" + portConnectionStr;
//
//            indexPage = resourceHost + "/index";
//            resourceHost += "/res";
//
//            layout.setVariable("hostResource", resourceHost);
//            layout.setVariable("indexPage", indexPage);
//            layout.setVariable("oauthCode", oauthCode);
//            layout.setVariable("BUSINESS_PORT", Registry.get("business.server.port", "8003"));
//
//            // Standardizing the informations
//            // Birthday
//            String DATE_FORMAT = "dd 'tháng' MM 'năm' yyyy";
//            SimpleDateFormat sfd = new SimpleDateFormat(DATE_FORMAT);
//            String birthday = sfd.format(new Date((Long) mapProfileItem.get("birthDate") * 1000L));
//            // Gender Khác Nam Nữ Không xác định
//            String gender = "";
//            switch ((String) mapProfileItem.get("userGender")) {
//                case "UGEN_Undef":
//                    gender = "Không xác định";
//                    break;
//                case "UGEN_Male":
//                    gender = "Nam";
//                    break;
//                case "UGEN_Female":
//                    gender = "Nữ";
//                    break;
//                case "UGEN_Other":
//                    gender = "Khác";
//                    break;
//            }
//
//            // Set to view
//            layout.setVariable("userAvatar", (String) mapProfileItem.get("avatar"));
//            layout.setVariable("userName", (String) mapProfileItem.get("userName"));
//            layout.setVariable("userId", (String) mapProfileItem.get("userId"));
//            layout.setVariable("displayName", (String) mapProfileItem.get("displayName"));
//            layout.setVariable("birthday", birthday);
//            layout.setVariable("gender", gender);
//
////            JSONParser parser = new JSONParser();
////            JSONObject obj = null;
////            try {
////                obj = (JSONObject) parser.parse(profile);
////            } catch (ParseException ex) {
////                Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
////            }
////            String displayName = (String) obj.get("displayName"),
////                    gender = (String) obj.get("userGender");
////            int birthday = (int)obj.get("birthDate");
////            String content = layout.render();
////            response.setCharacterEncoding("UTF-8");
////            response.getWriter().write(content);
//            response.setContentType("text/json;charset=utf-8");
//            response.setHeader("Access-Control-Allow-Origin", "*");
//            response.getWriter().println(profile);
//
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String userId = request.getParameter("userId");
        final String oauthCode = request.getParameter("oauthCode");

        // Option to get all profiles or only display name
        final String option = request.getParameter("option");

        String profile = null;
        BusinessChatHandler handler = new BusinessChatHandler();

        try {
            profile = handler.getProfileZalo(userId, oauthCode);
        } catch (TException ex) {
            logger.error("getProfileZalo:"+ ex);
        }
        // If option to get only display name

        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().println(profile);
    }

}

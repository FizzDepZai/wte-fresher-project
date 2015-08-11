/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.controllers;

import connection.library.core.Registry;
import connection.utility.ManagerServerChat;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tript Create on 12:41:03 PM Dec 22, 2013
 */
public class ConnectedController extends HttpServlet {

    ManagerServerChat managerServerChat = new ManagerServerChat();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serverForUser = managerServerChat.getServerForUser();
        String keyForPortServer = "chat.server" + serverForUser + ".port";
        System.out.println("Server chat port: " + keyForPortServer);

        //default cho vao server 1
        int portServerChat = Registry.getInt(keyForPortServer, 8001);

        portServerChat = 8001;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print(portServerChat);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}

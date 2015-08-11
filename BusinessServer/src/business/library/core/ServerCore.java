/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.library.core;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * bussiness server chay dinh ky theo period time de check status online cua
 * friend list period time =20s dinh nghia ben views connection;
 *
 * @author tript
 */
public class ServerCore {

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Registry.init("chat.properties");
        PropertyConfigurator.configure("../properties/log4j.business.properties");

        Server server = new Server();
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxIdleTimeMs(200000);
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(50);
        server.setThreadPool(threadPool);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost(Registry.get("business.server.host", "localhost"));
        connector.setPort(Registry.getInt("business.server.port", 8001));

        server.setConnectors(new Connector[]{connector});
        ServletHolder holderImage = context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/resources/*");
        holderImage.setInitParameter("resourceBase", "../resources");
        holderImage.setInitParameter("pathInfoOnly", "true");

        server.setHandler(context);
        context.addServlet(new ServletHolder(new business.controllers.FriendOnlineController()), "/friend_online");
        context.addServlet(new ServletHolder(new business.controllers.OldMessageController()), "/old_message");
        context.addServlet(new ServletHolder(new business.controllers.FriendListController()), "/friendlist");
        context.addServlet(new ServletHolder(new business.controllers.UserProfileController()), "/profile");
        context.addServlet(new ServletHolder(new business.controllers.LoadEmotionController()), "/load_emotion");
        context.addServlet(new ServletHolder(new business.controllers.UploadImageController()), "/upload_image");
        context.addServlet(new ServletHolder(new business.controllers.LoadGroupEmotionController()), "/load_groupemotion");
        context.addServlet(new ServletHolder(new business.controllers.LoginController()), "/login");
        context.addServlet(new ServletHolder(new business.controllers.LogoutController()), "/logout");
        //add worker for chat
//    
        server.start();
        //  server.join();
    }

}

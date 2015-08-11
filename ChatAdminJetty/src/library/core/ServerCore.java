package library.core;

import java.io.File;
import javax.servlet.MultipartConfigElement;
import library.memcached.Memcached;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 *
 * @author tript
 */
public class ServerCore {

    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(System.getProperty("java.io.tmpdir"));

    public static void main(String[] args) throws Exception {
        Registry.init("ChatAdmin.properties");
        PropertyConfigurator.configure("../properties/log4j.admin.properties");

        Server server = new Server();
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxIdleTimeMs(200000);
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(1000);
        server.setThreadPool(threadPool);

        String[] configFiles = {"etc/jetty.xml"};
        for (String configFile : configFiles) {
            XmlConfiguration configuration = new XmlConfiguration(new File(configFile).toURI().toURL());
            configuration.configure(server);
        }

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
       
        context.setContextPath("/");

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost("localhost");
        connector.setPort(8080);

        server.setConnectors(new Connector[]{connector});


        server.setHandler(context);


          ServletHolder holderImage = context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/resources/*");
        holderImage.setInitParameter("resourceBase", "../resources");
        holderImage.setInitParameter("pathInfoOnly", "true");

         context.addServlet(new ServletHolder(new application.controllers.admin.Index()), "/*");
        context.addServlet(new ServletHolder(new application.controllers.admin.Index()), "/index");
        context.addServlet(new ServletHolder(new application.controllers.admin.AdminLogin()), "/adminlogin");
        
        context.addServlet(new ServletHolder(new application.controllers.admin.Emotion()), "/groupEmotion");
        context.addServlet(new ServletHolder(new application.controllers.admin.EmotionList()), "/groupEmotion/emotion");
        context.addServlet(new ServletHolder(new application.controllers.admin.AddEmotion()), "/groupEmotion/emotion/add");
        context.addServlet(new ServletHolder(new application.controllers.admin.DeleteEmotion()), "/groupEmotion/emotion/delete");
        context.addServlet(new ServletHolder(new application.controllers.admin.EditEmotion()), "/groupEmotion/emotion/edit");
        context.addServlet(new ServletHolder(new application.controllers.admin.AddGroupEmotion()), "/groupEmotion/add");
        context.addServlet(new ServletHolder(new application.controllers.admin.DeleteGroupEmotion()), "/groupEmotion/delete");
        context.addServlet(new ServletHolder(new application.controllers.admin.EditGroupEmotion()), "/groupEmotion/edit");
                context.addServlet(new ServletHolder(new application.controllers.admin.AddNewEmotion()), "/groupEmotion/emotion/addEmotion");

        
        
        
      
       //Memcached
        Memcached.run();

        server.start();
        server.join();

    }

}
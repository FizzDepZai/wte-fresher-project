package connection.library.core;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 *
 * @author tript
 */
public class ServerCore {

    /**
     * Connection Server port 8000
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Registry.init("chat.properties");
        PropertyConfigurator.configure("../properties/log4j.connection.properties");

        Server server = new Server();
        QueuedThreadPool threadPool = new QueuedThreadPool();
         threadPool.setMinThreads(1000);
        threadPool.setMaxThreads(3000);
        server.setThreadPool(threadPool);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder holderImage = context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/resources/*");
        holderImage.setInitParameter("resourceBase", "../resources");
        holderImage.setInitParameter("pathInfoOnly", "true");

        context.setContextPath("/");

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost(Registry.get("connection.server.host", "localhost"));
        connector.setPort(Registry.getInt("connection.server.port", 8000));

        server.setConnectors(new Connector[]{connector});

        server.setHandler(context);
        context.addServlet(new ServletHolder(new connection.controllers.ConnectionController()), "/*");
        context.addServlet(new ServletHolder(new connection.controllers.ConnectionController()), "/index");

        context.addServlet(new ServletHolder(new connection.controllers.ConnectedController()), "/connected");
        //context.addServlet(new ServletHolder(new connection.controllers.CheckLoginController()), "/checkLogin");
        context.addServlet(new ServletHolder(new connection.controllers.LoginController()), "/login");
        context.addServlet(new ServletHolder(new connection.controllers.LogoutController()), "/logout");
        context.addServlet(new ServletHolder(new connection.controllers.DisplayProfileController()), "/viewprofile");
        server.start();
        server.join();
    }

}

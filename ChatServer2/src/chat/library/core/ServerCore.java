package chat.library.core;

import chat.controllers.ThreadCheckOnline;
import chat.gearman.worker.GMWorker;
import chat.library.core.Registry;
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
     * Chat Server port 8001-CHAT1
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Registry.init("chat.properties");
        Server server = new Server();
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(1000);
        threadPool.setMaxThreads(3000);
        server.setThreadPool(threadPool);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost(Registry.get("chat.server2.host", "localhost"));
        connector.setPort(Registry.getInt("chat.server2.port", 8002));

        server.setConnectors(new Connector[]{connector});

        server.setHandler(context);
        context.addServlet(new ServletHolder(new chat.controllers.PollController()), "/poll");
        context.addServlet(new ServletHolder(new chat.controllers.ChatController()), "/chat");

        //add worker for chat _dk ten server chat
        GMWorker gmWorker = new GMWorker("CHAT1");
        gmWorker.run();
//        

        server.start();
        //  server.join();
        ThreadCheckOnline threadCheckOnline = new ThreadCheckOnline();
        threadCheckOnline.run();
    }

}

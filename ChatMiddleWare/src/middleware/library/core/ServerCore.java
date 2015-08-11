/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.library.core;

import java.util.ArrayList;
import java.util.List;
import middleware.frontend.ConnectionChatHandler;
import middleware.memcached.MyMemcached;
import middleware.virtual.user.UserManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author biendltb
 */
public class ServerCore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws TTransportException {
        // TODO code application logic here
        int port = Registry.getInt("thrift.connection.server.port", 9090);
        PropertyConfigurator.configure("../properties/log4j.middleware.properties");

        TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9090);
        middleware.library.thrift.ChatProject.Processor processor
                = new middleware.library.thrift.ChatProject.Processor(new ConnectionChatHandler());

        TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).
                processor(processor));

        List<String> listUserOnline = new ArrayList<String>();
        MyMemcached.getInstance().set("userOnline", 0, listUserOnline);

        System.out.println("Starting server on port " + port + " ...");
        createUser();

        server.serve();
    }

    public static void createUser() {
        UserManager.getMapUser().put("1", "a");
        UserManager.getMapUser().put("2", "b");
        UserManager.getMapUser().put("3", "c");
        UserManager.getMapUser().put("4", "d");
        UserManager.getMapUser().put("5", "e");

    }
}

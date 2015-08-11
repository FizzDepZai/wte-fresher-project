/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.gearman.worker;

import chat.utility.Config;
import chat.model.valueobject.MessagePOJO;
import chat.model.valueobject.UserManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.gearman.Gearman;
import org.gearman.GearmanFunction;
import org.gearman.GearmanFunctionCallback;
import org.gearman.GearmanServer;
import org.gearman.GearmanWorker;

/**
 *
 * @author Thanhpv Create on 3:36:00 PM Dec 18, 2013 GMWorker of CHAT2-8001
 */
public class GMWorker implements GearmanFunction {

    String workerChatServerName;

    public GMWorker(String WorkerChatServerName) {
        this.workerChatServerName = WorkerChatServerName;
    }

    public void run() {
        System.out.println("in chat server " + workerChatServerName);
        /*
         * Create a Gearman instance
         */
        Gearman gearman = Gearman.createGearman();

        /*
         * Create the job server object. This call creates an object represents
         * a remote job server.
         * 
         * Parameter 1: the host address of the job server.
         * Parameter 2: the port number the job server is listening on.
         * 
         * A job server receives jobs from clients and distributes them to
         * registered workers.
         */
        GearmanServer server = gearman.createGearmanServer(
                "fresherchat.zapps.vn", 4730);

        /*
         * Create a gearman worker. The worker poll jobs from the server and
         * executes the corresponding GearmanFunction
         */
        GearmanWorker worker = gearman.createGearmanWorker();

        /*
         *  Tell the worker how to perform the echo function
         */
        for (int i = 0; i < Config.numChatServer; i++) {
            if (!("CHAT" + (i + 1)).equals(workerChatServerName)) {
                String functionName = "CHAT" + (i + 1) + "_" + workerChatServerName;
                worker.addFunction(functionName, this);
                System.out.println("function name is: " + functionName);
            }
        }
        /*
         *  Tell the worker that it may communicate with the this job server
         */
        worker.addServer(server);
    }

    @Override
    public byte[] work(String string, byte[] bytes, GearmanFunctionCallback gfc) {
        MessagePOJO message = null;
        try {
            message = (MessagePOJO) MessagePOJO.deserialize(bytes);
        } catch (IOException  ex) {
            Logger.getLogger(GMWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GMWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (message != null) {
            System.out.println("IN " + workerChatServerName + message.getContent());
            UserManager.sendMessageToClient(message);
        }
        return bytes;
    }

}

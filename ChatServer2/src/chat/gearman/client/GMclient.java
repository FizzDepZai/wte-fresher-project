/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.gearman.client;

import chat.model.valueobject.MessagePOJO;
import java.io.IOException;
import org.gearman.Gearman;
import org.gearman.GearmanClient;
import org.gearman.GearmanJobEvent;
import org.gearman.GearmanJobEventCallback;
import org.gearman.GearmanJoin;
import org.gearman.GearmanServer;

/**
 *
 * @author Thanhpv 
 * Create on 5:47:46 PM Dec 18, 2013
 * GMclient of CHAT2-8001
 */
public class GMclient implements GearmanJobEventCallback<String> {

    public static void run(byte[] content) throws InterruptedException, IOException, ClassNotFoundException {
//        System.out.println("deserialize inside client");
//        MessagePOJO message = (MessagePOJO) MessagePOJO.deserialize(content);
//        System.out.println("message.Content inside client: " + message.getContent());
        //create a gearman instance
        Gearman gearman = Gearman.createGearman();
        //create gearman GMclient
        GearmanClient client = gearman.createGearmanClient();
        //create job server
        GearmanServer server = gearman.createGearmanServer("fresherchat.zapps.vn", 4730);
        //
        client.addServer(server);
        GearmanJoin<String> join = client.submitJob(
                "CHAT1_CHAT2", content,
                "CHAT1_CHAT2", new GMclient());
        join.join();
        gearman.shutdown();
    }

    @Override
    public void onEvent(String a, GearmanJobEvent gje) {
        switch (gje.getEventType()) {
            case GEARMAN_JOB_SUCCESS: // Job completed successfully
                System.out.println("IN CLIENT - " + a);
                System.out.println("IN CLIENT - the data received in Client1 from server: " + new String(gje.getData()));
                break;
            case GEARMAN_SUBMIT_FAIL: // The job submit operation failed
                System.out.println("GEARMAN_SUBMIT_FAIL");
            case GEARMAN_JOB_FAIL: // The job's execution failed
                System.err.println(gje.getEventType() + ": "
                        + new String(gje.getData()));
            default:
        }
    }
}

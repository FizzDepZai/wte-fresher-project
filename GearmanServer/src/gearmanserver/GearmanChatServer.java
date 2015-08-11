/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gearmanserver;

import java.io.IOException;
import org.gearman.Gearman;
import org.gearman.GearmanServer;

/**
 *
 * @author root
 */
public class GearmanChatServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println("In server");
        Gearman gearman = Gearman.createGearman();
        GearmanServer server = gearman.startGearmanServer(4730);
    }
    
}


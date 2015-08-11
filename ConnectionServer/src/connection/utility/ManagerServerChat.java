/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author tript
 * Create on 11:19:21 AM  Dec 17, 2013 
 */
public class ManagerServerChat {
    private final  AtomicInteger countServer = new AtomicInteger();
    private final int TOTAL_SERVER_CHAT =2;
    
    /**
     * increment id and return value
     * @return new messageID
     */
    public int  getServerForUser(){
        int newServerId = countServer.incrementAndGet()%TOTAL_SERVER_CHAT+1;
        return newServerId;
    }
    
 
}

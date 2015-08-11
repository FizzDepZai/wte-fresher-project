/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author tript
 * Create on 11:19:21 AM  Dec 17, 2013 
 */
public class ManagerMessageId {
    private final  AtomicInteger countMessageId = new AtomicInteger();
    
    /**
     * increment id and return value
     * @return new messageID
     */
    public int  getNewMessageId(){
        int newMessageId = countMessageId.incrementAndGet();
        return newMessageId;
    }
    
   /**
    * set value for messageID when start server, get from memcached or DB
    */
    public void setMessageIdFromDB(){
     
    }
}

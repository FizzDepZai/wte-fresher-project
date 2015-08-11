/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.utility;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import middleware.memcached.MyMemcached;

/**
 *
 * @author tript Create on 11:19:21 AM Dec 17, 2013
 */
public class ManagerMessageId {

    private AtomicInteger countMessageId = new AtomicInteger();

    /**
     * increment id and return value
     *
     * @param sendingUserId
     * @param receiveUserId
     * @return new messageID
     */
    public int getNewMessageId(String sendingUserId, String receiveUserId) {
        //test du lieu tam thoi voi memcached
        
        List<Long> listMessageId = (List<Long>) MyMemcached.getInstance().get(createListMsgIdKey(sendingUserId,receiveUserId));
        if (listMessageId != null && listMessageId.size() > 0) {
            countMessageId.set(listMessageId.size());
        }
        //test
        int newMessageId = countMessageId.incrementAndGet();
        return newMessageId;
    }

    /**
     * set value for messageID when start server, get from memcached or DB
     */
    public void setMessageIdFromDB() {

    }

    private String createListMsgIdKey(String sendingUserId, String receiveUserId) {
        StringBuilder keyOfList = new StringBuilder();
        // SmallerUserId_BiggerUserId
        if (sendingUserId.compareTo(receiveUserId) < 0) {
            keyOfList.append(sendingUserId);
            keyOfList.append("_");
            keyOfList.append(receiveUserId);
        } else {
            keyOfList.append(receiveUserId);
            keyOfList.append("_");
            keyOfList.append(sendingUserId);
        }

        return keyOfList.toString();
    }
}

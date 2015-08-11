/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model.valueobject;

import chat.library.core.ConnectionChatHandler;
import chat.library.thrift.MsgItem;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tript Create on 2:16:18 PM Dec 17, 2013
 */
public class UserManager {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserManager.class.getName());
    //threadsafe
    private static final Map<String, UserChatPOJO> mapUser = new ConcurrentHashMap<String, UserChatPOJO>();

    public static Map<String, UserChatPOJO> getMapUser() {
        return mapUser;
    }

    /**
     * put new user to map
     *
     * @param user
     */
    public static synchronized void putUser(UserChatPOJO user) {
        mapUser.put(user.getUserId(), user);
    }

    /**
     * get user from map
     *
     * @param keyUserId
     * @return
     */
    public static synchronized UserChatPOJO getUser(String keyUserId) {
        return mapUser.get(keyUserId);

    }

    public static void sendMessageToClient(MessagePOJO message) {
        ConnectionChatHandler handler = new ConnectionChatHandler();
        MsgItem msgItem = new MsgItem(0, message.getSendingUserId(), message.getReceiveUserId(), message.getContent(), message.getDateTimeSend(), message.getType());

        //kiem user trong danh sach user cua server chat
        if (mapUser.get(message.getReceiveUserId()) != null) {
            //neu synchronized message moi fai dung ngoai doi
//            synchronized (mapUser.get(message.getReceiveUserId())) {
                //add new message vao danh sach message cua user do 
                mapUser.get(message.getReceiveUserId()).addNewMessage(message);
                if (mapUser.get(message.getReceiveUserId()).getContinuation() != null) {
                    //resume the request
                    //resume poll request
                    mapUser.get(message.getReceiveUserId()).getContinuation().resume();
                    mapUser.get(message.getReceiveUserId()).setContinuation(null);
                    handler.sendMessageInternal(msgItem, true);
                    System.out.println("send message onl");

                }
                //gui tiep message xuong middleware
                //set message id mac dinh la 0 de phia duoi middleware cap lai
//            }
        } else {

            handler.sendMessageInternal(msgItem, false);
            System.out.println("send message off");

        }
    }
}

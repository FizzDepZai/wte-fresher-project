/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.library.core;

import chat.controllers.ChatController;
import chat.library.thrift.EmotionItem;
import chat.library.thrift.EmotionStatistic;
import chat.library.thrift.GroupEmotion;
import chat.library.thrift.MsgItem;
import chat.library.thrift.RoomItem;
import chat.library.thrift.RoomStatistic;
import chat.library.thrift.UserItem;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author tript Create on 11:57:29 AM Dec 20, 2013
 */
public class ConnectionChatHandler implements chat.library.thrift.ChatProject.Iface {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConnectionChatHandler.class);

    TTransport transport;
    chat.library.thrift.ChatProject.Client client;

    public ConnectionChatHandler() {
        String host = Registry.get("thrift.connection.server.host", "localhost");
        int port = Registry.getInt("thrift.connection.server.port", 9090);
        transport = new TSocket(host, port);
        TFramedTransport framedTransport = new TFramedTransport(transport);
        TProtocol protocol = new TBinaryProtocol(framedTransport);
        client = new chat.library.thrift.ChatProject.Client(protocol);
    }

    @Override
    public boolean sendMessageInternal(MsgItem msgItem, boolean messageOnline) {
        try {
            transport.open();
            client.sendMessageInternal(msgItem, messageOnline);
            transport.close();

        } catch (TException ex) {
            logger.error("sendMessageInternal: " + ex);
            return false;
        }
        return true;
    }

    @Override
    public void notifyUserOnline(String userId) {
        try {
            if (!transport.isOpen()) {
                transport.open();
            }
            client.notifyUserOnline(userId);
            transport.close();
        } catch (TException ex) {
            logger.error("notifyUserOnline: " + ex);
        }
    }

    @Override
    public void notifyListUserOffline(List<String> listUserIdOffline) {
        try {
            transport.open();
            client.notifyListUserOffline(listUserIdOffline);
            transport.close();
        } catch (TException ex) {
            logger.error("notifyListUserOffline: " + ex);

        }

    }

    @Override
    public List<RoomItem> getAllChatRoom() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserItem> getListUserInRoom(String roomId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GroupEmotion> getAllEmotionGroup() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupEmotion getEmotionGroup(String emotionGroupId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addRoom(RoomItem newRoom) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editRoom(RoomItem room) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRoom(String roomId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEmotion(String emotionItem) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editEmotion(long emotionId, EmotionItem emotionItem) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEmotion(long emotionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void kickUser(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RoomStatistic getRoomStatisticByDate(int date) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmotionStatistic getEmotionStatisticByDate(int date) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAllEmotionInGroup(String groupEmotionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteGroupEmotion(String groupEmotionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String addGroupEmotion(String groupEmotionName) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String gelAllGroupEmotion() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String loadAllEmotion() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEmotionWithGroup(String groupId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkImageEmotionExist(String imageStyle, String groupEmotionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveListMsgId(List<Long> listMsgId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveOfflineMsgNotify(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MsgItem getMessageItem(long MsgId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveRoomMsg(MsgItem msg, String roomId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MsgItem> getRoomMsg(String roomId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserItem> getUserInRoom(String roomId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserItem getUserProfile(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserItem> getFriendList(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getNewMsgId() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param userId1
     * @param userId2
     * @param totalMsg tong so message hien co trong khung chat
     * @r
     * @throws org.apache.thrift.TExceptioneturn
     */
    @Override
    public List<MsgItem> getMessageList(String userId1, String userId2, int totalMsg) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public List<Long> getMessageIdList(String userId1, String userId2, int totalMsg) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Boolean> checkFriendOnline(List<String> listFriendId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFriendListZalo(String userId, String oAuthCode) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getProfileZalo(String userId, String oAuthCode) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> checkOfflineMessage(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogin(String sessionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLogin(String sessionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogout(String sessionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long getImageId() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adminRegister(String userName, String password) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkAdmin(String userName, String password) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllAdmin() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

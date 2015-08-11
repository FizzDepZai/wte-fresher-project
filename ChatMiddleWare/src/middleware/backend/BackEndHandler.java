/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.backend;

import java.util.List;
import java.util.logging.Level;
import middleware.frontend.ConnectionChatHandler;
import middleware.library.core.*;
import middleware.library.thrift.ChatProject;
import middleware.library.thrift.EmotionItem;
import middleware.library.thrift.EmotionStatistic;
import middleware.library.thrift.GroupEmotion;
import middleware.library.thrift.MsgItem;
import middleware.library.thrift.RoomItem;
import middleware.library.thrift.RoomStatistic;
import middleware.library.thrift.UserItem;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author biendltb
 */
public class BackEndHandler implements middleware.library.thrift.ChatProject.Iface {

    Logger logger = Logger.getLogger(BackEndHandler.class);

    public TTransport transport;
    middleware.library.thrift.ChatProject.Client backendClient;

    public BackEndHandler() {

        String host = Registry.get("backend.message.server.host", "localhost");
        int port = Registry.getInt("backend.message.server.port", 9091);
        transport = new TSocket(host, port);
        TFramedTransport framedTransport = new TFramedTransport(transport);
        TProtocol protocol = new TBinaryProtocol(framedTransport);
        backendClient = new middleware.library.thrift.ChatProject.Client(protocol);

    }

    @Override
    public boolean sendMessageInternal(MsgItem msgItem, boolean messageOnline) {

        try {
            if (!transport.isOpen()) {
                transport.open();
            }
            backendClient.sendMessageInternal(msgItem, messageOnline);
            transport.close();
        } catch (TException ex) {
            logger.error("sendMessageInternal: " + ex);
            return false;
        }

        return true;
    }

    /**
     * Get list of message id from back-end
     *
     * @param userId1
     * @param userId2
     * @param currentMsgId
     * @return list of message id
     */
    @Override
    public List<Long> getMessageIdList(String userId1, String userId2, int totalMsg) {
        List<Long> listMsgId = null;
        try {
            if (!transport.isOpen()) {
                transport.open();
            }
            listMsgId = backendClient.getMessageIdList(userId1, userId2, totalMsg);
            transport.close();
        } catch (TException ex) {
            logger.error("getMessageIdList: " + ex);

        }
        return listMsgId;
    }

    /**
     * Get message item from backend by message id
     *
     * @param userId1
     * @param userId2
     * @param MsgId
     * @return
     */
    @Override
    public MsgItem getMessageItem(long MsgId) throws TException {
        MsgItem msg = null;
        try {
            if (!transport.isOpen()) {
                transport.open();
            }
            msg = backendClient.getMessageItem(MsgId);
            transport.close();
        } catch (TException ex) {
            logger.error("getMessageItem: " + ex);

        }

        return msg;
    }

    @Override
    public long getNewMsgId() {
        long msgId = 0;

        try {
            if (!transport.isOpen()) {
                transport.open();
            }
            msgId = backendClient.getNewMsgId();

            transport.close();
        } catch (TException ex) {
            logger.error("getNewMsgId: " + ex);

        }
        return msgId;
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
        String emotion = "";
//        try {
//            transport.open();
//            emotion = backendClient.loadAllEmotion();
//            transport.close();
//        } catch (TException ex) {
//            logger.error("loadAllEmotion: " + ex);
//
//        }
        return emotion;
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
    public void notifyUserOnline(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyListUserOffline(List<String> listUserIdOffline) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MsgItem> getMessageList(String userId1, String userId2, int totalMsg) throws TException {
        transport.open();
        List<MsgItem> listOldMessage = backendClient.getMessageList(userId1, userId2, totalMsg);
        transport.close();
        return listOldMessage;
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
        List<String> listFriendIdHaveOffline = null;
        try {
            transport.open();
            listFriendIdHaveOffline = backendClient.checkOfflineMessage(userId);
            transport.close();
        } catch (TException ex) {
            logger.error("checkOfflineMessage: " + ex);

        }
        return listFriendIdHaveOffline;
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

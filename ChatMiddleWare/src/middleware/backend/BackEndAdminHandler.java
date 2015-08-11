/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.backend;

import java.util.List;
import middleware.library.core.Registry;
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

/**
 *
 * @author tript Create on 7:55:04 AM Jan 3, 2014
 */
public class BackEndAdminHandler implements middleware.library.thrift.ChatProject.Iface {

    Logger logger = Logger.getLogger(BackEndHandler.class);
    public TTransport transport;
    middleware.library.thrift.ChatProject.Client backendClient;

    public BackEndAdminHandler() {
        String host = Registry.get("backend.admin.server.host", "localhost");
        int port = Registry.getInt("backend.admin.server.port", 8989);
        transport = new TSocket(host, port);
        TFramedTransport framedTransport = new TFramedTransport(transport);
        TProtocol protocol = new TBinaryProtocol(framedTransport);
        backendClient = new middleware.library.thrift.ChatProject.Client(protocol);
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
        String strListGroupEmotion = "";
        try {
            transport.open();
            strListGroupEmotion = backendClient.gelAllGroupEmotion();
            transport.close();
        } catch (TException ex) {
            logger.error("loadAllEmotion: " + ex);

        }
        return strListGroupEmotion;
    }

    @Override
    public String loadAllEmotion() throws TException {
        String emotion = "";
        try {
            transport.open();
            emotion = backendClient.loadAllEmotion();
            transport.close();
        } catch (TException ex) {
            logger.error("loadAllEmotion: " + ex);

        }
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
    public boolean sendMessageInternal(MsgItem msgItem, boolean messageOnline) throws TException {
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
    public List<MsgItem> getMessageList(String userId1, String userId2, int totalMsg) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Long> getMessageIdList(String userId1, String userId2, int totalMsg) throws TException {
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

    @Override
    public void notifyUserOnline(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void notifyListUserOffline(List<String> listUserIdOffline) throws TException {
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
        Long imgId;
        if(!transport.isOpen()){
            transport.open();
            
        }
        imgId = backendClient.getImageId();
        transport.close();
        return imgId;
               
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

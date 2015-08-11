/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.middleware;

import business.library.core.Registry;
import business.library.thrift.EmotionItem;
import business.library.thrift.EmotionStatistic;
import business.library.thrift.GroupEmotion;
import business.library.thrift.MsgItem;
import business.library.thrift.RoomItem;
import business.library.thrift.RoomStatistic;
import business.library.thrift.UserItem;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author tript Create on 3:39:52 PM Dec 21, 2013
 */
public class BusinessChatHandler implements business.library.thrift.ChatProject.Iface {

    TTransport transport;
    business.library.thrift.ChatProject.Client client;

    public BusinessChatHandler() {
        String host = Registry.get("thrift.connection.server.host", "localhost");
        int port = Registry.getInt("thrift.connection.server.port", 9090);
        transport = new TSocket(host, port);
        TFramedTransport framedTransport = new TFramedTransport(transport);
        TProtocol protocol = new TBinaryProtocol(framedTransport);
        client = new business.library.thrift.ChatProject.Client(protocol);
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
        transport.open();
        String listGroupEmotion = "";
        listGroupEmotion = client.gelAllGroupEmotion();
        transport.close();
        return listGroupEmotion;
    }

    @Override
    public String loadAllEmotion() throws TException {
        transport.open();
        String listEmotion = "";
        listEmotion = client.loadAllEmotion();
        transport.close();
        return listEmotion;
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

    /**
     *
     * @param userId1
     * @param userId2
     * @param totalMsg tong so message hien co trong khung chat
     * @return
     * @throws org.apache.thrift.TException
     */
    @Override
    public List<MsgItem> getMessageList(String userId1, String userId2, int totalMsg) throws TException {
        transport.open();
        List<MsgItem> listOldMesage = client.getMessageList(userId1, userId2, totalMsg);
        transport.close();
        return listOldMesage;
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
        transport.open();
        List<Boolean> friendListOnline = client.checkFriendOnline(listFriendId);
        transport.close();
        return friendListOnline;
    }

    @Override
    public String getFriendListZalo(String userId, String oAuthCode) throws TException {

        transport.open();
        String listFriend = "";
        listFriend = client.getFriendListZalo(userId, oAuthCode);
        transport.close();
        return listFriend;
    }

    @Override
    public String getProfileZalo(String userId, String oAuthCode) throws TException {
        transport.open();
        String profile = "";
        profile = client.getProfileZalo(userId, oAuthCode);
        transport.close();
        return profile;
    }

    @Override
    public List<String> checkOfflineMessage(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogin(String sessionId) throws TException {
        transport.open();
        client.setLogin(sessionId);
        transport.close();
    }

    @Override
    public boolean isLogin(String sessionId) throws TException {
        transport.open();
        boolean checkLogin = client.isLogin(sessionId);
        transport.close();
        return checkLogin;
    }

    @Override
    public void setLogout(String sessionId) throws TException {
        transport.open();
        client.setLogout(sessionId);
        transport.close();
    }

    @Override
    public long getImageId() throws TException {
        Long imageId;
        transport.open();
        imageId = client.getImageId();
        transport.close();
        return imageId;
                
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

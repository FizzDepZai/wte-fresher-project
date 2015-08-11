/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.middleware;

import java.util.List;
import library.core.Registry;
import library.thrift.EmotionItem;
import library.thrift.EmotionStatistic;
import library.thrift.GroupEmotion;
import library.thrift.MsgItem;
import library.thrift.RoomItem;
import library.thrift.RoomStatistic;
import library.thrift.UserItem;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author tript Create on 9:24:19 AM Jan 3, 2014
 */
public class ChatAdminHandler implements library.thrift.ChatProject.Iface {

    TTransport transport;
    library.thrift.ChatProject.Client client;

    public ChatAdminHandler() {
        String host = Registry.get("thrift.connection.server.host");
        int port = Integer.parseInt(Registry.get("thrift.connection.server.port"));
        transport = new TSocket(host, port);
        TFramedTransport framedTransport = new TFramedTransport(transport);
        TProtocol protocol = new TBinaryProtocol(framedTransport);
        client = new library.thrift.ChatProject.Client(protocol);
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
    public void addRoom(RoomItem newRoom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editRoom(RoomItem room) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRoom(String roomId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEmotion(String emotionItem) throws TException {
        transport.open();
        client.addEmotion(emotionItem);
        transport.close();
    }

    @Override
    public void editEmotion(long emotionId, EmotionItem emotionItem) throws TException {
         transport.open();
        client.editEmotion(emotionId,emotionItem);
        transport.close();
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
    public RoomStatistic getRoomStatisticByDate(int date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmotionStatistic getEmotionStatisticByDate(int date) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteAllEmotionInGroup(String groupEmotionId) throws TException {
        transport.open();
        boolean result = client.deleteAllEmotionInGroup(groupEmotionId);
        transport.close();
        return result;
    }

    @Override
    public boolean deleteGroupEmotion(String groupEmotionId) throws TException {
        transport.open();
        boolean result = client.deleteGroupEmotion(groupEmotionId);
        transport.close();
        return result;
    }

    @Override
    public String addGroupEmotion(String groupEmotionName) throws TException {
        transport.open();
        String groupEmotionId = client.addGroupEmotion(groupEmotionName);
        transport.close();
        return groupEmotionId;
    }

    @Override
    public String gelAllGroupEmotion() throws TException {
        transport.open();
        String strListGroupEmotion = client.gelAllGroupEmotion();
        transport.close();
        return strListGroupEmotion;
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
        transport.open();
        String listEmotion = "";
        listEmotion = client.getEmotionWithGroup(groupId);
        transport.close();
        return listEmotion;
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
    public UserItem getUserProfile(String userId) {
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
    public List<String> checkOfflineMessage(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void adminRegister(String userName, String password) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkAdmin(String userName, String password) throws TException {
        if(!transport.isOpen()){
            transport.open();
        }
        boolean isAdmin = false;
        isAdmin = client.checkAdmin(userName, password);
        transport.close();
        return isAdmin;
    }

    @Override
    public List<String> getAllAdmin() throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

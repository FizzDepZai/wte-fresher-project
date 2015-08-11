/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.frontend;

import com.vng.zalosdk.entity.ShortProfile;
import com.vng.zalosdk.entity.StdProfile;
import middleware.library.core.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import middleware.backend.BackEndAdminHandler;
import middleware.backend.BackEndHandler;
import middleware.library.thrift.EmotionItem;
import middleware.library.thrift.EmotionStatistic;
import middleware.library.thrift.GroupEmotion;
import middleware.library.thrift.MsgItem;
import middleware.library.thrift.RoomItem;
import middleware.library.thrift.RoomStatistic;
import middleware.library.thrift.UserItem;
import middleware.memcached.MyMemcached;
import middleware.model.business.UserZaloBO;
import middleware.utility.ManagerMessageId;
import middleware.virtual.user.UserManager;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 *
 * @author biendltb
 */
public class ConnectionChatHandler implements middleware.library.thrift.ChatProject.Iface {

    Logger logger = Logger.getLogger(ConnectionChatHandler.class);

    ManagerMessageId managerMessageId = new ManagerMessageId();
    BackEndHandler handler = new BackEndHandler();
    BackEndAdminHandler adminHandler = new BackEndAdminHandler();
    // Expiration time of a message item
    private int exp_msg = 0,
            exp_zaloService = 0,
            exp_session = 0;

    // Maximum message client can get in each request time
    private int maxMsgGet = 0;

    public ConnectionChatHandler() {
        // Get expiration time from config file
        Registry.init("chat.properties");
        exp_msg = Registry.getInt("exp_msg", 3600);
        exp_zaloService = Registry.getInt("exp_zaloService", 3600);
        // Get maximum message client can get from config file
        maxMsgGet = Registry.getInt("max_msg_get", 30);
        exp_session = Registry.getInt("session_exp", 86400);
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
        return adminHandler.gelAllGroupEmotion();
    }

    @Override
    public String loadAllEmotion() throws TException {
        return adminHandler.loadAllEmotion();

    }

    @Override
    public String getEmotionWithGroup(String groupId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkImageEmotionExist(String imageStyle, String groupEmotionId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Append message id to list of message id in memcached, save message item
     * to memcached and send message item to backend
     *
     * @param msgItem
     * @return
     */
    @Override
    public boolean sendMessageInternal(MsgItem msgItem, boolean messageOnline) {
//         Generate message id and add it to message item
//        int messageId = managerMessageId.getNewMessageId(msgItem.sendingUserId, msgItem.receiveUserId);

        long messageId = handler.getNewMsgId();
        msgItem.msgId = messageId;
        try {
            updateListMsgId(msgItem);
        } catch (IOException ex) {
            logger.error("sendMessageInternal:" + ex);
        } catch (InterruptedException ex) {
            logger.error("sendMessageInternal:" + ex);
        } catch (ExecutionException ex) {
            logger.error("sendMessageInternal:" + ex);
        }

//        System.out.println("Save message id and message item to memcached...");
        System.out.println("New message: " + msgItem.msgId + "Msg content: " + msgItem.content);
        // Add message item to memcached
        String key = Long.toString(messageId);
        MyMemcached.getInstance().set(key, exp_msg, msgItem);
        // Save message to database
        handler.sendMessageInternal(msgItem, messageOnline);
        // Check user online
        // If receiving user's offline, mark to offline message
        return true;
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
     * Get list of recent message and return to front-end
     *
     * @param userId1
     * @param userId2
     * @param currentMsgId
     * @return list of recent message
     */
    @Override
    public List<MsgItem> getMessageList(String userId1, String userId2, int totalMsg) {
        // List of message id need to get
        List<Long> listMsgId = null;
        List<MsgItem> listMsg = new ArrayList<MsgItem>();

        // Find list message id from memcached
        String key = createListMsgIdKey(userId1, userId2);
        listMsgId = (List<Long>) MyMemcached.getInstance().get(key);

        // If number of message in memcached larger than total message, get from memcached
        if (listMsgId != null) {
            if (listMsgId.size() > totalMsg) {
                System.out.println("Get from memcached: " + listMsgId.size() + " messages\n");

                listMsgId = listMsgId.subList(listMsgId.size() - totalMsg - maxMsgGet >= 0
                        ? (listMsgId.size() - totalMsg - maxMsgGet) : 0, listMsgId.size() - totalMsg);

                //Collections.reverse(listMsgId);
            } else {
                // Get a list of recent message id (default: 30msg) from back-end
                listMsgId = handler.getMessageIdList(userId1, userId2, totalMsg);
                System.out.println("Get from backend1.\n");
                List<Long> listMsgTmp = (List<Long>) MyMemcached.getInstance().get(key);
                //list msgId lay tu backend phai nam o tren dau
                for (int i = 0; i < listMsgId.size(); i++) {
                    listMsgTmp.add(listMsgId.get(i));
                }
                MyMemcached.getInstance().set(key, exp_msg, listMsgTmp);
            }
        } else {
            // Get a list of recent message id (default: 30msg) from back-end
            System.out.println("Get from backend2. total msg: " + totalMsg + "\n");
            listMsgId = handler.getMessageIdList(userId1, userId2, totalMsg);
            System.out.println("Get from backend2.: " + listMsgId + "\n");
            if (listMsgId == null) {
                logger.info("listMsgID null");
                return listMsg;
            }
            MyMemcached.getInstance().set(key, exp_msg, listMsgId);

        }

        // List contain message item to response to server chat
        System.out.println("list msg id:  " + listMsgId.size());

        long start = System.nanoTime();

        for (long msgId : listMsgId) {
            MsgItem msg = null;
            msg = (MsgItem) MyMemcached.getInstance().get(Long.toString(msgId));

            // If it doesn't exist in memcached, get it from backend
            if (msg == null) {
                try {
                    msg = handler.getMessageItem(msgId);
                } catch (TException ex) {
                    logger.error("getMessageList:" + ex);
                }
            }

            // Add to list of message item
            listMsg.add(msg);
        }
        long end = System.nanoTime();
        long microseconds = end - start;
        System.out.println("Time run for read  microsecond: " + microseconds);
        return listMsg;
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
    public void notifyUserOnline(String userId
    ) {
        // Check if key exists in memcached
        Object obj = MyMemcached.getInstance().get("userOnline");
        if (obj == null) {
            // If key doesn't exist, create a list contain msg id and set to memcached
            List<String> listUserOnline = new ArrayList<String>();
            listUserOnline.add(userId);
            MyMemcached.getInstance().set("userOnline", 0, listUserOnline);
        } else {
            // If key exists in memcached, load it to a list 
            //and append message id to it, save to memcached and replace the old
            List<String> listUserOnline = new ArrayList<String>();
            listUserOnline = (List<String>) obj;
            if (!listUserOnline.contains(userId)) {
                listUserOnline.add(userId);
            }
            MyMemcached.getInstance().set("userOnline", 0, listUserOnline);
            for (String userIdElems : listUserOnline) {
                System.out.print(userIdElems + ",");
                System.out.print("\n");
            }
        }
    }

    @Override
    public void notifyListUserOffline(List<String> listUserIdOffline
    ) {
        ArrayList<String> listUserOnline = (ArrayList<String>) MyMemcached.getInstance().get("userOnline");
        //khi khong co user online thi return
        if (listUserOnline == null) {
            return;
        }
        for (String userIdOffline : listUserIdOffline) {
            if (listUserOnline.contains(userIdOffline)) {
                listUserOnline.remove(userIdOffline);
            }
        }
        MyMemcached.getInstance().set("userOnline", 0, listUserOnline);
        for (String userIdElems : listUserOnline) {
            System.out.println(userIdElems + ",");
        }
    }

    /**
     * tao ra key cua list message id Create key of list message id
     * (userId_userId)
     */
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

    private void updateListMsgId(MsgItem msgItem) throws IOException, InterruptedException, ExecutionException {
        // Create key to store list message id in memcache
        String key = createListMsgIdKey(msgItem.sendingUserId, msgItem.receiveUserId);
        // Check if key exists in memcached
        Object o = MyMemcached.getInstance().get(key);
        if (o == null) {
            // If key doesn't exist, create a list contain msg id and set to memcached
            List<Long> listMsgId = new ArrayList<Long>();
            listMsgId.add(msgItem.msgId);
            //set(key, listMsgId);
            MyMemcached.getInstance().set(key, exp_msg, listMsgId);
        } else {
            // If key exists in memcached, load it to a list 
            //and append message id to it, save to memcached and replace the old
            List<Long> listMsgId = new ArrayList<Long>();
            listMsgId = (List<Long>) o;
            if (!listMsgId.contains(msgItem.msgId)) {
                listMsgId.add(msgItem.msgId);
            }
            MyMemcached.getInstance().set(key, exp_msg, listMsgId);
        }
    }

    @Override
    public List<Boolean> checkFriendOnline(List<String> listFriendId) throws TException {
        //kiem tra danh sach user online tren memcached va tra ket qua list status ve cho frontend
        List<Boolean> listFriendOnlineStatus = new LinkedList<Boolean>();
        List<String> listUserOnline = (List<String>) MyMemcached.getInstance().get("userOnline");
        System.out.println("listUserOnline:" + listUserOnline);
        if (listUserOnline == null) {
            return listFriendOnlineStatus;
        } else if (listFriendId != null) {
            for (int i = 0; i < listFriendId.size(); i++) {
                if (listUserOnline.contains(listFriendId.get(i))) {
                    listFriendOnlineStatus.add(true);

                } else {
                    listFriendOnlineStatus.add(false);
                }
            }
        }
        return listFriendOnlineStatus;
    }

    @Override
    public String getFriendListZalo(String userId, String oAuthCode) {
        String friendList = "";
        // Search in memcached
        String key = userId + "_friendlist";
        friendList = (String) MyMemcached.getInstance().get(key);
        // If friendlist doesn't exist in memcached, get friend list from zalo
        if (friendList == null || "".equals(friendList)) {
            List friendListJSONObject = new LinkedList();
            List friendListJSONObjectMemcached = new LinkedList();
            // Get friend list from zalo
            UserZaloBO userZaloBO = new UserZaloBO(oAuthCode);
            List<ShortProfile> friendListZalo = null;

            try {
                friendListZalo = userZaloBO.getFriends();
            } catch (Exception e) {
                friendListZalo = null;
            }
            List<String> listFriendIdHaveOffline = null;
            try {
                listFriendIdHaveOffline = handler.checkOfflineMessage(userId);
            } catch (TException ex) {
                logger.error("checkOfflineMessage: " + ex);
            }

            if (friendListZalo != null) {
                // Parse ShortProfile to hash map and add to linked list
                for (int i = 0; i < friendListZalo.size(); i++) {
                    HashMap mapFriendItemMemcached = new HashMap();
                    HashMap mapFriendItem = new HashMap();
                    mapFriendItem.put("userId", friendListZalo.get(i).userId);
                    mapFriendItem.put("userName", friendListZalo.get(i).userName);
                    mapFriendItem.put("displayName", friendListZalo.get(i).displayName);
                    mapFriendItem.put("avatar", friendListZalo.get(i).avatar);

                    mapFriendItemMemcached.put("userId", friendListZalo.get(i).userId);
                    mapFriendItemMemcached.put("userName", friendListZalo.get(i).userName);
                    mapFriendItemMemcached.put("displayName", friendListZalo.get(i).displayName);
                    mapFriendItemMemcached.put("avatar", friendListZalo.get(i).avatar);
                    mapFriendItemMemcached.put("hasOffline", false);

                    if (listFriendIdHaveOffline == null) {
                        mapFriendItem.put("hasOffline", false);
                    } else if (listFriendIdHaveOffline.contains(Integer.toString(friendListZalo.get(i).userId))) {
                        mapFriendItem.put("hasOffline", true);
                    } else {
                        mapFriendItem.put("hasOffline", false);

                    }

                    friendListJSONObject.add(mapFriendItem);
                    friendListJSONObjectMemcached.add(mapFriendItemMemcached);
                }
            } else {
                //get friend list user ao
                //start friend list ao
                //tao ra friend list json chuyen  wa views luu tru

                for (String virtualUserKey : UserManager.getMapUser().keySet()) {
                    if ((virtualUserKey.equals(userId)) == false) {

                        HashMap mapFriendItem = new HashMap();
                        mapFriendItem.put("userId", virtualUserKey);
                        mapFriendItem.put("userName", virtualUserKey);
                        mapFriendItem.put("displayName", UserManager.getMapUser().get(virtualUserKey));
                        mapFriendItem.put("avatar", "http://fresherchat.zapps.vn/resources/img/default.png");
                        if (listFriendIdHaveOffline == null) {
                            mapFriendItem.put("hasOffline", false);
                        } else if (listFriendIdHaveOffline.contains(virtualUserKey)) {
                            mapFriendItem.put("hasOffline", true);
                        } else {
                            mapFriendItem.put("hasOffline", false);

                        }

                        friendListJSONObject.add(mapFriendItem);

                    }

                }

            }
            friendList = JSONValue.toJSONString(friendListJSONObject);
            String friendListMemcached = JSONValue.toJSONString(friendListJSONObjectMemcached);
            // Set friend list to memcached
            MyMemcached.getInstance().set(key, exp_zaloService, friendListMemcached);
        }
        return friendList;
    }

    /**
     * get profile of current user
     *
     * @param userId
     * @param oAuthCode
     * @return profile of user after login
     */
    @Override
    public String getProfileZalo(String userId, String oAuthCode) {
        String profile = "";

        // Search in memcached
        String key = userId + "_profile";
        profile = (String) MyMemcached.getInstance().get(key);

        if (profile == null) {
            UserZaloBO userZaloBO = new UserZaloBO(oAuthCode);
            StdProfile profileZalo = userZaloBO.getProfile();
            Map mapProfile = new HashMap();

            if (profileZalo != null) {
                mapProfile.put("avatar", profileZalo.avatar);
                mapProfile.put("birthDate", profileZalo.birthDate);
                mapProfile.put("displayName", profileZalo.displayName);
                mapProfile.put("userGender", profileZalo.userGender.toString());
                mapProfile.put("userId", profileZalo.userId);
                mapProfile.put("userName", profileZalo.userName);
            } else {

                //get virtual profile
                mapProfile.put("avatar", "http://s240.avatar.talk.zdn.vn/default");
                mapProfile.put("birthDate", "0");
                mapProfile.put("displayName", UserManager.getMapUser().get(userId));
                mapProfile.put("userGender", "Male");
                mapProfile.put("userId", userId);
                mapProfile.put("userName", "1");
            }

            profile = JSONValue.toJSONString(mapProfile);

            // Set to memcached
            MyMemcached.getInstance().set(key, exp_zaloService, profile);
        }
        return profile;
    }

    /**
     * run when get friend list
     *
     * @param userId
     * @return
     * @throws TException
     */
    @Override
    public List<String> checkOfflineMessage(String userId) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogin(String sessionId) {
        // Get 100 first character for the key
        String key = sessionId.substring(0, 100);
        MyMemcached.getInstance().set(key, exp_msg, true);
    }

    @Override
    public boolean isLogin(String sessionId) {
        // Get 100 first character for the key
        String key = sessionId.substring(0, 100);
        boolean isLogin  =false;
        try {
             isLogin = (Boolean) MyMemcached.getInstance().get(key);
        } catch(NullPointerException ex) {
            logger.error("isLogin:" +ex);
                  
        }

        return isLogin;
    }

    @Override
    public void setLogout(String sessionId) {
        // Get 100 first character for the key
        String key = sessionId.substring(0, 100);
        MyMemcached.getInstance().delete(key);
    }

    @Override
    public long getImageId() throws TException {
        return adminHandler.getImageId();
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

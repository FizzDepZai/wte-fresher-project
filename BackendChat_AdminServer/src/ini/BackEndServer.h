/* 
 * File:   BackEndServer.h
 * Author: root
 *
 * Created on December 21, 2013, 3:14 PM
 */

#ifndef BACKENDSERVER_H
#define	BACKENDSERVER_H

//#include "Database.h"
#include "JobWorker.h"
//#include "Workers.h"
#include "thrift/ChatProject.h"
#include "WriteToDBWorkers.h"
#include <Poco/Util/ServerApplication.h>
#include <thrift/server/TNonblockingServer.h>
#include <Poco/ThreadPool.h>
#include "KeyGenerate.h"
#include "ServerLog.h"
using Poco::Util::ServerApplication;
using namespace ::apache::thrift::server;
using namespace ::ChatProject;

class BackEndServer : public Poco::Runnable {
public:
    static BackEndServer* createBackEndServer();
    ~BackEndServer();
    void run();
    void stop();
private:
    BackEndServer();
    static BackEndServer* backEndServer;
    TNonblockingServer* server;
    //khi stop se goi stopAll de free cac thread trong pool
    Poco::ThreadPool *workPoolGroupEmotionItem, *workPoolEmotionItem, *workPoolRoomStatistic, *workPoolEmotionStatistic, *workPoolChatRoom, *workPoolGroupEmotion;
    vector<DataStructure*> database;
    vector<JobWorker*> workers;
    WriteToDBWorkers* writeToDBWorkers;
    static KeyGenerate* keyGen;
    Poco::NotificationQueue jobQueueGroupEmotionItem, jobQueueEmotionItem, jobQueueRoomStatistic, jobQueueEmotionStatistic, jobQueueChatRoom, jobQueueGroupEmotion;
    class ChatProjectHandler : public ChatProjectIf {
    public:
        ChatProjectHandler();
        void getAllChatRoom(std::vector<RoomItem> & _return);
        void getListUserInRoom(std::vector<UserItem> & _return, const std::string& roomId);
        void getAllEmotionGroup(std::vector<GroupEmotion> & _return);
        void getEmotionGroup(GroupEmotion& _return, const std::string& emotionGroupId);
        void addRoom(const RoomItem& newRoom);
        void editRoom(const RoomItem& room);
        void deleteRoom(const std::string& roomId);
        void addEmotion(const std::string& emotionItem);
        void editEmotion(const int64_t emotionId, const EmotionItem& emotionItem);
        void deleteEmotion(const int64_t emotionId);
        void kickUser(const std::string& userId);
        void getRoomStatisticByDate(RoomStatistic& _return, const int32_t date);
        void getEmotionStatisticByDate(EmotionStatistic& _return, const int32_t date);
        bool deleteAllEmotionInGroup(const std::string& groupEmotionId);
        bool deleteGroupEmotion(const std::string& groupEmotionId);
        void addGroupEmotion(std::string& _return, const std::string& groupEmotionName);
        void gelAllGroupEmotion(std::string& _return);
        void loadAllEmotion(std::string& _return);
        void getEmotionWithGroup(std::string& _return, const std::string& groupId);
        bool checkImageEmotionExist(const std::string& imageStyle, const std::string& groupEmotionId);
        bool sendMessageInternal(const MsgItem& msgItem, const bool messageOnline);
        void saveListMsgId(const std::vector<int64_t> & listMsgId);
        void saveListMsgItem(const std::vector<MsgItem> & listMsgItem);
        void saveOfflineMsgNotify(const std::string& userId);
        void getMessageList(std::vector<MsgItem> & _return, const std::string& userId1, const std::string& userId2, const int32_t totalMsg);
        void getMessageIdList(std::vector<int64_t> & _return, const std::string& userId1, const std::string& userId2, const int32_t totalMsg);
        void getMessageItem(MsgItem& _return, const int64_t MsgId);
        void saveRoomMsg(const MsgItem& msg, const std::string& roomId);
        void getRoomMsg(std::vector<MsgItem> & _return, const std::string& roomId);
        void getUserInRoom(std::vector<UserItem> & _return, const std::string& roomId);
        void getUserProfile(UserItem& _return, const std::string& userId);
        void getFriendList(std::vector<UserItem> & _return, const std::string& userId);
        int64_t getNewMsgId();
        void notifyUserOnline(const std::string& userId);
        void notifyListUserOffline(const std::vector<std::string> & listUserIdOffline);
        void checkFriendOnline(std::vector<bool> & _return, const std::vector<std::string> & listFriendId);
        void getFriendListZalo(std::string& _return, const std::string& userId, const std::string& oAuthCode);
        void getProfileZalo(std::string& _return, const std::string& userId, const std::string& oAuthCode);
        void getAllMessageIdList(std::vector<int64_t> & _return, const std::string& userId1, const std::string& userId2);
        void checkOfflineMessage(std::vector<std::string> & _return, const std::string& userId);
        void setLogin(const std::string& sessionId);
        bool isLogin(const std::string& sessionId);
        void setLogout(const std::string& sessionId);
        int64_t getImageId();
        void adminRegister(const std::string& userName, const std::string& password);
        bool checkAdmin(const std::string& userName, const std::string& password);
        void getAllAdmin(std::vector<std::string> & _return);
        /*
         * Not service function
         */
        bool writeToImageMngFile(int64_t);
        int64_t readFromImageMngFile();
        void saveListMsgId_NotService(const std::string& userId1, const std::string& userId2, const int64_t newMsgId);
    };
};

#endif	/* BACKENDSERVER_H */


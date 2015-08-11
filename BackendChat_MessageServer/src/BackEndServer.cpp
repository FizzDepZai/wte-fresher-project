/* 
 * File:   BackEndServer.cpp
 * Author: Thanhpv
 * Created on December 21, 2013, 3:14 PM
 */
#include "ini/BackEndServer.h"
//#include "ini/Database.h"
//#include "ini/Workers.h"
#include "ini/Node.h"
#include "ini/WorkNotification.h"
#include "ini/LinkListId.h"
#include "ini/Config.h"
#include <Poco/Util/PropertyFileConfiguration.h>
#include <thrift/concurrency/PosixThreadFactory.h>
#include <Poco/Thread.h>
#include <thrift/concurrency/ThreadManager.h>
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/server/TNonblockingServer.h>
#include <stdio.h>
#include <iostream>
#include "../src/ini/thrift/chatProject_types.h"
#include <boost/cstdint.hpp>
#include <boost/lexical_cast.hpp>
#include <memory.h>
#include "ini/EmotionItemIdGen.h"
#include "ini/jsoncpp/json/json.h"
#include <sstream>
#define MAX_GET 10
using Poco::AutoPtr;
using Poco::Util::PropertyFileConfiguration;
using Poco::Thread;
using boost::shared_ptr;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;
using namespace ::apache::thrift::server;
using namespace ::apache::thrift::concurrency;
using namespace std;
using namespace ChatProject;
BackEndServer* BackEndServer::backEndServer = NULL;

/*
 * create TNonBlockingServer
 */
BackEndServer::BackEndServer() {
    AutoPtr<PropertyFileConfiguration> pConf;
//    pConf = new PropertyFileConfiguration("properties/BackEndSerVerProperties.properties");
        pConf = new PropertyFileConfiguration("../../../properties/BackEndSerVerProperties.properties");
    int serverPort;
    try {
        serverPort = pConf->getInt("serverPort");
    } catch (Poco::NotFoundException e) {
        serverPort = pConf->getInt("serverMessagePort"); //default port
    }
    shared_ptr<BackEndServer::ChatProjectHandler> handler(new BackEndServer::ChatProjectHandler());
    shared_ptr<TProcessor> processor(new ChatProjectProcessor(handler));
    shared_ptr<TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());
    shared_ptr<ThreadManager> threadManager = ThreadManager::newSimpleThreadManager(15);
    shared_ptr<PosixThreadFactory> threadFactory = shared_ptr<PosixThreadFactory>(new PosixThreadFactory());
    threadManager->threadFactory(threadFactory);
    threadManager->start();
    server = new TNonblockingServer(processor, protocolFactory, serverPort, threadManager);
}

BackEndServer::~BackEndServer() {

}

/*
 * create jobQueue, Worker, writeToDBWorker, database
 */
void BackEndServer::run() {
    //log
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::ACTIVITY);
    logger.information("start BackEndServer");
    //read properties file
    AutoPtr<PropertyFileConfiguration> pConf;
//    pConf = new PropertyFileConfiguration("properties/BackEndSerVerProperties.properties");
        pConf = new PropertyFileConfiguration("../../../properties/BackEndSerVerProperties.properties");
    //create databaseNames array
    string databaseName[] = {"MessageId", "MessageItem", "MessageOffline"};
    //create database
    //    BackEndServer::database = Database::createInstance();
    //get the size of databaseName array
    int databaseNameSize = sizeof (databaseName) / sizeof (databaseName[0]);
    for (int i = 0; i < databaseNameSize; i++) {
        //        cout << i << " " << databaseName[i] << " " << databaseNameSize << endl;
        DataStructure *dataStructure = new DataStructure(databaseName[i]);
        database.push_back(dataStructure);
    }
    //create jobWorkers
    //    BackEndServer::workers = Workers::createInstance();
    workers.push_back(new JobWorker("MessageIdWorker", jobQueueMessageId, database[0]));
    workers.push_back(new JobWorker("MessageItemWorker", jobQueueMessageItem, database[1]));
    workers.push_back(new JobWorker("MessageOfflineWorker", jobQueueMessageOffline, database[2]));
    //load datafrom disk to cach
    logger.information("load data from hashDB to cachDB");
    for (int i = 0; i < databaseNameSize; i++) {
        string loading("Loading database from Disk to Memory....");
        loading.append(databaseName[i]);
        logger.information(loading);
        database[i]->readFromDisk();
        string cachCount("After load: ");
        string hashCount("After load: ");
        stringstream ss, ss2;
        ss << database[i]->getCachCount();
        cachCount.append(ss.str()).append("key-value in cach").append(databaseName[i]);
        ss2 << database[i]->getHashCount();
        hashCount.append(ss2.str()).append("key-value in hash").append(databaseName[i]);
        logger.information(cachCount);
        logger.information(hashCount);
    }

    logger.information("finish loading data from hashDB to cachDB");
    //create writeToDBWorker
    BackEndServer::writeToDBWorkers = WriteToDBWorkers::createInstance();
    for (int i = 0; i < databaseNameSize; i++) {
        writeToDBWorkers->addWriteToDBWorker(databaseName[i] + "WriteToDBWorker", database[i]);
    }
    //    create workpool
    int workPoolTotalThreads;
    try {
        workPoolTotalThreads = pConf->getInt("TotalThread");
    } catch (Poco::Exception e) {
        workPoolTotalThreads = pConf->getInt("workPoolTotalThread");
    }
    int perWorker = workPoolTotalThreads / workers.size();
    //create workpool
    BackEndServer::workPoolMessageId = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolMessageItem = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolMessageOffline = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    //start worker for each job
    for (int i = 0; i < perWorker; i++) {
        BackEndServer::workPoolMessageId->start(*workers[0]);
        BackEndServer::workPoolMessageItem->start(*workers[1]);
        BackEndServer::workPoolMessageOffline->start(*workers[2]);
    }
    //create threads to handle writeToDBWorker
    Poco::Thread MessageIdThread;
    MessageIdThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[0] + "WriteToDBWorker"));
    Poco::Thread MessageItemThread;
    MessageItemThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[1] + "WriteToDBWorker"));
    Poco::Thread MessageOfflineThread;
    MessageOfflineThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[2] + "WriteToDBWorker"));
    //    cout << "size of MessageId db " << database->getDataStructure("MessageId")->getCount() << endl;
    BackEndServer::server->serve();
    jobQueueMessageId.wakeUpAll();
    jobQueueMessageItem.wakeUpAll();
    jobQueueMessageOffline.wakeUpAll();

}

BackEndServer* BackEndServer::createBackEndServer() {
    if (!backEndServer) {
        backEndServer = new BackEndServer();
    }
    return backEndServer;
}

void BackEndServer::stop() {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::ACTIVITY);
    logger.information("----------------------------stop BackEndServer--------------------------------");
    //stop server
    //stop ko nhan request nua
    BackEndServer::server->stop();
    //wait until all jobqueue are empty
    while (!jobQueueMessageId.empty() || !jobQueueMessageItem.empty() || !jobQueueMessageOffline.empty()) {
    }
    //write cachDB to hashDB
    database[0]->writeToDisk();
    database[1]->writeToDisk();
    database[2]->writeToDisk();
    //free workers
    //free threadpool
    workPoolMessageId->stopAll();
    workPoolMessageItem->stopAll();
    workPoolMessageOffline->stopAll();
    //free jobqueue
}

BackEndServer::ChatProjectHandler::ChatProjectHandler() {
    // Your initialization goes here
}

void BackEndServer::ChatProjectHandler::getAllChatRoom(std::vector<RoomItem> & _return) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getAllChatRoom");
}

void BackEndServer::ChatProjectHandler::getListUserInRoom(std::vector<UserItem> & _return, const std::string& roomId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getListUserInRoom");
}

void BackEndServer::ChatProjectHandler::getAllEmotionGroup(std::vector<GroupEmotion> & _return) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getAllEmotionGroup");
}

void BackEndServer::ChatProjectHandler::getEmotionGroup(GroupEmotion& _return, const std::string& emotionGroupId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getEmotionGroup");
}

void BackEndServer::ChatProjectHandler::addRoom(const RoomItem& newRoom) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("addRoom");
}

void BackEndServer::ChatProjectHandler::editRoom(const RoomItem& room) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("editRoom");
}

void BackEndServer::ChatProjectHandler::deleteRoom(const std::string& roomId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("deleteRoom");
}

void BackEndServer::ChatProjectHandler::addEmotion(const std::string& emotionItem) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("addEmotion");
}

void BackEndServer::ChatProjectHandler::editEmotion(const int64_t emotionId, const EmotionItem& emotionItem) {

}

void BackEndServer::ChatProjectHandler::deleteEmotion(const int64_t emotionId) {
}

void BackEndServer::ChatProjectHandler::kickUser(const std::string& userId) {
    // Your implementation goes here
}

void BackEndServer::ChatProjectHandler::getRoomStatisticByDate(RoomStatistic& _return, const int32_t date) {
    // Your implementation goes here
}

void BackEndServer::ChatProjectHandler::getEmotionStatisticByDate(EmotionStatistic& _return, const int32_t date) {
    // Your implementation goes here
}

bool BackEndServer::ChatProjectHandler::deleteAllEmotionInGroup(const std::string& groupEmotionId) {
}

bool BackEndServer::ChatProjectHandler::deleteGroupEmotion(const std::string& groupEmotionId) {
}

void BackEndServer::ChatProjectHandler::addGroupEmotion(std::string& _return, const std::string& groupEmotionName) {
}

void BackEndServer::ChatProjectHandler::gelAllGroupEmotion(std::string& _return) {
}

void BackEndServer::ChatProjectHandler::getEmotionWithGroup(std::string& _return, const std::string& groupId) {
}

bool BackEndServer::ChatProjectHandler::checkImageEmotionExist(const std::string& imageStyle, const std::string& groupEmotionId) {
}

bool BackEndServer::ChatProjectHandler::sendMessageInternal(const MsgItem& msgItem, const bool messageOnline) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("sendMessageInternal");
    MsgId* msgId;
    //    MsgItem *tmpMsgItem;
    string one;
    string kBuffer;
    string vBuffer;
    msgId = new MsgId();
    //    tmpMsgItem = new MsgItem();
    //serialize key
    one = boost::lexical_cast<std::string>(msgItem.msgId);
    msgId->__set_msgId(one);
    msgId->writeSerialize(kBuffer);
    //        serialize value
    //    tmpMsgItem->msgId = msgItem.msgId;
    //    tmpMsgItem->sendingUserId = msgItem.sendingUserId;
    //    tmpMsgItem->receiveUserId = msgItem.receiveUserId;
    //    tmpMsgItem->content = msgItem.content;
    //
    //    tmpMsgItem->time = msgItem.time;
    //    tmpMsgItem->msgType = msgItem.msgType;
    //    tmpMsgItem->writeSerialize(vBuffer);
    msgItem.writeSerialize(vBuffer);
    char* tmpKBuffer = (char*) malloc(kBuffer.size());
    memcpy(tmpKBuffer, kBuffer.data(), kBuffer.size());
    char* tmpVBuffer = (char*) malloc(vBuffer.size());
    memcpy(tmpVBuffer, vBuffer.data(), vBuffer.size());
    //enqueue job add to MessageItem DB  
    WorkNotification::Ptr saveListMsgItemWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveListMsgItem", tmpKBuffer, kBuffer.size(),
            tmpVBuffer, vBuffer.size());
    backEndServer->jobQueueMessageItem.enqueueNotification(saveListMsgItemWorkNfc);
    //    delete tmpMsgItem;
    delete msgId;
    //enqueue job add to MessageId DB
    //    vector<int64_t> newMsgItemList;
    //    newMsgItemList.push_back(msgItem.msgId);
    saveListMsgId_NotService(msgItem.receiveUserId, msgItem.sendingUserId, msgItem.msgId);


    /*
     *Message offline
     */
    if (!messageOnline) {
        //send key and value to JobWorker to add to DB
        string tmpOffMsgSend = msgItem.sendingUserId;
        string tmpOffMsgReceive = msgItem.receiveUserId;
        UserId* sendId = new UserId();
        sendId->__set_userId(tmpOffMsgSend);
        UserId* receiveId = new UserId();
        receiveId->__set_userId(tmpOffMsgReceive);
        string saveOffMsgSend, saveOffMsgReceive;
        sendId->writeSerialize(saveOffMsgSend);
        receiveId->writeSerialize(saveOffMsgReceive);
        char* saveOffMsgSendBuffer = (char*) malloc(saveOffMsgSend.size());
        memcpy(saveOffMsgSendBuffer, saveOffMsgSend.data(), saveOffMsgSend.size());
        char* saveOffMsgReceiveBuffer = (char*) malloc(saveOffMsgReceive.size());
        memcpy(saveOffMsgReceiveBuffer, saveOffMsgReceive.data(), saveOffMsgReceive.size());
        /*
         *Test
         */
        //        string xxx(saveOffMsgSendBuffer, saveOffMsgSend.size());
        //        string yyy(saveOffMsgReceiveBuffer, saveOffMsgReceive.size());
        //        UserId* XXX = new UserId();
        //        XXX->readSerialize(xxx);
        //        UserId* YYY = new UserId();
        //        YYY->readSerialize(yyy);
        //        cout << "XXX UserId is: " << XXX->userId << " YYY UserId is: " << YYY->userId << endl;
        WorkNotification::Ptr saveListMsgOffWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveListOffMessage", saveOffMsgReceiveBuffer, saveOffMsgReceive.size(),
                saveOffMsgSendBuffer, saveOffMsgSend.size());
        backEndServer->jobQueueMessageOffline.enqueueNotification(saveListMsgOffWorkNfc);

    }
}

void BackEndServer::ChatProjectHandler::saveOfflineMsgNotify(const std::string& userId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("saveOfflineMsgNotify");
}

void BackEndServer::ChatProjectHandler::saveRoomMsg(const MsgItem& msg, const std::string& roomId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("saveRoomMsg");
}

void BackEndServer::ChatProjectHandler::getRoomMsg(std::vector<MsgItem> & _return, const std::string& roomId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getRoomMsg");
}

void BackEndServer::ChatProjectHandler::getUserInRoom(std::vector<UserItem> & _return, const std::string& roomId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getUserInRoom");
}

void BackEndServer::ChatProjectHandler::getUserProfile(UserItem& _return, const std::string& userId) {
    // Your implementation goes here
    printf("getUserProfile\n");
}

void BackEndServer::ChatProjectHandler::getFriendList(std::vector<UserItem> & _return, const std::string& userId) {
    // Your implementation goes here
    printf("getFriendList\n");
}

void BackEndServer::ChatProjectHandler::saveListMsgId(const std::vector<int64_t> & listMsgId) {

}

/*
 * enqueue for process later
 */
void BackEndServer::ChatProjectHandler::saveListMsgId_NotService(const std::string& userId1, const std::string& userId2, const int64_t newMsgId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("saveListMsgId_NotService");
    string under = "_";
    string user1_user2 = "";
    user1_user2 = userId1.compare(userId2) < 0 ? ((user1_user2.append(userId1)).append(under)).append(userId2) : ((user1_user2.append(userId2)).append(under)).append(userId1);
    //serialize key
    MsgId* msgId = new MsgId();
    msgId->__set_msgId(user1_user2);
    string kBuffer;
    msgId->writeSerialize(kBuffer);
    char* tmpKBuffer = (char*) malloc(kBuffer.size());
    memcpy(tmpKBuffer, kBuffer.data(), kBuffer.size());
    //serialize value
    //    MsgIdList* msgIdList = new MsgIdList();
    //    msgIdList->__set_msgIdList(listMsgId);
    //    string vBuffer;
    //    msgIdList->writeSerialize(vBuffer);
    //    char* tmpVBuffer = (char*) malloc(vBuffer.size());
    //    memcpy(tmpVBuffer, vBuffer.data(), vBuffer.size());
    //enqueue job
    /*
     * test 
     */
    //    string valBuffer(vBuffer.data(), vBuffer.size());
    //    MsgIdList* pushList = new MsgIdList();
    //    pushList->readSerialize(valBuffer);
    WorkNotification::Ptr saveListMsgIdWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveListMsgId", tmpKBuffer, kBuffer.size(),
            newMsgId, sizeof (newMsgId));
    backEndServer->jobQueueMessageId.enqueueNotification(saveListMsgIdWorkNfc);
}

void BackEndServer::ChatProjectHandler::saveListMsgItem(const std::vector<MsgItem> & listMsgItem) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("saveListMsgItem");
    MsgId* msgId;
    MsgItem *msgItem;
    string one;
    string kBuffer;
    string vBuffer;
    for (int i = 0; i < listMsgItem.size(); i++) {
        msgId = new MsgId();
        msgItem = new MsgItem();
        //serialize key
        one = boost::lexical_cast<std::string>(listMsgItem[i].msgId);
        msgId->__set_msgId(one);
        msgId->writeSerialize(kBuffer);
        //        serialize value
        msgItem->msgId = listMsgItem[i].msgId;
        msgItem->sendingUserId = listMsgItem[i].sendingUserId;
        msgItem->receiveUserId = listMsgItem[i].receiveUserId;
        msgItem->content = listMsgItem[i].content;
        msgItem->time = listMsgItem[i].time;
        msgItem->writeSerialize(vBuffer);
        char* tmpKBuffer = (char*) malloc(kBuffer.size());
        memcpy(tmpKBuffer, kBuffer.data(), kBuffer.size());
        char* tmpVBuffer = (char*) malloc(vBuffer.size());
        memcpy(tmpVBuffer, vBuffer.data(), vBuffer.size());
        //enqueue job
        WorkNotification::Ptr saveListMsgItemWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveListMsgItem", tmpKBuffer, kBuffer.size(),
                tmpVBuffer, vBuffer.size());
        backEndServer->jobQueueMessageItem.enqueueNotification(saveListMsgItemWorkNfc);
    }
    delete msgItem;
    delete msgId;
}

/*
 * Return immediately
 */
int64_t BackEndServer::ChatProjectHandler::getNewMsgId() {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getNewMsgId");
    int theLastIs = KeyGenerate::createInstance(backEndServer->database[1]->getHashCount())->getKey();
    return theLastIs;
}

void BackEndServer::ChatProjectHandler::getMessageList(std::vector<MsgItem> & _return, const std::string& userId1, const std::string& userId2, const int32_t totalMsg) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getMessageList");
    // Your implementation goes here
    //get msgIdList from MessageId db
    vector<int64_t> msgIdList;
    BackEndServer::ChatProjectHandler::getMessageIdList(msgIdList, userId1, userId2, totalMsg);
    //traverse msgIdList and MessageItem to get the message and append to list
    //    cout << "so luong cua msgItem: " << messageItem->getHashCount() << endl;

    string kBuffer;
    string one;
    if (msgIdList.size() != 0)
        for (int i = 0; i <= msgIdList.size() - 1; i++) {
            MsgId* msgId = new MsgId();
            one = boost::lexical_cast<std::string>(msgIdList[i]);

            msgId->__set_msgId(one);
            msgId->writeSerialize(kBuffer);
            //        
            uint32_t vsizeFromDB = 0;
            char* tmpKBuffer = (char*) malloc(kBuffer.size());
            memcpy(tmpKBuffer, kBuffer.data(), kBuffer.size());
            char *vFromDB = backEndServer->database[1]->getValueFromKey(tmpKBuffer, kBuffer.size(), 10000, vsizeFromDB);
            if (vsizeFromDB != 0) {
                string vBuffer(vFromDB, vsizeFromDB);
                MsgItem* msgItemReturn = new MsgItem();
                msgItemReturn->readSerialize(vBuffer);
                //                cout << "msgId is: " << msgItemReturn->msgId << endl;
                _return.push_back(*msgItemReturn);
                delete msgItemReturn;
                msgItemReturn = NULL;
            }
            delete msgId;
            msgId = NULL;
        }
    //
}

void BackEndServer::ChatProjectHandler::getMessageIdList(std::vector<int64_t> & _return, const std::string& userId1, const std::string& userId2, const int32_t totalMsg) {
    // Your implementation goes here
    //    cout << "getMessageIdList " << userId1 << " " << userId2 << endl;
        Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
        logger.information("getMessageIdList");
        string under = "_";
        string user1_user2 = "";
        user1_user2 = userId1.compare(userId2) < 0 ? ((user1_user2.append(userId1)).append(under)).append(userId2) : ((user1_user2.append(userId2)).append(under)).append(userId1);
        //serialize key
        MsgId* msgId = new MsgId();
        msgId->__set_msgId(user1_user2);
        string kBuffer;
        msgId->writeSerialize(kBuffer);
        //go to database to get it
        uint32_t vsizeFromDB = 0;
        DataStructure* tmpDBStruct = backEndServer->database[0];
        char* vFromDB = tmpDBStruct->getValueFromKey(kBuffer.data(), kBuffer.size(), 100000, vsizeFromDB);
        //serialize value
        if (vsizeFromDB != 0) {
            string vBuffer(vFromDB, vsizeFromDB);
            MsgIdList* msgIdListReturn = new MsgIdList();
            //    cout << vBuffer.size() << endl;
            msgIdListReturn->readSerialize(vBuffer);
            // sort list
            std::sort(msgIdListReturn->msgIdList.begin(), msgIdListReturn->msgIdList.begin() + msgIdListReturn->msgIdList.size()-1);
            //        cout << "msgIdListReturn->msgIdList.size() " << msgIdListReturn->msgIdList.size() << endl;
            int returnMsgIdListEnd = msgIdListReturn->msgIdList.size() - totalMsg;
            int returnMsgIdListBegin = returnMsgIdListEnd - MAX_GET > 0 ? returnMsgIdListEnd - MAX_GET : 0;
            returnMsgIdListEnd = returnMsgIdListEnd >= 0 ? returnMsgIdListEnd : msgIdListReturn->msgIdList.size();
            for (int i = returnMsgIdListBegin; i < returnMsgIdListEnd; i++) {
                _return.push_back(msgIdListReturn->msgIdList[i]);
                //            cout<<"---------------------------------"<<msgIdListReturn->msgIdList[i]<<endl;
            }
            //        cout << "--------------number of msgIdList in backend is: " << msgIdListReturn->msgIdList.capacity() << endl;
        }
}

void BackEndServer::ChatProjectHandler::getAllMessageIdList(std::vector<int64_t> & _return, const std::string& userId1, const std::string& userId2) {
    // Your implementation goes here
    //    cout << "getMessageIdList " << userId1 << " " << userId2 << endl;
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getAllMessageIdList");
    string under = "_";
    string user1_user2 = "";
    user1_user2 = userId1.compare(userId2) < 0 ? ((user1_user2.append(userId1)).append(under)).append(userId2) : ((user1_user2.append(userId2)).append(under)).append(userId1);
    //serialize key
    MsgId* msgId = new MsgId();
    msgId->__set_msgId(user1_user2);
    string kBuffer;
    msgId->writeSerialize(kBuffer);
    //go to database to get it
    uint32_t vsizeFromDB = 0;
    DataStructure* tmpDBStruct = backEndServer->database[0];
    char* vFromDB = tmpDBStruct->getValueFromKey(kBuffer.data(), kBuffer.size(), 100000, vsizeFromDB);
    //serialize value
    if (vsizeFromDB != 0) {
        string vBuffer(vFromDB, vsizeFromDB);
        MsgIdList* msgIdListReturn = new MsgIdList();
        //    cout << vBuffer.size() << endl;
        msgIdListReturn->readSerialize(vBuffer);
        //        cout << "msgIdListReturn->msgIdList.size() " << msgIdListReturn->msgIdList.size() << endl;
        _return = msgIdListReturn->msgIdList;
        std::sort(_return.begin(), _return.begin() + _return.size() - 1);
        cout << "--------------number of msgIdList in backend is: " << _return.size() << endl;
    }
}

void BackEndServer::ChatProjectHandler::getMessageItem(MsgItem& _return, const int64_t pMsgId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getMessageItem");
    string one;
    string kBuffer;
    MsgId* tmpMsgId = new MsgId();
    one = boost::lexical_cast<std::string>(pMsgId);
    tmpMsgId->__set_msgId(one);
    tmpMsgId->writeSerialize(kBuffer);

    uint32_t vsizeFromDB = 0;
    char *vFromDB = backEndServer->database[1]->getValueFromKey(kBuffer.data(), kBuffer.size(), 10000, vsizeFromDB);
    if (vsizeFromDB != 0) {
        string vBuffer(vFromDB, vsizeFromDB);
        MsgItem* msgItemReturn = new MsgItem();
        msgItemReturn->readSerialize(vBuffer);
        //        cout << "msgId is: " << msgItemReturn->msgId << endl;
        _return = *msgItemReturn;
        //        cout << "----------------------" << _return.content << _return.receiveUserId<<_return.sendingUserId<<_return.time<<_return.msgId<<_return.msgType << endl;
        delete msgItemReturn;
        msgItemReturn = NULL;
    }
}


void BackEndServer::ChatProjectHandler::notifyUserOnline(const std::string & userId) {

}

void BackEndServer::ChatProjectHandler::notifyListUserOffline(const std::vector<std::string> & listUserIdOffline) {

}

void BackEndServer::ChatProjectHandler::checkFriendOnline(std::vector<bool> & _return, const std::vector<std::string> & listFriendId) {

}

void BackEndServer::ChatProjectHandler::getFriendListZalo(std::string& _return, const std::string& userId, const std::string & oAuthCode) {

}

void BackEndServer::ChatProjectHandler::getProfileZalo(std::string& _return, const std::string& userId, const std::string & oAuthCode) {

}

void BackEndServer::ChatProjectHandler::loadAllEmotion(std::string & _return) {
}

void BackEndServer::ChatProjectHandler::checkOfflineMessage(std::vector<std::string> & _return, const std::string& userId) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("checkOfflineMessage");
    //    cout<<"checkOfflineMessage\n"<<endl;
    UserId* kUserId = new UserId();
    kUserId->__set_userId(userId);

    string tmpKey;
    kUserId->writeSerialize(tmpKey);
    char* key = (char*) malloc(tmpKey.size());
    memcpy(key, tmpKey.data(), tmpKey.size());
    uint32_t vsizeGet = 0;
    char* valueGet = backEndServer->database[2]->getValueFromKey(key, tmpKey.size(), 100000, vsizeGet);
    string tmpValue(valueGet, vsizeGet);
    ListOfflineMsg* returnListMsgOff = new ListOfflineMsg();
    returnListMsgOff->readSerialize(tmpValue);
    _return = returnListMsgOff->listOfflineMsg;
    //delete that list
    WorkNotification::Ptr delMsgOffWorkNfc = new WorkNotification(WorkNotification::DELETE_ITEM, "delMsgOffItem", key, tmpKey.size(),
            NULL, 0);
    backEndServer->jobQueueMessageOffline.enqueueNotification(delMsgOffWorkNfc);
}

void BackEndServer::ChatProjectHandler::setLogin(const std::string& sessionId) {
}

bool BackEndServer::ChatProjectHandler::isLogin(const std::string& sessionId) {
}

void BackEndServer::ChatProjectHandler::setLogout(const std::string& sessionId) {
}

int64_t BackEndServer::ChatProjectHandler::getImageId() {
}

bool BackEndServer::ChatProjectHandler::writeToImageMngFile(int64_t value) {
}

int64_t BackEndServer::ChatProjectHandler::readFromImageMngFile() {
}

void BackEndServer::ChatProjectHandler::adminRegister(const std::string& userName, const std::string& password) {
}

bool BackEndServer::ChatProjectHandler::checkAdmin(const std::string& userName, const std::string& password) {
}

void BackEndServer::ChatProjectHandler::getAllAdmin(std::vector<std::string> & _return) {
}
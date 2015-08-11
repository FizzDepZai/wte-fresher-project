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
#include "ini/GroupEmotionItemIdGen.h"
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
//    pConf = new PropertyFileConfiguration("properties/BackEndSerVerProperties.properties"); //run tren IDE
        pConf = new PropertyFileConfiguration("../../../properties/BackEndSerVerProperties.properties"); //run tren terminal
    int serverPort;
    try {
        serverPort = pConf->getInt("serverPort");
    } catch (Poco::NotFoundException e) {
        serverPort = pConf->getInt("serverAdminPort"); //default port
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
    //write log
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::ACTIVITY);
    logger.information("start BackEndServer");
    //read properties file
    AutoPtr<PropertyFileConfiguration> pConf;
//    pConf = new PropertyFileConfiguration("properties/BackEndSerVerProperties.properties"); //run tren IDE
        pConf = new PropertyFileConfiguration("../../../properties/BackEndSerVerProperties.properties"); //run tren terminal
    //create databaseNames array
    string databaseName[] = {"GroupEmotionItem", "EmotionItem", "RoomStatistic", "EmotionStatistic", "ChatRoom", "GroupEmotion", "AdminList"};
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
    workers.push_back(new JobWorker("GroupEmotionItemWorker", jobQueueGroupEmotionItem, database[0]));
    workers.push_back(new JobWorker("EmotionItemWorker", jobQueueEmotionItem, database[1]));
    workers.push_back(new JobWorker("RoomStatisticWorker", jobQueueRoomStatistic, database[2]));
    workers.push_back(new JobWorker("EmotionStatisticWorker", jobQueueEmotionStatistic, database[3]));
    workers.push_back(new JobWorker("ChatRoomWorker", jobQueueChatRoom, database[4]));
    workers.push_back(new JobWorker("GroupEmotionWorker", jobQueueGroupEmotion, database[5]));
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
    // create workpool
    int workPoolTotalThreads;
    try {
        workPoolTotalThreads = pConf->getInt("TotalThread");
    } catch (Poco::Exception e) {
        workPoolTotalThreads = pConf->getInt("workPoolTotalThread");
    }
    int perWorker = workPoolTotalThreads / workers.size();
    //create workpool
    BackEndServer::workPoolGroupEmotionItem = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolEmotionItem = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolEmotionStatistic = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolGroupEmotion = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolChatRoom = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    BackEndServer::workPoolRoomStatistic = new Poco::ThreadPool(perWorker, perWorker, 60, 0);
    //start worker for each job
    for (int i = 0; i < perWorker; i++) {
        BackEndServer::workPoolChatRoom->start(*workers[0]);
        BackEndServer::workPoolEmotionItem->start(*workers[1]);
        BackEndServer::workPoolEmotionStatistic->start(*workers[2]);
        BackEndServer::workPoolGroupEmotion->start(*workers[3]);
        BackEndServer::workPoolGroupEmotionItem->start(*workers[4]);
        BackEndServer::workPoolRoomStatistic->start(*workers[5]);
    }
    //create threads to handle writeToDBWorker
    Poco::Thread GroupEmotionItemThread;
    GroupEmotionItemThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[0] + "WriteToDBWorker"));
    Poco::Thread EmotionItemThread;
    EmotionItemThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[1] + "WriteToDBWorker"));
    Poco::Thread RoomStatisticThread;
    RoomStatisticThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[2] + "WriteToDBWorker"));
    Poco::Thread EmotionStatisticThread;
    EmotionStatisticThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[3] + "WriteToDBWorker"));
    Poco::Thread ChatRoomThread;
    ChatRoomThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[4] + "WriteToDBWorker"));
    Poco::Thread GroupEmotionThread;
    GroupEmotionThread.start(*writeToDBWorkers->getWriteToDBWorker(databaseName[5] + "WriteToDBWorker"));
    //    cout << "size of MessageId db " << database->getDataStructure("MessageId")->getCount() << endl;


    BackEndServer::server->serve();
    jobQueueGroupEmotionItem.wakeUpAll();
    jobQueueEmotionItem.wakeUpAll();
    jobQueueRoomStatistic.wakeUpAll();
    jobQueueEmotionStatistic.wakeUpAll();
    jobQueueChatRoom.wakeUpAll();
    jobQueueGroupEmotion.wakeUpAll();
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
    while (!jobQueueGroupEmotion.empty() || !jobQueueChatRoom.empty() || !jobQueueEmotionItem.empty() || !jobQueueEmotionStatistic.empty()
            || !jobQueueGroupEmotionItem.empty() || !jobQueueRoomStatistic.empty()) {
    }
    //write cachDB to hashDB
    database[0]->writeToDisk();
    database[1]->writeToDisk();
    database[2]->writeToDisk();
    database[3]->writeToDisk();
    database[4]->writeToDisk();
    database[5]->writeToDisk();
    //free workers
    //free threadpool
    workPoolGroupEmotionItem->stopAll();
    workPoolChatRoom->stopAll();
    workPoolEmotionItem->stopAll();
    workPoolEmotionStatistic->stopAll();
    workPoolGroupEmotion->stopAll();
    workPoolRoomStatistic->stopAll();
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
    /*
     *additional implementation
     */
    string start = "{\"JSONArray\":";
    string tmpEmotionItem;
    tmpEmotionItem.append(emotionItem);
    start.append(tmpEmotionItem.append("}"));
    Json::Value jsonValueArray;
    Json::Reader jsonReaderArray;
    //    cout << start << endl;
    bool parseSuccess = jsonReaderArray.parse(start, jsonValueArray, false);
    Json::Value value = jsonValueArray["JSONArray"];
    cout << value.size() << endl;
    for (Json::UInt x = 0; x < value.size(); x++) {
        EmotionItemIdGen* emotionIdGen = EmotionItemIdGen::createInstance(backEndServer->database[1]->getHashCount());
        int64_t keyGen = emotionIdGen->getKey();
        //replace the key with alt
        string kBuffer, vBuffer;
        //add listEmotionId to GroupEmotion DB
        //json de lay groupId va modify emotion id
        //        bool parsedSuccess = jsonReader.parse(value[x].asString(), jsonValue, false);
        if (value[x]["style"].asString().compare("") != 0) {
            int groupId = value[x]["group"].asInt();
            std::ostringstream sin, sin2;
            sin << groupId;
            sin2 << (keyGen - 1);
            value[x]["alt"] = sin2.str();
            GroupId* pGroupId = new GroupId();
            pGroupId->__set_groupId(sin.str());
            pGroupId->writeSerialize(kBuffer);
            char* tmpKBuffer1 = (char*) malloc(kBuffer.size());
            memcpy(tmpKBuffer1, kBuffer.data(), kBuffer.size());
            vector<int64_t> needAddingEmotion;
            needAddingEmotion.push_back(keyGen);
            ListEmotionId* needAddingListEmotionIdPtr = new ListEmotionId();
            needAddingListEmotionIdPtr->__set_listEmotionId(needAddingEmotion);
            needAddingListEmotionIdPtr->writeSerialize(vBuffer);
            char* tmpVBuffer = (char*) malloc(vBuffer.size());
            memcpy(tmpVBuffer, vBuffer.data(), vBuffer.size());
            //
            string valBuffer(tmpVBuffer, vBuffer.size());
            ListEmotionId* pushList = new ListEmotionId();
            pushList->readSerialize(valBuffer);
            //
            WorkNotification::Ptr saveGroupEmotionWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveGroupEmotion", tmpKBuffer1,
                    kBuffer.size(), tmpVBuffer, vBuffer.size());
            backEndServer->jobQueueGroupEmotion.enqueueNotification(saveGroupEmotionWorkNfc);
            //add all Emotion to EmotionItem DB
            EmotionId* tmpEmotionId = new EmotionId();
            tmpEmotionId->__set_emotionId(keyGen);
            string EItemKBuffer, EItemVBuffer;
            tmpEmotionId->writeSerialize(EItemKBuffer);
            char* tmpKBuffer5 = (char*) malloc(EItemKBuffer.size());
            memcpy(tmpKBuffer5, EItemKBuffer.data(), EItemKBuffer.size());
            EItemVBuffer = value[x].toStyledString(); //assign new JSON string to value
            char* tmpVBuffer2 = (char*) malloc(EItemVBuffer.size());
            memcpy(tmpVBuffer2, EItemVBuffer.data(), EItemVBuffer.size());
            WorkNotification::Ptr saveEmotionItemWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveEmotionItem", tmpKBuffer5, EItemKBuffer.size(),
                    tmpVBuffer2, EItemVBuffer.size());
            backEndServer->jobQueueEmotionItem.enqueueNotification(saveEmotionItemWorkNfc);
        }

    }
    /*
     * Test
     */
    //        cout<<emotionItem<<endl;
    //        Json::Value jsonValue;
    //        Json::Reader jsonReader;
    //        Json::UInt x = 0;
    //        jsonReader.parse(emotionItem, jsonValue, false);
    //        Json::Value value = jsonValue["JSONArray"];
    //        cout<<value.size()<<endl;
    //        const Json::Value tmp = jsonValue;
    //        cout<<value.get(x, tmp)<<endl;
    /*
     *
     */

}

void BackEndServer::ChatProjectHandler::editEmotion(const int64_t emotionId, const EmotionItem& emotionItem) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("editEmotion");
    //serialize key
    EmotionId* kEmotionId = new EmotionId();
    kEmotionId->__set_emotionId(emotionId);
    string kBuffer;
    kEmotionId->writeSerialize(kBuffer);
    char* tmpkBuffer = (char*) malloc(kBuffer.size());
    memcpy(tmpkBuffer, kBuffer.data(), kBuffer.size());

    //serialize value
    string vBuffer;
    std::ostringstream sin, sin2;
    sin << emotionItem.groupId;
    sin2 << emotionItem.emotionId;
    vBuffer.append("{\"keyInput\": \"").append(emotionItem.keyInput).append("\", ").append("\"style\": ").append("\"").append(emotionItem.style).append("\", ").append("\"src\":").append("\"").append(emotionItem.src).append("\", ").
            append("\"title\":").append("\"").append(emotionItem.title).append("\", ").append("\"alt\": ").append("\"").append(sin2.str()).append("\", ").append("\"groupId\": ").append(sin.str()).append("}");
    char* tmpvBuffer = (char*) malloc(vBuffer.size());
    memcpy(tmpvBuffer, vBuffer.data(), vBuffer.size());
    //add to jobqueue
    WorkNotification::Ptr saveEmotionItemWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveEmotionItem", tmpkBuffer, kBuffer.size(),
            tmpvBuffer, vBuffer.size());
    backEndServer->jobQueueEmotionItem.enqueueNotification(saveEmotionItemWorkNfc);
}

void BackEndServer::ChatProjectHandler::deleteEmotion(const int64_t emotionId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("deleteEmotion");
    DataStructure* EmotionItemDB = backEndServer->database[1];
    EmotionId* delEmotionId = new EmotionId();
    delEmotionId->__set_emotionId(emotionId);
    string kDelEmotionId;
    delEmotionId->writeSerialize(kDelEmotionId);
    char* delChr = (char*) malloc(kDelEmotionId.size());
    memcpy(delChr, kDelEmotionId.data(), kDelEmotionId.size());
    uint32_t DelEmotionInDB = 0;
    char* delEmotionItemStr = EmotionItemDB->getValueFromKey(delChr, kDelEmotionId.size(), 10000, DelEmotionInDB);
    if (DelEmotionInDB != 0) {
        //delete in Emotion Item in EmotionItem DB
        WorkNotification::Ptr delEmotionItemWorkNfc = new WorkNotification(WorkNotification::DELETE_ITEM, "deleteEmotionItem", delChr, kDelEmotionId.size(),
                delEmotionItemStr, DelEmotionInDB);
        backEndServer->jobQueueEmotionItem.enqueueNotification(delEmotionItemWorkNfc);
        //delete in emotionIdList in GroupEmotionItem DB
        string jsonStr(delEmotionItemStr, DelEmotionInDB);
        Json::Value jsonValueArray;
        Json::Reader jsonReaderArray;
        bool parseSuccess = jsonReaderArray.parse(jsonStr, jsonValueArray, false);
        string groupIdStr = jsonValueArray["group"].asString();
        GroupId* groupId = new GroupId();
        groupId->__set_groupId(groupIdStr);
        string groupIdKBuffer;
        char* listEmotionIdVBuffer;
        uint32_t vListEmotionIdDB = 0;
        groupId->writeSerialize(groupIdKBuffer);

        char* groupIdChr = (char*) malloc(groupIdKBuffer.size());
        memcpy(groupIdChr, groupIdKBuffer.data(), groupIdKBuffer.size());

        DataStructure* groupEmotionDB = backEndServer->database[5];
        listEmotionIdVBuffer = groupEmotionDB->getValueFromKey(groupIdChr, groupIdKBuffer.size(), 100000, vListEmotionIdDB);
        ListEmotionId* listEmotionId = new ListEmotionId();
        string listEmotionIdStr(listEmotionIdVBuffer, vListEmotionIdDB);
        listEmotionId->readSerialize(listEmotionIdStr);
        vector<int64_t> vectorEmotionId = listEmotionId->listEmotionId;
        int index = 0;
        for (int i = 0; i < vectorEmotionId.size(); i++) {
            if (vectorEmotionId[i] == emotionId) {
                index = i;
                break;
            }
        }
        vectorEmotionId.erase(vectorEmotionId.begin() + index);
        string listEmotionIdBufferSer;
        listEmotionId->__set_listEmotionId(vectorEmotionId);
        listEmotionId->writeSerialize(listEmotionIdBufferSer);

        char* listEmotionIdBufferSerChr = (char*) malloc(listEmotionIdBufferSer.size());
        memcpy(listEmotionIdBufferSerChr, listEmotionIdBufferSer.data(), listEmotionIdBufferSer.size());
        WorkNotification::Ptr saveGroupEmotionItemWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "saveGroupEmotion", groupIdChr, groupIdKBuffer.size(),
                listEmotionIdBufferSerChr, listEmotionIdBufferSer.size());
        backEndServer->jobQueueGroupEmotion.enqueueNotification(saveGroupEmotionItemWorkNfc);
    }
}

bool BackEndServer::ChatProjectHandler::deleteAllEmotionInGroup(const std::string& groupEmotionId) {
    //del all emotion in emotion Item list
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("deleteAllEmotionInGroup");
    GroupId* groupId = new GroupId();
    groupId->__set_groupId(groupEmotionId);
    string groupIdKBuffer;
    char* listEmotionIdVBuffer;
    uint32_t vListEmotionIdDB = 0;
    groupId->writeSerialize(groupIdKBuffer);
    DataStructure* groupEmotionDB = backEndServer->database[5];
    listEmotionIdVBuffer = groupEmotionDB->getValueFromKey(groupIdKBuffer.data(), groupIdKBuffer.size(), 10000, vListEmotionIdDB);
    ListEmotionId* curListEmotionId = new ListEmotionId();
    string listEmotionIdStr(listEmotionIdVBuffer, vListEmotionIdDB);
    curListEmotionId->readSerialize(listEmotionIdStr);
    //delete the emotion
    for (int i = 0; i < curListEmotionId->listEmotionId.size(); i++) {
        //serialize each emotion id to delete
        //        stringstream ss;
        //        ss << curListEmotionId->listEmotionId[i];
        string emotionIdStr;
        EmotionId* kEmotion = new EmotionId();
        kEmotion->__set_emotionId(curListEmotionId->listEmotionId[i]);
        kEmotion->writeSerialize(emotionIdStr);
        char* key = (char*) malloc(emotionIdStr.size());
        memcpy(key, emotionIdStr.data(), emotionIdStr.size());
        WorkNotification::Ptr delEmotionItemWorkNfc = new WorkNotification(WorkNotification::DELETE_ITEM, "deleteEmotionItem", key, emotionIdStr.size(),
                "", 0);
        backEndServer->jobQueueEmotionItem.enqueueNotification(delEmotionItemWorkNfc);
    }
    //del key_value in Group Emotion
    char* keyGroup = (char*) malloc(groupIdKBuffer.size());
    memcpy(keyGroup, groupIdKBuffer.data(), groupIdKBuffer.size());
    WorkNotification::Ptr delGroupEmotionWorkNfc = new WorkNotification(WorkNotification::DELETE_ITEM, "deleteGroupEmotion", keyGroup, groupIdKBuffer.size(), "", 0);
    backEndServer->jobQueueGroupEmotion.enqueueNotification(delGroupEmotionWorkNfc);
}

bool BackEndServer::ChatProjectHandler::deleteGroupEmotion(const std::string& groupEmotionId) {
    //delete all emotion in group first
    //delete in EmotionItem DB and GroupEmotion DB
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("deleteGroupEmotion");
    deleteAllEmotionInGroup(groupEmotionId);
    //delete in GroupEmotionItem
    GroupId* delGroupId = new GroupId();
    delGroupId->__set_groupId(groupEmotionId);
    string delGroupIdStr;
    delGroupId->writeSerialize(delGroupIdStr);
    char* key = (char*) malloc(delGroupIdStr.size());
    memcpy(key, delGroupIdStr.data(), delGroupIdStr.size());
    WorkNotification::Ptr delGroupEmotionWorkNfc = new WorkNotification(WorkNotification::DELETE_ITEM, "deleteGroupEmotion", key, delGroupIdStr.size(), "", 0);
    backEndServer->jobQueueGroupEmotionItem.enqueueNotification(delGroupEmotionWorkNfc);
}

void BackEndServer::ChatProjectHandler::addGroupEmotion(std::string& _return, const std::string& groupEmotionName) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("addGroupEmotion");
    //add to db groupEmotionItem
    //return groupEmotionId
    //get id for groupEmotion
    GroupEmotionItemIdGen* groupEmotionIdGen = GroupEmotionItemIdGen::createInstance(backEndServer->database[0]->getHashCount());
    int64_t lastGen = groupEmotionIdGen->getKey();
    GroupId* newGroupId = new GroupId();
    stringstream ss;
    ss << (lastGen - 1);
    string groupIdstr = ss.str();
    newGroupId->__set_groupId(groupIdstr);
    string kBuffer;
    newGroupId->writeSerialize(kBuffer);
    char* key = (char*) malloc(kBuffer.size());
    memcpy(key, kBuffer.data(), kBuffer.size());
    //serialize value
    char* value = (char*) malloc(groupEmotionName.size());
    memcpy(value, groupEmotionName.data(), groupEmotionName.size());
    WorkNotification::Ptr delEmotionItemWorkNfc = new WorkNotification(WorkNotification::ADD_ITEM, "addGroupEmotion", key, kBuffer.size(),
            value, groupEmotionName.size());
    backEndServer->jobQueueGroupEmotionItem.enqueueNotification(delEmotionItemWorkNfc);
    _return = groupIdstr;
}

void BackEndServer::ChatProjectHandler::gelAllGroupEmotion(std::string& _return) {
    //read from groupEmotionItem DB
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("gelAllGroupEmotion");
    _return = backEndServer->database[0]->getAllDataGroupEmotionItem();
}

void BackEndServer::ChatProjectHandler::getEmotionWithGroup(std::string& _return, const std::string& groupId) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getEmotionWithGroup");
    string kBuffer;
    string groupEmotionId = groupId;
    GroupId* pGroupId = new GroupId();
    pGroupId->__set_groupId(groupEmotionId);
    pGroupId->writeSerialize(kBuffer);
    DataStructure* groupEmotionDB = backEndServer->database[5];
    uint32_t vsizeGetListId = 0;
    char* listEmotionIdChr = groupEmotionDB->getValueFromKey(kBuffer.data(), kBuffer.size(), 10000, vsizeGetListId);
    string listEmotionIdStr(listEmotionIdChr, vsizeGetListId);
    ListEmotionId* listEmotionId = new ListEmotionId();
    listEmotionId->readSerialize(listEmotionIdStr);
    vector<int64_t> listEmotionIdVtr = listEmotionId->listEmotionId;
    //    cout<<"number of item in listEmotionId is: "<<listEmotionId->listEmotionId.capacity()<<endl;
    //get the value of EmotionItem from EmotionItem DB
    DataStructure* EmotionItemDB = backEndServer->database[1];
    if (listEmotionIdVtr.size() != 0)
        _return.append("[");
    for (int i = 0; i < listEmotionIdVtr.size(); i++) {
        EmotionId* tmpEIdKey = new EmotionId();
        tmpEIdKey->__set_emotionId(listEmotionIdVtr[i]);
        string serStr;
        tmpEIdKey->writeSerialize(serStr);
        uint32_t emotionVal = 0;
        char* val = EmotionItemDB->getValueFromKey(serStr.data(), serStr.size(), 10000, emotionVal);
        string tmp(val, emotionVal);
        _return.append(tmp).append(",");
        // break;
    }
    if (listEmotionIdVtr.size() != 0)
        _return.append("]");
}

bool BackEndServer::ChatProjectHandler::checkImageEmotionExist(const std::string& imageStyle, const std::string& groupEmotionId) {
    string returnValue;
    getEmotionWithGroup(returnValue, groupEmotionId);
    return returnValue.find(imageStyle, 0);
}

void BackEndServer::ChatProjectHandler::kickUser(const std::string& userId) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("kickUser");
}

void BackEndServer::ChatProjectHandler::getRoomStatisticByDate(RoomStatistic& _return, const int32_t date) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getRoomStatisticByDate");
}

void BackEndServer::ChatProjectHandler::getEmotionStatisticByDate(EmotionStatistic& _return, const int32_t date) {
    // Your implementation goes here
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getEmotionStatisticByDate");
}

bool BackEndServer::ChatProjectHandler::sendMessageInternal(const MsgItem& msgItem, const bool messageOnline) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("sendMessageInternal");
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
void BackEndServer::ChatProjectHandler::saveListMsgId_NotService(const std::string& userId1, const std::string& userId2, const int64_t listMsgId) {
}

void BackEndServer::ChatProjectHandler::saveListMsgItem(const std::vector<MsgItem> & listMsgItem) {
}

/*
 * Return immediately
 */
int64_t BackEndServer::ChatProjectHandler::getNewMsgId() {
}

void BackEndServer::ChatProjectHandler::getMessageList(std::vector<MsgItem> & _return, const std::string& userId1, const std::string& userId2, const int32_t totalMsg) {
}

void BackEndServer::ChatProjectHandler::getMessageIdList(std::vector<int64_t> & _return, const std::string& userId1, const std::string& userId2, const int32_t totalMsg) {
}

void BackEndServer::ChatProjectHandler::getAllMessageIdList(std::vector<int64_t> & _return, const std::string& userId1, const std::string& userId2) {
}
//

void BackEndServer::ChatProjectHandler::getMessageItem(MsgItem& _return, const int64_t MsgId) {
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
    //read GroupEmotion DB to get list of EmotionId
    //get all group emotion
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("loadAllEmotion");

    DataStructure* groupEmotionItemDB = backEndServer->database[0];
    _return.append("[");
    for (int index = 0; index < groupEmotionItemDB->getHashCount(); index++) {
        stringstream ss;
        ss << index;
        string str = ss.str();
        string kBuffer;
        string groupEmotionId = str;
        GroupId* pGroupId = new GroupId();
        pGroupId->__set_groupId(groupEmotionId);
        pGroupId->writeSerialize(kBuffer);

        DataStructure* groupEmotionDB = backEndServer->database[5];
        uint32_t vsizeGetListId = 0;
        char* listEmotionIdChr = groupEmotionDB->getValueFromKey(kBuffer.data(), kBuffer.size(), 100000, vsizeGetListId);
        string listEmotionIdStr(listEmotionIdChr, vsizeGetListId);
        ListEmotionId* listEmotionId = new ListEmotionId();
        listEmotionId->readSerialize(listEmotionIdStr);
        vector<int64_t> listEmotionIdVtr = listEmotionId->listEmotionId;
        //    cout<<"number of item in listEmotionId is: "<<listEmotionId->listEmotionId.capacity()<<endl;
        //get the value of EmotionItem from EmotionItem DB

        DataStructure* EmotionItemDB = backEndServer->database[1];
        if (listEmotionIdVtr.size() != 0) {
            for (int i = 0; i < listEmotionIdVtr.size(); i++) {
                EmotionId* tmpEIdKey = new EmotionId();
                tmpEIdKey->__set_emotionId(listEmotionIdVtr[i]);
                string serStr;
                tmpEIdKey->writeSerialize(serStr);
                uint32_t emotionVal = 0;
                char* val = EmotionItemDB->getValueFromKey(serStr.data(), serStr.size(), 10000, emotionVal);
                string tmp(val, emotionVal);
                //        cout << "___________________________" << serStr << "-----" << tmp << "______________________________" << endl;
                _return.append(tmp).append(",");
                // break;
            }
            _return = _return.substr(0, _return.size() - 2);

        }
    }
    _return.append("]");

}

void BackEndServer::ChatProjectHandler::checkOfflineMessage(std::vector<std::string> & _return, const std::string& userId) {
}

void BackEndServer::ChatProjectHandler::setLogin(const std::string& sessionId) {
}

bool BackEndServer::ChatProjectHandler::isLogin(const std::string& sessionId) {
}

void BackEndServer::ChatProjectHandler::setLogout(const std::string& sessionId) {
}

int64_t BackEndServer::ChatProjectHandler::getImageId() {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getImageId");
    int64_t lastGen = readFromImageMngFile();
    int64_t newLastGen = KeyGenerate::createInstance(lastGen)->getKey();
    writeToImageMngFile(newLastGen);
    cout << newLastGen << endl;
    return newLastGen;
}

bool BackEndServer::ChatProjectHandler::writeToImageMngFile(int64_t value) {
    ofstream imageMngFileStr("imageIdFile.txt");
    if (!imageMngFileStr.is_open()) {
        cout << "fail to open" << endl;
    }
    imageMngFileStr << value << endl;
    imageMngFileStr.close();
}

int64_t BackEndServer::ChatProjectHandler::readFromImageMngFile() {
//    ifstream imageMngFileStr("imageIdFile.txt");
    ifstream imageMngFileStr("../../../imageIdFile.txt");
    if (!imageMngFileStr.is_open()) {
        cout << "fail to open" << endl;
    }
    int64_t lastGen;
    imageMngFileStr >> lastGen;
    imageMngFileStr.close();
    return lastGen;
}

void BackEndServer::ChatProjectHandler::adminRegister(const std::string& userName, const std::string& password) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("adminRegister");
    UserName* uName = new UserName();
    Password* pWord = new Password();
    uName->__set_userName(userName);
    pWord->__set_passWord(password);
    string kUName, vPWord;
    uName->writeSerialize(kUName);
    pWord->writeSerialize(vPWord);

    char* key = (char*) malloc(kUName.size());
    memcpy(key, kUName.data(), kUName.size());

    char* value = (char*) malloc(vPWord.size());
    memcpy(value, vPWord.data(), vPWord.size());

    BackEndServer::backEndServer->database[6]->saveToCachDB(key, kUName.size(), value, vPWord.size());
    BackEndServer::backEndServer->database[6]->writeToDisk();
}

bool BackEndServer::ChatProjectHandler::checkAdmin(const std::string& userName, const std::string& password) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("checkAdmin");
    UserName* uName = new UserName();
    Password* pWord = new Password();
    uName->__set_userName(userName);
    string kUName;
    uName->writeSerialize(kUName);

    char* key = (char*) malloc(kUName.size());
    memcpy(key, kUName.data(), kUName.size());
    uint32_t vsizeDB = 0;
    char* value = BackEndServer::backEndServer->database[6]->getValueFromKey(key, kUName.size(), 10000, vsizeDB);
    string vPWord(value, vsizeDB);
    pWord->readSerialize(vPWord);
    return (strcmp(pWord->passWord.data(), password.data()) == 0);
}

void BackEndServer::ChatProjectHandler::getAllAdmin(std::vector<std::string> & _return) {
    Logger& logger = ServerLog::getLoggerForActivity(ServerLog::SERVICE);
    logger.information("getAllAdmin");
    _return = backEndServer->database[6]->getAllAdmins();
}
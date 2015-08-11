/* 
 * File:   JobWorker.cpp
 * Author: root
 * 
 * Created on December 21, 2013, 6:35 AM
 */

#include "ini/JobWorker.h"
#include <iostream>
#include "stdio.h"
#include "ini/LinkListId.h"
#include "../src/ini/thrift/chatProject_types.h"
#include "ini/ServerLog.h"
using namespace std;
using namespace ChatProject;

JobWorker::~JobWorker() {
}

JobWorker::JobWorker(std::string name, Poco::NotificationQueue& jobQueue, DataStructure* database) :
name(name), jobQueue(jobQueue), database(database) {
    groupEmotionItem = "GroupEmotionItem";
    emotionItem = "EmotionItem";
    roomStatistic = "RoomStatistic";
    emotionStatistic = "EmotionStatistic";
    chatRoom ="ChatRoom";
    groupEmotion ="GroupEmotion";
}

void JobWorker::run() {
    while (true) {
        //        cout<<"In run of jobWorker"<<endl;
        Poco::Notification::Ptr pNf(jobQueue.waitDequeueNotification());
        if (pNf) {
            const Poco::Notification::Ptr cpNf = pNf.get();
            WorkNotification::Ptr pWorkNf = pNf.cast<WorkNotification>();
            if (pWorkNf) {
                Poco::FastMutex::ScopedLock lock(JobWorker::_mutex);
                process(pWorkNf->getType(), "", pWorkNf->getKey(), pWorkNf->getKsize(), pWorkNf->getValue(), pWorkNf->getVsize());
            }
            Poco::Thread::sleep(200);
        } else {
            break;
        }
    }
}

void JobWorker::process(WorkNotification::JobType jobType, std::string, const char* key, const size_t pKsize, const char* value, const size_t pVsize) {
    //cast ksize and vsize to size_t
    //    size_t ks = (size_t) pKsize;
    //    size_t& ksize = ks;
    //    size_t vs = (size_t) pVsize;
    //    size_t& vsize = vs;
    string databaseName = database->getDataName().c_str();
    switch (jobType) {
        case WorkNotification::ADD_ITEM:
        {
            
            //            cout <<  << databaseName << endl;
            string add("ADD_ITEM to database: ");
            add.append(databaseName);
            Logger& logger = ServerLog::getLoggerForActivity(ServerLog::ACTIVITY);
            logger.information(add);
            if (database->isExist(key, pKsize)) {
                if (databaseName.compare(groupEmotionItem) == 0) {
                    //add new GroupEmotion
                    //                    cout << "in save to cacheDB of GroupEmotionItem " << endl;
                    database->saveToCachDB(key, pKsize, value, pVsize);
                } else if (databaseName.compare(emotionItem) == 0) {
                    //add new EmotionItem
                    database->saveToCachDB(key, pKsize, value, pVsize);
                } else if (databaseName.compare(roomStatistic) == 0) {
                    //
                    //                    cout << "in save to cacheDB of RoomStatistic " << endl;
                    database->saveToCachDB(key, pKsize, value, pVsize);
                } else if (databaseName.compare(emotionStatistic) == 0) {
                    //
                    //                    cout << "in save to cacheDB of EmotionStatistic " << endl;
                    database->saveToCachDB(key, pKsize, value, pVsize);
                } else if (databaseName.compare(chatRoom) == 0) {
                    //
                    //                    cout << "in save to cacheDB of ChatRoom " << endl;
                    database->saveToCachDB(key, pKsize, value, pVsize);
                } else if (databaseName.compare(groupEmotion) == 0) {
                    //chang ListEmotionId
                    uint32_t curVSize;
                    //get current MsgId List in DB
                    char* curVValue;
                    curVValue = database->getValueFromKey(key, pKsize, 100000, curVSize);
                    string vBuffer(curVValue, curVSize);
                    ListEmotionId* emotionIdListReturn = new ListEmotionId();
                    emotionIdListReturn->readSerialize(vBuffer);
                    //push the value into current MsgId List
                    string valBuffer(value, pVsize);
                    ListEmotionId* pushList = new ListEmotionId();
                    pushList->readSerialize(valBuffer);
                    vector<int64_t> newEmotionIdList = emotionIdListReturn->listEmotionId;
                    newEmotionIdList.push_back(pushList->listEmotionId[0]);
                    //serialize and save to DB
                    string saveValue;
                    ListEmotionId* saveList = new ListEmotionId();
                    saveList->__set_listEmotionId(newEmotionIdList);
                    saveList->writeSerialize(saveValue);
                    database->saveToCachDB(key, pKsize, saveValue.data(), saveValue.size());
                }
            } else {
                //add new (key, value)
                database->saveToCachDB(key, pKsize, value, pVsize);
            }
            break;
        }
        case WorkNotification::DELETE_ITEM:
        {
            database->deleteRecord(key, pKsize);
            break;
        }
        default:
            //            cout << "DEFAULT: " << endl;
            break;
    }
}

std::string JobWorker::getName() {
    return name;
}
Poco::FastMutex JobWorker::_mutex;
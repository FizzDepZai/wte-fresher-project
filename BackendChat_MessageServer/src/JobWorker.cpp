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
    messageId = "MessageId";
    messageItem = "MessageItem";
    messageOffline = "MessageOffline";
}

void JobWorker::run() {
    while (true) {
        //        cout<<"In run of jobWorker"<<endl;
        Poco::Notification::Ptr pNf(jobQueue.waitDequeueNotification());
        //tu khoang nay tro di la phan phoi cho cac thread trong pool xu ly
        if (pNf) {
            const Poco::Notification::Ptr cpNf = pNf.get();
            WorkNotification::Ptr pWorkNf = pNf.cast<WorkNotification>();
            /*
             * Test stt
             */
            //            if (strcmp(database->getDataName().data(), "MessageId") == 0) {
            //                cout << "----------------------------" << pWorkNf->getvalueListId() << endl;
            //            }
            if (pWorkNf) {
                //neu ko co mutex message se luu lon xon, ko theo thu tu
                Poco::FastMutex::ScopedLock lock(JobWorker::_mutex);
                if (strcmp(database->getDataName().data(), "MessageItem") == 0)
                    process(pWorkNf->getType(), "", pWorkNf->getKey(), pWorkNf->getKsize(), pWorkNf->getValue(), pWorkNf->getVsize());
                else if (strcmp(database->getDataName().data(), "MessageId") == 0) {
                    //                    cout << "+++++++++++++++++++++++++" << pWorkNf->getvalueListId() << endl;
                    process(pWorkNf->getType(), "", pWorkNf->getKey(), pWorkNf->getKsize(), pWorkNf->getvalueListId(), pWorkNf->getVsizeListId());
                } else if (strcmp(database->getDataName().data(), "MessageOffline") == 0) {
                    process(pWorkNf->getType(), "", pWorkNf->getKey(), pWorkNf->getKsize(), pWorkNf->getValue(), pWorkNf->getVsize());
                }
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
            if (databaseName.compare(messageItem) == 0) {
                //add Message Item 
                database->saveToCachDB(key, pKsize, value, pVsize);
            } else if (databaseName.compare(messageOffline) == 0) {
                //get current listMsgOffline of key
                uint32_t vsizeGetCur = 0;
                char* valueGetCur = database->getValueFromKey(key, pKsize, 100000, vsizeGetCur);
                string curMsgOff(valueGetCur, vsizeGetCur);
                ListOfflineMsg* curMsgOffList = new ListOfflineMsg();
                curMsgOffList->readSerialize(curMsgOff);
                //append the additional UserId
                UserId* newUserId = new UserId();
                string newOffMsgUser(value, pVsize);

                newUserId->readSerialize(newOffMsgUser);

                vector<string> newMsgOffList = curMsgOffList->listOfflineMsg;
                bool isExist = false;
                for (int i = 0; i < newMsgOffList.size(); i++) {
                    if (newMsgOffList[i].compare(newUserId->userId) == 0) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    newMsgOffList.push_back(newUserId->userId);
                    //serialize and save
                    ListOfflineMsg* newSaveMsgOffList = new ListOfflineMsg();
                    newSaveMsgOffList->__set_listOfflineMsg(newMsgOffList);
                    string newMsgOffListStr;
                    newSaveMsgOffList->writeSerialize(newMsgOffListStr);
                    char* newMsgOffListChr = (char*) malloc(newMsgOffListStr.size());
                    memcpy(newMsgOffListChr, newMsgOffListStr.data(), newMsgOffListStr.size());
                    database->saveToCachDB(key, pKsize, newMsgOffListChr, newMsgOffListStr.size());
                }
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

void JobWorker::process(WorkNotification::JobType jobType, std::string, const char* key, const size_t pKsize, const int64_t pvalueIdList, const size_t pVsizeIdList) {
    string databaseName = database->getDataName();
    switch (jobType) {
        case WorkNotification::ADD_ITEM:
        {
            if (databaseName.compare(messageId) == 0) {
                //change listId
                //                cout << "in save to cacheDB of MessageIdList---" << pvalueIdList << "----" << endl;
                uint32_t curVSize = 0;
                //get current MsgId List in DB
                char* curVValue;
                curVValue = database->getValueFromKey(key, pKsize, 100000, curVSize);
                string vBuffer(curVValue, curVSize);
                MsgIdList* msgIdListReturn = new MsgIdList();
                msgIdListReturn->readSerialize(vBuffer);
                //push the value into current MsgId List
                //                    string valBuffer(value, pVsize);
                //                    MsgIdList* pushList = new MsgIdList();
                //                    pushList->readSerialize(valBuffer);
                vector<int64_t> newMsgItemList = msgIdListReturn->msgIdList;
                newMsgItemList.push_back(pvalueIdList);

                //serialize and save to DB
                string saveValue;
                MsgIdList* saveList = new MsgIdList();
                saveList->__set_msgIdList(newMsgItemList);
                saveList->writeSerialize(saveValue);
                /*
                 *test
                 */
                //                MsgIdList* testList = new MsgIdList();
                //                testList->readSerialize(saveValue);
                //                cout << "-----------------" << testList->msgIdList.size() << endl;
                database->saveToCachDB(key, pKsize, saveValue.data(), saveValue.size());
            }
        }
    }
}

std::string JobWorker::getName() {
    return name;
}
Poco::FastMutex JobWorker::_mutex;
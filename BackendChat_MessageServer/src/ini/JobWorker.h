/* 
 * File:   JobWorker.h
 * Author: root
 *
 * Created on December 21, 2013, 6:35 AM
 */

#ifndef JOBWORKER_H
#define	JOBWORKER_H
#include "Runnable.h"
#include "Poco/Mutex.h"
#include <Poco/NotificationQueue.h>
#include <Poco/Thread.h>
#include "DataStructure.h"
#include "WorkNotification.h"
using namespace std;

class JobWorker : public Poco::Runnable{
public:
    static Poco::FastMutex _mutex;
    JobWorker(std::string, Poco::NotificationQueue&, DataStructure*);
    void run();
    virtual ~JobWorker();
    std::string getName();
    string messageId;
    string messageItem;
    string messageOffline;
private:
    DataStructure* database;
    std::string name;
    Poco::NotificationQueue& jobQueue;
    void process(WorkNotification::JobType, string, const char*, const size_t, const char*, const size_t);
    void process(WorkNotification::JobType, string, const char*, const size_t, const int64_t, const size_t);
};

#endif	/* JOBWORKER_H */


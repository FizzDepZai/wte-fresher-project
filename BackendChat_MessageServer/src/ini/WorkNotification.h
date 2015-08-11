/* 
 * File:   WorkNotification.h
 * Author: Thanhpv
 *
 * Created on December 21, 2013, 6:57 AM
 */

#ifndef WORKNOTIFICATION_H
#define	WORKNOTIFICATION_H
#include <stdio.h>
#include <iostream>
#include <Poco/Notification.h>
#include <stddef.h>
using namespace std;

class WorkNotification : public Poco::Notification {
public:
    typedef Poco::AutoPtr<WorkNotification> Ptr;
    
    enum JobType {
        ADD_ITEM = 1,
        DELETE_ITEM = 2,
        RETRIEVE_ITEM = 3
    };
    virtual ~WorkNotification();
    WorkNotification(JobType, std::string, const char*, const size_t , const char*, const size_t);
    WorkNotification(JobType, std::string, const char*, const size_t , const int64_t, const size_t);
    JobType getType();
    char* getKey();
    char* getValue();
    int64_t getvalueListId();
    unsigned int getKsize();
    unsigned int getVsize();
    unsigned int getVsizeListId();
private:
    JobType type;
    char* key;
    char* valueItem;
    int64_t valueListId;
    unsigned int ksize;
    unsigned int vsizeItem;
    unsigned int vsizeListId;
};

#endif	/* WORKNOTIFICATION_H */


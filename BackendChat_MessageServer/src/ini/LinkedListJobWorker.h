///* 
// * File:   LinkedListJobWorker.h
// * Author: root
// *
// * Created on December 21, 2013, 8:07 PM
// */
//#include "JobWorker.h"
//#include "JobWorkerNode.h"
//
//#include <stdio.h>
//#include <iostream>
//
//#ifndef LINKEDLISTJOBWORKER_H
//#define	LINKEDLISTJOBWORKER_H
//
//using namespace std;
//class LinkedListJobWorker {
//public:
//    enum Error{
//        ADD_SUCCESS = 1,
//        ADD_FAIL =2
//    };
//    LinkedListJobWorker();
//    LinkedListJobWorker(const LinkedListJobWorker& orig);
//    virtual ~LinkedListJobWorker();
//    int add(std::string name, Poco::NotificationQueue& jobQueue, DataStructure* database);
//    JobWorker* getJobWorkerNodeForUpdate(string name);
//    int getSize();
//private:
//    int count;
//    JobWorkerNode* pHead;
//};
//
//#endif	/* LINKEDLISTJOBWORKER_H */
//

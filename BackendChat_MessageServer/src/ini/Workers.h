///* 
// * File:   Workers.h
// * Author: root
// *
// * Created on December 21, 2013, 8:37 PM
// */
//#include "JobWorker.h"
//#include "LinkedListJobWorker.h"
//
//#include <iostream>
//#include <stdio.h>
//
//#ifndef WORKERS_H
//#define	WORKERS_H
//
//using namespace std;
//
//class Workers {
//public:
//    Workers(const Workers& orig);
//    virtual ~Workers();
//    static Workers* createInstance();
//    void addJobWorker(std::string name, Poco::NotificationQueue& jobQueue, DataStructure* database);
//    JobWorker* getJobWorker(string workerName);
//    int getJobWorkerCount();
//private:
//    static Workers* workers;
//    LinkedListJobWorker* jobWorkerList;
//    Workers();
//};
//
//#endif	/* WORKERS_H */
//

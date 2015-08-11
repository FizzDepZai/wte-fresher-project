///* 
// * File:   Workers.cpp
// * Author: root
// * 
// * Created on December 21, 2013, 8:37 PM
// */
//
//#include "ini/Workers.h"
//Workers* Workers::workers=NULL;
//Workers::Workers() {
//    Workers::jobWorkerList = new LinkedListJobWorker();
//}
//
//Workers::Workers(const Workers& orig) {
//}
//
//Workers::~Workers() {
//}
//
//Workers* Workers::createInstance(){
//    if (!workers) {
//        workers = new Workers();
//    }
//    return workers;
//}
//void Workers::addJobWorker(std::string name, Poco::NotificationQueue& jobQueue, DataStructure* database) {
//    jobWorkerList->add(name, jobQueue, database);
//}
//JobWorker* Workers::getJobWorker(string jobWorkerName) {
//    JobWorker* jobWorker = this->jobWorkerList->getJobWorkerNodeForUpdate(jobWorkerName); 
//    return jobWorker;
//}
//
//int Workers::getJobWorkerCount(){
//    return jobWorkerList->getSize();
//}
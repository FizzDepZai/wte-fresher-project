///* 
// * File:   LinkedListJobWorker.cpp
// * Author: root
// * 
// * Created on December 21, 2013, 8:07 PM
// */
//
//#include "ini/LinkedListJobWorker.h"
//#include "ini/DataStructure.h"
//
//LinkedListJobWorker::LinkedListJobWorker() {
//    this->count = 0;
//}
//
//LinkedListJobWorker::LinkedListJobWorker(const LinkedListJobWorker& orig) {
//}
//
//LinkedListJobWorker::~LinkedListJobWorker() {
//}
//
//int LinkedListJobWorker::add(std::string name, Poco::NotificationQueue& jobQueue, DataStructure* database) {
//    JobWorkerNode* node = new JobWorkerNode();
//    JobWorker* value = new JobWorker(name, jobQueue, database);
//    node->next = NULL;
//    node->value = value;
//    if (this->count == 0) {
//        
//        pHead = node;
//    } else {
////        cout << "number of LinkedListJobWorker is: " << this->count << endl;
//        JobWorkerNode* tmp = pHead;
//        while (tmp->next != NULL) {
//            tmp = tmp->next;
//        }
//        tmp->next = node;
//    }
//    node = NULL;
//    value = NULL;
//    count++;
//    return LinkedListJobWorker::ADD_SUCCESS;
//}
//
//JobWorker* LinkedListJobWorker::getJobWorkerNodeForUpdate(string name) {
//    JobWorkerNode* traverse = this->pHead;
//    while (traverse) {
//        if (traverse->value->getName() != name)
//            traverse = traverse->next;
//        else break;
//    }
//    if (traverse)
//        return traverse->value;
//    else
//        return NULL;
//}
//
//int LinkedListJobWorker::getSize() {
//    return count;
//}
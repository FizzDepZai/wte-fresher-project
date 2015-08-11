/* 
 * File:   LinkedListWriteToDBWorker.cpp
 * Author: root
 * 
 * Created on December 21, 2013, 9:07 PM
 */

#include "ini/LinkedListWriteToDBWorker.h"

LinkedListWriteToDBWorker::LinkedListWriteToDBWorker() {
    this->count = 0;
}

LinkedListWriteToDBWorker::LinkedListWriteToDBWorker(const LinkedListWriteToDBWorker& orig) {
}

LinkedListWriteToDBWorker::~LinkedListWriteToDBWorker() {
}

int LinkedListWriteToDBWorker::add(std::string name, DataStructure* database) {
    WriteToDBWorkerNode* node = new WriteToDBWorkerNode();
    WriteToDBWorker* value = new WriteToDBWorker(name, database);
    node->next = NULL;
    node->value = value;
    if (this->count == 0) {
        pHead = node;
    } else {
        WriteToDBWorkerNode* tmp = pHead;
//        cout<< "number of LinkedListWriteToDBWorker is: " << this->count << endl;
        while (tmp->next != NULL) {
            tmp = tmp->next;
        }
        tmp->next = node;
    }
    node = NULL;
    value = NULL;
    count++;
    return LinkedListWriteToDBWorker::ADD_SUCCESS;
}

WriteToDBWorker* LinkedListWriteToDBWorker::getWriteToDBbWorkerNodeForUpdate(string name) {
    WriteToDBWorkerNode* traverse = this->pHead;
    while (traverse) {
        if (traverse->value->getName() != name)
            traverse = traverse->next;
        else break;
    }
    if (traverse)
        return traverse->value;
    else
        return NULL;
}

int LinkedListWriteToDBWorker::getSize() {
    return count;
}
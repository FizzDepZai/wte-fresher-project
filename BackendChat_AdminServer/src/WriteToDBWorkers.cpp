/* 
 * File:   WriteToDBWorkers.cpp
 * Author: root
 * 
 * Created on December 21, 2013, 9:28 PM
 */

#include "ini/WriteToDBWorkers.h"
#include "ini/LinkedListWriteToDBWorker.h"
WriteToDBWorkers* WriteToDBWorkers::writeToDBWorkers=NULL;
WriteToDBWorkers::WriteToDBWorkers() {
    WriteToDBWorkers::writeToDBWorkerList = new LinkedListWriteToDBWorker();
}

WriteToDBWorkers::WriteToDBWorkers(const WriteToDBWorkers& orig) {
}

WriteToDBWorkers::~WriteToDBWorkers() {
}

WriteToDBWorkers* WriteToDBWorkers::createInstance(){
    if (!writeToDBWorkers) {
        writeToDBWorkers = new WriteToDBWorkers();
    }
    return writeToDBWorkers;
}
void WriteToDBWorkers::addWriteToDBWorker(std::string name, DataStructure* database) {
    writeToDBWorkerList->add(name, database);
}
WriteToDBWorker* WriteToDBWorkers::getWriteToDBWorker(string writeToDBWorkerName) {
    WriteToDBWorker* writeToDBWorker = this->writeToDBWorkerList->getWriteToDBbWorkerNodeForUpdate(writeToDBWorkerName); 
    return writeToDBWorker;
}

int WriteToDBWorkers::getWriteToDBWorkerCount(){
    return writeToDBWorkerList->getSize();
}
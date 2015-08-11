/* 
 * File:   WriteToDBWorker.cpp
 * Author: Thanhpv
 * 
 * Created on December 21, 2013, 6:04 AM
 */
#include <Poco/Thread.h>
#include "ini/WriteToDBWorker.h"
#include "ini/ServerLog.h"

WriteToDBWorker::WriteToDBWorker() {

}

WriteToDBWorker::~WriteToDBWorker() {

}

WriteToDBWorker::WriteToDBWorker(string name, DataStructure* database) : name(name), database(database), TIME_DELAY(300) {

}

void WriteToDBWorker::run() {
    while (true) {
        cout<<"__WRITE TO HASHDB "<< database->getDataName()<<endl;
        database->writeToDisk();
        Poco::Thread::sleep(TIME_DELAY * 1000); //millisecond
    }
}

std::string WriteToDBWorker::getName() {
    return name;
}
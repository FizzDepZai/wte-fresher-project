/* 
 * File:   WriteToDBWorkers.h
 * Author: root
 *
 * Created on December 21, 2013, 9:28 PM
 */

#ifndef WRITETODBWORKERS_H
#define	WRITETODBWORKERS_H

#include <iostream>
#include <stdio.h>
#include "LinkedListWriteToDBWorker.h"
using namespace std;
class WriteToDBWorkers {
public:
    WriteToDBWorkers(const WriteToDBWorkers& orig);
    virtual ~WriteToDBWorkers();
    static WriteToDBWorkers* createInstance();
    void addWriteToDBWorker(std::string name, DataStructure* database);
    WriteToDBWorker* getWriteToDBWorker(string workerName);
    int getWriteToDBWorkerCount();
private:
    static WriteToDBWorkers* writeToDBWorkers;
    LinkedListWriteToDBWorker* writeToDBWorkerList;
     WriteToDBWorkers();
};

#endif	/* WRITETODBWORKERS_H */


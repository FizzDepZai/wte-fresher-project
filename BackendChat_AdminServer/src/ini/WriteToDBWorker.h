/* 
 * File:   WriteToDBWorker.h
 * Author: Thanhpv
 *
 * Created on December 21, 2013, 6:04 AM
 */

#ifndef WRITETODBWORKER_H
#define	WRITETODBWORKER_H

#include <stdio.h>
#include <iostream>

#include "Poco/Runnable.h"
#include "DataStructure.h"

class WriteToDBWorker : public Poco::Runnable {
public:
    WriteToDBWorker();
    virtual ~WriteToDBWorker();
    WriteToDBWorker(std::string, DataStructure*);
    void run();
    std::string getName();
private:
    string name;
    DataStructure* database;
    int TIME_DELAY;
};

#endif	/* WRITETODBWORKER_H */


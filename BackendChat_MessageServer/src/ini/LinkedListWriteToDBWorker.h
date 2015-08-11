/* 
 * File:   LinkedListWriteToDBWorker.h
 * Author: root
 *
 * Created on December 21, 2013, 9:07 PM
 */
#include "WriteToDBWorker.h"
#include "WriteToDBWorkerNode.h"
#ifndef LINKEDLISTWRITETODBWORKER_H
#define	LINKEDLISTWRITETODBWORKER_H

class LinkedListWriteToDBWorker {
public:
    enum Error{
        ADD_SUCCESS = 1,
        ADD_FAIL =2
    };
    LinkedListWriteToDBWorker();
    LinkedListWriteToDBWorker(const LinkedListWriteToDBWorker& orig);
    virtual ~LinkedListWriteToDBWorker();
    int add(std::string name, DataStructure* database);
    WriteToDBWorker* getWriteToDBbWorkerNodeForUpdate(string name);
    int getSize();
private:
    int count;
    WriteToDBWorkerNode* pHead;
};

#endif	/* LINKEDLISTWRITETODBWORKER_H */


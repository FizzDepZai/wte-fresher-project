/* 
 * File:   WriteToDBWorkerNode.h
 * Author: root
 *
 * Created on December 21, 2013, 9:05 PM
 */
#include "WriteToDBWorker.h"

#ifndef WRITETODBWORKERNODE_H
#define	WRITETODBWORKERNODE_H

class WriteToDBWorkerNode {
    public:
    WriteToDBWorker* value;
    WriteToDBWorkerNode* next;
    
};

#endif	/* WRITETODBWORKERNODE_H */


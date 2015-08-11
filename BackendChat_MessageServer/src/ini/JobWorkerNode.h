/* 
 * File:   JobWorkerNode.h
 * Author: root
 *
 * Created on December 21, 2013, 8:10 PM
 */
#include "JobWorker.h"

#ifndef JOBWORKERNODE_H
#define	JOBWORKERNODE_H

class JobWorkerNode {
public:
    JobWorker* value;
    JobWorkerNode* next;
};

#endif	/* JOBWORKERNODE_H */


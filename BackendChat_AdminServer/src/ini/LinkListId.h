/* 
 * File:   LinkList.h
 * Author: minh
 *
 * Created on December 17, 2013, 3:33 PM
 */

#ifndef LINKLIST_H
#define	LINKLIST_H
#include "Node.h"

class LinkList {
private:
        int count;
	Node *pHead;
        Node *pTail;
public:
        LinkList();
        LinkList(const LinkList& orig);
        virtual ~LinkList();
	Node* Top();
        Node* Tail();
        int Count();
	int Add(int );
	int IsEmpty();
};

#endif	/* LINKLIST_H */


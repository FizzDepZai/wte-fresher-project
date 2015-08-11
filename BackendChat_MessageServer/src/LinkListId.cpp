///* 
// * File:   LinkList.cpp
// * Author: minh
// * 
// * Created on December 17, 2013, 3:33 PM
// */
//
//#include "ini/LinkListId.h"
//#include "stdio.h"
//#include "Config.h"
//
//LinkList::LinkList() {
//    count = 0;
//    pHead = NULL;
//    pTail = NULL;
//}
//
//LinkList::LinkList(const LinkList& orig) {
//}
//
//LinkList::~LinkList() {
//}
//
///*
// * return count or -1 if fail
// */
//int LinkList::Add(int x) {
//    Node *p = new Node;
//    if (p == NULL)
//        return -1;
//    p->num = x;
//    p->pNext = pHead;
//    if (count == 0) {
//        pHead = p;
//        pHead->pNext = pTail;
//    } else {
//        pTail = p;
//        pTail->pNext = NULL;
//    }
//    count++;
//    return count;
//}
//
//int LinkList::IsEmpty() {
//    if (pHead == NULL)
//        return 1;
//    return 0;
//}
//
//Node* LinkList::Top() {
//    if (IsEmpty() == 1)
//        return NULL;
//    return
//    pHead;
//}
//
//Node* LinkList::Tail() {
//    if (IsEmpty() == 1)
//        return NULL;
//    return
//    pTail;
//}
//
//int LinkList::Count() {
//    return count;
//}
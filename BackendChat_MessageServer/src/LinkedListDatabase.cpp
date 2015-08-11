///* 
// * File:   LinkedListTemplate.cpp
// * Author: root
// * 
// * Created on December 18, 2013, 11:47 AM
// */
//
//#include "ini/LinkedListDatabase.h"
//#include "ini/DataStructure.h"
//#include "ini/DataStructureNode.h"
//using namespace std;
//
//LinkedList::LinkedList() {
//    count = 0;
//    pHead = NULL;
//}
//
//LinkedList::LinkedList(const LinkedList& orig) {
//}
//
//LinkedList::~LinkedList() {
//
//}
//
//int LinkedList::add(string name) {
//    DataStructureNode* node = new DataStructureNode();
//    DataStructure* value = new DataStructure(name);
//    node->next = NULL;
//    node->value = value;
//    if (this->count == 0) {
//        pHead = node;
//    } else {
//        DataStructureNode* tmp = pHead;
//        while (tmp->next != NULL) {
//            tmp = tmp->next;
//        }
//        tmp->next = node;
//        tmp = NULL;
//    }
//    node = NULL;
//    value = NULL;
//    count++;
//    return LinkedList::ADD_SUCCESS;
//}
//
///*
// * return the pointer to the specific Node has name
// */
//DataStructure* LinkedList::getDataStructureNodeForUpdate(string name) {
//    DataStructureNode* traverse = this->pHead;
//    while (traverse) {
//        if (traverse->value->getDataName() != name)
//            traverse = traverse->next;
//        else break;
//    }
//    if (traverse)
//        return traverse->value;
//    else
//        return NULL;
//}
//
///*
// * return size
// */
//
//int LinkedList::getSize() {
//    return count;
//}
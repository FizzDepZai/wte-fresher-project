/* 
 * File:   WorkNotification.cpp
 * Author: root
 * 
 * Created on December 21, 2013, 6:57 AM
 */

#include "ini/WorkNotification.h"

WorkNotification::~WorkNotification() {
}

WorkNotification::WorkNotification(WorkNotification::JobType pType, std::string name, const char* pKey, const size_t pKsize, const char* pValue, const size_t pVsize):
type(pType), key((char*)pKey), ksize(pKsize), value((char*)pValue), vsize(pVsize)
{
}
WorkNotification::JobType WorkNotification::getType(){
    return this->type;
}
char* WorkNotification::getKey(){
    return this->key;
}
char* WorkNotification::getValue(){
    return this->value;
}
unsigned int WorkNotification::getKsize(){
    return this->ksize;
}
unsigned int WorkNotification::getVsize(){
    return this->vsize;
}
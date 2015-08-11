/* 
 * File:   GroupEmotionItemIdGen.cpp
 * Author: root
 * 
 * Created on January 7, 2014, 11:21 PM
 */

#include "GroupEmotionItemIdGen.h"
#include <stddef.h>
GroupEmotionItemIdGen* GroupEmotionItemIdGen::keyGenerate = NULL;
int GroupEmotionItemIdGen::theLastGenerate = 1;
GroupEmotionItemIdGen::GroupEmotionItemIdGen(int theLast) {
    GroupEmotionItemIdGen::theLastGenerate = theLast+1;
}

int GroupEmotionItemIdGen::getKey(){
    return theLastGenerate++;
}
GroupEmotionItemIdGen::~GroupEmotionItemIdGen() {
}
GroupEmotionItemIdGen* GroupEmotionItemIdGen::createInstance(int theLast){
    if(!GroupEmotionItemIdGen::keyGenerate){
        keyGenerate = new GroupEmotionItemIdGen(theLast);
    }
    return keyGenerate;
}

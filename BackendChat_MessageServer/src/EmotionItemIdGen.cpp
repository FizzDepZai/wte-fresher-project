/* 
 * File:   EmotionItemIdGen.cpp
 * Author: root
 * 
 * Created on December 31, 2013, 12:46 PM
 */

#include <stddef.h>

#include "ini/EmotionItemIdGen.h"
EmotionItemIdGen* EmotionItemIdGen::keyGenerate = NULL;
int EmotionItemIdGen::theLastGenerate = 1;
EmotionItemIdGen::EmotionItemIdGen(int theLast) {
    EmotionItemIdGen::theLastGenerate = theLast+1;
}

int EmotionItemIdGen::getKey(){
    return theLastGenerate++;
}
EmotionItemIdGen::~EmotionItemIdGen() {
}
EmotionItemIdGen* EmotionItemIdGen::createInstance(int theLast){
    if(!EmotionItemIdGen::keyGenerate){
        keyGenerate = new EmotionItemIdGen(theLast);
    }
    return keyGenerate;
}

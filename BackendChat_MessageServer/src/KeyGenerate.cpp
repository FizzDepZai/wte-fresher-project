/* 
 * File:   KeyGenerate.cpp
 * Author: Thanhpv
 * 
 * Created on December 21, 2013, 5:09 AM
 */

#include <stddef.h>

#include "ini/KeyGenerate.h"
KeyGenerate* KeyGenerate::keyGenerate = NULL;
int KeyGenerate::theLastGenerate = 1;
KeyGenerate::KeyGenerate(int theLast) {
    KeyGenerate::theLastGenerate = theLast+1;
}

KeyGenerate::~KeyGenerate() {
}

int KeyGenerate::getKey(){
    return theLastGenerate++;
}

KeyGenerate* KeyGenerate::createInstance(int theLast){
    if(!KeyGenerate::keyGenerate){
        keyGenerate = new KeyGenerate(theLast);
    }
    return keyGenerate;
}
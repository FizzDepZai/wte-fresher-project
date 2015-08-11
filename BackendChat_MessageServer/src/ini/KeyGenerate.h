/* 
 * File:   KeyGenerate.h
 * Author: Thanhpv
 *
 * Created on December 21, 2013, 5:09 AM
 */

#ifndef KEYGENERATE_H
#define	KEYGENERATE_H

class KeyGenerate {
public:
    //before keygen, must get the last gen number
    static KeyGenerate* createInstance(int theLast);
    int getKey();
    virtual ~KeyGenerate();
private:
    static KeyGenerate* keyGenerate;
    static int theLastGenerate;
    KeyGenerate(int theLast);
};

#endif	/* KEYGENERATE_H */


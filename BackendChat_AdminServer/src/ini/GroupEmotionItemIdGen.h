/* 
 * File:   GroupEmotionItemIdGen.h
 * Author: root
 *
 * Created on January 7, 2014, 11:21 PM
 */

#ifndef GROUPEMOTIONITEMIDGEN_H
#define	GROUPEMOTIONITEMIDGEN_H

class GroupEmotionItemIdGen {
public:
    static GroupEmotionItemIdGen* createInstance(int theLast);
    int getKey();
    virtual ~GroupEmotionItemIdGen();
private:
    static GroupEmotionItemIdGen* keyGenerate;
    static int theLastGenerate;
    GroupEmotionItemIdGen(int theLast);
};


#endif	/* GROUPEMOTIONITEMIDGEN_H */


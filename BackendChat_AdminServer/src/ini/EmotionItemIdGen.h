/* 
 * File:   EmotionItemIdGen.h
 * Author: root
 *
 * Created on December 31, 2013, 12:46 PM
 */

#ifndef EMOTIONITEMIDGEN_H
#define	EMOTIONITEMIDGEN_H

class EmotionItemIdGen {
public:
    static EmotionItemIdGen* createInstance(int theLast);
    int getKey();
    virtual ~EmotionItemIdGen();
private:
    static EmotionItemIdGen* keyGenerate;
    static int theLastGenerate;
    EmotionItemIdGen(int theLast);
};

#endif	/* EMOTIONITEMIDGEN_H */

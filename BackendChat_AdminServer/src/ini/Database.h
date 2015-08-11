/* 
 * File:   Database.h
 * Author: Thanhpv
 *
 * Created on December 18, 2013, 11:42 AM
 */

#ifndef DATABASE_H
#define	DATABASE_H
#include <stdio.h>
#include <iostream>
#include "LinkedListDatabase.h"
#include "DataStructure.h"
using namespace std;

class Database {
public:
    virtual ~Database();
    static Database* createInstance();
    void addDataStructure(string name);
    DataStructure* getDataStructure(string dbName);
    int getDatabaseCount();
private:
    static Database* database;
    LinkedList* dataList;
    Database();
};

#endif	/* DATABASE_H */


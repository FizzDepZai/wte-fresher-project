/* 
 * File:   Database.cpp
 * Author: Thanhpv
 * 
 * Created on December 18, 2013, 11:42 AM
 */

#include <stddef.h>

#include "ini/Database.h"
#include "ini/DataStructure.h"
#include "ini/LinkedListDatabase.h"

/*
 * Construct the dataList containing all DataStructures exists on disk
 */
Database* Database::database=NULL;
Database::Database() {
    Database::dataList = new LinkedList();
}

Database::~Database() {
    
}

/*
 * Singleton class
 */
Database* Database::createInstance() {
    if (!database) {
        database = new Database();
    }
    return database;
}

/*
 * Create a DataStructure Node and add to dataList
 * When the dataStructure is created its will granted CacheDB and HashDB
 */
void Database::addDataStructure(string name) {
    dataList->add(name);
}


/*
 * Get data from Disk of DataStructure has name dbName and write to its cacheDB
 */
DataStructure* Database::getDataStructure(string dbName) {
    DataStructure* dataStructure = this->dataList->getDataStructureNodeForUpdate(dbName); 
    return dataStructure;
}
/*
 * Get number of database
 */
int Database::getDatabaseCount(){
    return dataList->getSize();
}
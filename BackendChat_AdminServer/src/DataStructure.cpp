/* 
 * File:   DataStructure.cpp
 * Author: Thanhpv
 * 
 * Created on December 18, 2013, 11:41 AM
 */

#include "ini/DataStructure.h"
#include <iostream>
#include <stdio.h>
#include <string.h>
#include "ini/Config.h"
#include "ini/TestSinhVien.h"
#include "ini/LinkListId.h"
using namespace std;

/*
 * create a DataStructure with specific name
 * its CacheDB and HashDB will be created this time
 */
DataStructure::DataStructure(string name) {
    this->DataName = name;
    //Open database
    cachDB.open(name + "Mem.kch");

    //Configure cache memory database
    //Cache ten milions of record
    cachDB.tune_buckets(10LL * 1000 * 1000);
    cachDB.cap_count(10LL * 1000 * 1000);

    //Use less than 8GB of memory
    cachDB.cap_size(1LL << 30);

//        hashDB.open("dbFiles/" + name + "Disk.kch", HashDB::OWRITER | HashDB::OCREATE);//run tren  IDE
    hashDB.open("../../../dbFiles/" + name + "Disk.kch", HashDB::OWRITER | HashDB::OCREATE); //run tren terminal
}

DataStructure::~DataStructure() {
    cachDB.clear();
    cachDB.close();

    hashDB.close();
}

/*
 * This method save the record with key and value
 * if no record exist, create a new one
 * if one exist value will be overwritten
 */
int DataStructure::saveToCachDB(const char* key, const size_t& ksize, const char* value, const size_t& vsize) {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    bool saveResult = this->cachDB.set(key, ksize, value, vsize);
    if (saveResult) return DataStructure::SAVE_SUCCESS;
    else return DataStructure::SAVE_FAIL;
}

string DataStructure::getDataName() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    return this->DataName;
}

bool DataStructure::isExist(const char* key, const size_t& ksize) {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    return true;
}

/*
 * size is the size of value of key
 */
char* DataStructure::getValueFromKey(const char* key, const size_t& size, const size_t& vsize, unsigned int& vsizeGet) {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    //    cout << "IN GETVALUEFROMKEY + " + this->DataName << endl;
    char* value = (char*) malloc(vsize);
    int32_t fromCache = -1;

    if (cachDB.count() != 0) {
        fromCache = this->cachDB.get(key, size, value, vsize);
    }
    if (fromCache != -1) {
        //        cout << "the value gotten is in cache: " << &key << " value is: " << &value << " size:" << fromCache << endl;
        vsizeGet = fromCache;
    } else {
        int32_t fromHash = this->hashDB.get(key, size, value, vsize);
        if (fromHash > 0) {
            vsizeGet = fromHash;
        }
        //        cout << "the value gotten is in disk: " << fromHash << endl;
    }
    return value;
}

/*
 * read from HashDB and write to CacheDB
 */
int DataStructure::readFromDisk() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    //    cout << "IN READFROMDISK" << endl;
    DB::Cursor* cur = this->hashDB.cursor();
    cur->jump();
    char* ckey, *cvalue;
    size_t sizeOfValue = 10000;
    size_t sizeOfKey = 100;
    ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    if (!ckey)
        return DataStructure::READ_DISK_TO_MEM_FAIL;
    while (ckey) {
        bool writeToCache = cachDB.set(ckey, sizeOfKey, cvalue, sizeOfValue);
        if (!writeToCache)
            return DataStructure::WRITE_TO_CACHE_FAIL;
        ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
        if (!ckey)
            return DataStructure::READ_DISK_TO_MEM_FAIL;
    }
    ckey = NULL;
    cvalue = NULL;
    return DataStructure::READ_DISK_TO_MEM_SUCCESS;
    delete cur;
}

/*
 * write from CacheDB to HashDb
 * only write 10000 record a time
 * write all
 */
int DataStructure::writeToDisk() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    DB::Cursor* cur = this->cachDB.cursor();
    cur->jump();
    size_t sizeOfValue = 10000;
    size_t sizeOfKey = 100;
    char* ckey;
    char *cvalue;

    ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    if (!ckey)
        return DataStructure::READ_CACHE_FAIL;
    while (ckey) {

        bool writeResult = hashDB.set(ckey, sizeOfKey, cvalue, sizeOfValue);
        if (!writeResult)
            return DataStructure::WRITE_HASH_FAIL;
        ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    }
    ckey = NULL;
    cvalue = NULL;
    delete cur;
    //    cachDB.clear();
    //    cout<<"after save to hashDB: "<<this->DataName<<" has cachDB size: "<<cachDB.count()<<endl;
    //    cout<<"after save to hashDB: "<<this->DataName<<" has hash size: "<<hashDB.count()<<endl;
    return DataStructure::WRITE_HASH_SUCCESS;
}

/*
 *
 */
void DataStructure::readCache() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    //    cout << "IN READCACHE" << endl;
    DB::Cursor* cur = this->cachDB.cursor();
    cur->jump();
    char *ckey, *cvalue;
    size_t sizeOfValue = sizeof (cvalue);
    size_t sizeOfKey = sizeof (ckey);
    ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    while (ckey) {
        //        cout << "key is: " << ckey << " value is: " << cvalue << endl;
        ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    }
    delete cur;
}

/*
 *
 */
char* DataStructure::getValueFromKeyNo(const char *key, const size_t& sizeKey) {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    char* value;
    int32_t valueResult = cachDB.get(key, sizeKey, value, Config::buffSize);
    //    cout << "value of key " << key << " is: " << value << endl;
    return value;
}

//bool set(const char* kbuf, size_t ksiz, const char* vbuf, size_t vsiz) 
//char* get(const char* kbuf, size_t ksiz, size_t* sp) {
// }

void DataStructure::readDisk() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    //    cout << "IN READDISK" << endl;
    DB::Cursor* cur = this->hashDB.cursor();
    cur->jump();
    char *ckey, *cvalue;
    size_t sizeOfValue = sizeof (cvalue);
    size_t sizeOfKey = sizeof (ckey);
    ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    while (ckey) {
        //        cout << "key is: " << ckey << " value is: " << cvalue << endl;
        ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    }
    delete cur;
}

bool DataStructure::deleteRecord(const char* key, const size_t& ksize) {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    cachDB.remove(key, ksize);
    hashDB.remove(key, ksize);
}

int DataStructure::getHashCount() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    return hashDB.count();
}

int DataStructure::getCachCount() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    return cachDB.count();
}

string DataStructure::getAllDataGroupEmotionItem() {
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    string result = "{";
    //    cout << "IN READFROMDISK" << endl;
    DB::Cursor* cur = this->cachDB.cursor();
    cur->jump();
    char* ckey, *cvalue;
    size_t sizeOfValue = 10000;
    size_t sizeOfKey = 100;
    ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    if (!ckey)
        return "";
    while (ckey) {
        string key(ckey, sizeOfKey);
        string value(cvalue, sizeOfValue);
        result.append("\"").append(key).append("\"").append(": ").append("\"").append(value).append("\"").append(", ");
        ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    }
    result = result.substr(0, result.size() -2 );
    result.append("}");
    ckey = NULL;
    cvalue = NULL;
    delete cur;
    return result;
}
vector<string> DataStructure::getAllAdmins(){
    Poco::FastMutex::ScopedLock lock(DataStructure::_mutex);
    
    vector<string> returnRes;
    //    cout << "IN READFROMDISK" << endl;
    DB::Cursor* cur = this->hashDB.cursor();
    cur->jump();
    char* ckey, *cvalue;
    size_t sizeOfValue = 10000;
    size_t sizeOfKey = 100;
    ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);

    if (!ckey)
        return returnRes;
    while (ckey) {
        string key(ckey, sizeOfKey);
        string value(cvalue, sizeOfValue);
        string result;
        result.append("\"").append(key).append("\"").append(": ").append("\"").append(value).append("\"");
        returnRes.push_back(result);
        ckey = cur->get(&sizeOfKey, (const char**) &cvalue, &sizeOfValue, true);
    }
    ckey = NULL;
    cvalue = NULL;
    delete cur;
    return returnRes;
}
Poco::FastMutex DataStructure::_mutex;
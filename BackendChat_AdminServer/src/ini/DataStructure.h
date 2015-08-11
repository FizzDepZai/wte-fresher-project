/* 
 * File:   DataStructure.h
 * Author: Thanhpv
 *
 * Created on December 18, 2013, 11:41 AM
 */

#ifndef DATASTRUCTURE_H
#define	DATASTRUCTURE_H
#include <kccachedb.h>
#include <kchashdb.h>
#include "Poco/Mutex.h"
#include <Poco/NotificationQueue.h>
#include <Poco/Thread.h>
using namespace std;
using namespace kyotocabinet;
class DataStructure {
public:
    static Poco::FastMutex _mutex;
    enum Error {
        SAVE_SUCCESS = 1,
        SAVE_FAIL = 2,
        READ_DISK_TO_MEM_SUCCESS = 3,
        READ_DISK_TO_MEM_FAIL = 4,
        WRITE_TO_CACHE_SUCCESS = 5,
        WRITE_TO_CACHE_FAIL = 6, 
        READ_CACHE_FAIL =7,
        WRITE_HASH_FAIL = 8,
        WRITE_HASH_SUCCESS = 9
    };
    DataStructure(string name);
    virtual ~DataStructure();
    int saveToCachDB(const char* key, const size_t& ksize, const char* value, const size_t& vsize);
    string getDataName();
    bool isExist(const char* key, const size_t& ksize);
    char* getValueFromKey(const char* key, const size_t& size, const size_t& vsize, unsigned int& vsizeGet);
    bool deleteRecord(const char* key, const size_t& ksize);
    int readFromDisk();
    int writeToDisk();
    string getAllDataGroupEmotionItem();
    vector<string> getAllAdmins();
    /*
     * Methods for testing
     */
    void readCache();
    char* getValueFromKeyNo(const char* key, const size_t& size);
    void readDisk();
    int getHashCount();
    int getCachCount();
private:
    string DataName;
    CacheDB cachDB;
    HashDB hashDB;
};

#endif	/* DATASTRUCTURE_H */


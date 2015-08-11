///* 
// * File:   Test.cpp
// * Author: root
// *
// * Created on December 19, 2013, 10:58 AM
// */
//
//#include <cstdlib>
//#include "DataStructure.h"
//#include "LinkedListDatabase.h"
//#include "TestSinhVien.h"
//#include "KeyGenerate.h"
//#include "LinkListId.h"
//#include "Database.h"
//using namespace std;
//
///*
// * 
// */
//int main(int argc, char** argv) {
//    /*
//     * Test DataStructure 
//     */
//    //create db file in both memory and disk
//    //    DataStructure* testData = new DataStructure("test");
//    //cache has record
//    //test save and read from cachDB
//    //    string key, value;
//    //    for (int i = 0; i < 10; i++) {
//    //        stringstream ss;
//    //        ss << i;
//    //        string str = ss.str();
//    //        key = str + "X";
//    //        value = str + "Y";
//    //
//    //        char* ckey = (char*) key.c_str();
//    //        char* cvalue = (char*) value.c_str();
//    //        cout << "input key: " << ckey << " input value: " << cvalue << endl;
//    //        testData->saveToCachDB(ckey, cvalue);
//    //
//    //    }
//    //    testData->readCache();
//    //    cout << "---------------------------------------read + write Cache-------------------------------------" << endl;
//    //    //hash has record
//    //    testData->writeToDisk();
//    //    testData->readDisk();
//    //    cout << "---------------------------------------read + write Disk-------------------------------------" << endl;
//    //    //from hash to cache
//    //    testData->readFromDisk();
//    //    testData->readCache();
//    //    cout << "---------------------------------------read from disk and write to mem-------------------------------------" << endl;
//    //    string key  = "8X";
//    //    char* tmp = (char*)key.c_str();
//    //    cout << "---------------------------------------get value with key 8X-------------------------------------" << endl;
//    //    testData->getValueFromKeyNo(tmp, key.size());
//    //    
//
//
//
//
//
//
//    /*
//     * Test MessageIdDB
//     */
////        DataStructure* MessageIdDB = ll->getDataStructureNodeForUpdate("MessageId");
//    //    string strKey = "U1_U2_U3_U4";
//    //    char* key = (char*) strKey.c_str();
//    //    string strValue = "Hoc voi d";
//    //    //        char* value = (char*) strValue.c_str();
////                int fail = MessageIdDB->saveToCachDB(key, strKey.size(), value, strValue.size());
//    //    int x = 3298;
//    //    stringstream ss;
//    //    ss << x;
//    //    string strnum = ss.str();
//    //
//    //    char* val = (char*) strnum.c_str();
//    //    MessageIdDB->writeToDisk();
//    //    int fail = MessageIdDB->saveToCachDB(key, strKey.size(), val, sizeof (int));
//    //    //    cout<<"fail or not: "<<fail<<endl;
//    //            char* returnValue;
//    //            string strKey2 =  "U1_U2_U3_U4";
//    //            char* key2 = (char*) strKey2.c_str();
//    //            returnValue = MessageIdDB->getValueFromKey(key2, strKey2.size(), 100);
//    //            string xxxxxxxx = static_cast<string> ((returnValue));
//    //            cout<<"xxxxxxxx is: "<<std::atoi(xxxxxxxx.c_str())<<endl;
//    //    key = NULL;
//    //    //        value = NULL;
//    //            returnValue = NULL;
//    //            key2 = NULL;
//    //    delete MessageIdDB;
//    /*
//     * Test ChatRoomDB
//     */
//    Database* database = Database::createInstance();
//    if (database != NULL)
//        database->addDataStructure("MessageId");
//    database->addDataStructure("MessageItem");
//    database->addDataStructure("GroupEmotionItem");
//    database->addDataStructure("EmotionItem");
//    database->addDataStructure("RoomStatistic");
//    database->addDataStructure("EmotionStatistic");
//    database->addDataStructure("ChatRoom");
//    database->addDataStructure("GroupEmotion");
//    DataStructure* ChatRoomDB = database->getDataStructure("ChatRoom");
//    string strKey = "SaiGon";
//    char* key = (char*) strKey.c_str();
//    string strValue = "Hocvaohanh";
//    char* value = (char*) strValue.c_str();
//    int fail = ChatRoomDB->saveToCachDB(key, strKey.size(), value, strValue.size());
//    //    cout<<"fail or not: "<<fail<<endl;
//    char* returnValue;
//    string strKey2 = "SaiGon";
//    char* key2 = (char*) strKey2.c_str();
//    returnValue = ChatRoomDB->getValueFromKeyNo(key2, strKey2.size());
//    string xxxxxxxx = static_cast<string> ((returnValue));
//    cout << "xxxxxxxx is: " << xxxxxxxx << endl;
//    cout << "Value of returnValue: " << returnValue << endl;
//    /*
//     * Test with SinhVien Objects
//     */
//    //    SinhVien *sv = new SinhVien(2, "tript", "25/02", 1);
//    //    SinhVien *sv1 = new SinhVien(3, "thanhpv", "1/02", 2);
//    //    SinhVien *sv2 = new SinhVien(4, "biennv", "5/02", 2);
//    ////    dbSV.set((char*) &sv->id, sizeof (sv->id), (char*) sv, sizeof (SinhVien));
//    ////    dbSVdbSV.set((char*) &sv1->id, sizeof (sv1->id), (char*) sv1, sizeof (SinhVien));
//    ////    dbSV.set((char*) &sv2->id, sizeof (sv2->id), (char*) sv2, sizeof (SinhVien));
//    //    DataStructure* ChatRoom = ll->getDataStructureNodeForUpdate("ChatRoom");
//    //    ChatRoom->saveToCachDB((char*)sv, sizeof(sv), (char*)sv, sizeof(SinhVien));
//    //    ChatRoom->writeToDisk();
//    ////    cout<<"fail or not: "<<fail<<endl;
//    //    char* returnValue;
//    //    SinhVien* sv_ = new SinhVien(2, "tript", "25/02", 1);
//    //    size_t ksize = sizeof(sv_);
//    //    size_t vsize = sizeof(SinhVien);
//    //    returnValue = ChatRoom->getValueFromKey((char*)sv_, ksize, vsize);
//    //    SinhVien * tttt = static_cast<SinhVien *> (static_cast<void*> (returnValue));
//    //    if(tttt) cout<<"xxxxxxxx is: "<<tttt->name<<endl;
//    //    delete ChatRoom;
//    /*
//     * Test Key gen;
//     */
//    
//
//    /*
//     * Test Serialize LinkList of Int
//     */
//    //        LinkList* lkl = new LinkList();
//    //        for(int i=0; i<10000; i++){
//    //            lkl->Add(i);
//    //        }
//    //        lkl->Add(2);
//    //        lkl->Add(3);
//    //        lkl->Add(4);
//    //    //    DataStructure* MessageId = ll->getDataStructureNodeForUpdate("MessageId");
//    //        string strKey =  "U1_U2";
//    //        char* key = (char*) strKey.c_str();
//    //    //    
//    //        char* value = (char*) lkl;
//    //    int fail = MessageId->saveToCachDB(key, strKey.size(), value, sizeof(LinkList));
//    ////    cout<<"fail or not: "<<fail<<endl;
//    //    char* returnValue;
//    //    string strKey2 =  "SaiGon";
//    //    char* key2 = (char*) strKey2.c_str();
//    //    returnValue = MessageId->getValueFromKey(key2, strKey2.size(), sizeof(LinkList));
//    //    LinkList* xxxxxxxx = static_cast<LinkList*> (static_cast<void*>(returnValue));
//    //    
//    //    cout<<"xxxxxxxx is: "<<xxxxxxxx->Top()<<endl;
//    //    cout<<"xxxxxxxx is: "<<xxxxxxxx->Tail()<<endl;
//    //    cout<<"xxxxxxxx is: "<<xxxxxxxx->Count()<<endl;
//    //    cout<<"Value of returnValue: " <<returnValue<<endl;
//    /*
//     * Test Database
//     */
////    LinkList* lkl = new LinkList();
////    for (int i = 0; i < 10000; i++) {
////        lkl->Add(i);
////    }
////    //    DataStructure* MessageId = ll->getDataStructureNodeForUpdate("MessageId");
////    string strKey = "U1_U2";
////    char* key = (char*) strKey.c_str();
////    //    
////    char* value = (char*) lkl;
//
////    cout << database->getDatabaseCount() << endl;
////    DataStructure* MessageIdDB = database->getDataStructure("MessageId1");
////    MessageIdDB->saveToCachDB(key, strKey.size(), value, sizeof (LinkList));
////    char* returnVal = MessageIdDB->getValueFromKey(key, strKey.size(), sizeof (LinkList));
////    LinkList* ll = static_cast<LinkList*>(static_cast<void*>(returnVal));
////    cout<<"ll size is: "<<ll->Count()<<endl;
////    return 0;
//}
//
////#include "Poco/Thread.h"
////#include "Poco/Runnable.h"
////#include <iostream>
////class HelloRunnable: public Poco::Runnable
////{
////virtual void run()
////{
////std::cout << "Hello, world!" << std::endl;
////}
////};
////int main(int argc, char** argv)
////{
////HelloRunnable runnable;
////Poco::Thread thread;
////thread.start(runnable);
////thread.join();
////}
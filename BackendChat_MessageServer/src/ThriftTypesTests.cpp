//#include <string>
//#include <boost/shared_ptr.hpp>
//#include <thrift/transport/TBufferTransports.h>
//#include <thrift/protocol/TBinaryProtocol.h>
//#include "ini/thrift/chatProject_types.h"
//#include <iostream>
//#include <stdio.h>
//#include "ini/DataStructure.h"
//#include "MySerializer.hpp"
//#include "ini/thrift/chatProject_types.h"
//using namespace apache::thrift::transport;
//using namespace ::ChatProject;
//using namespace std;
//typedef ::apache::thrift::transport::TMemoryBuffer MemBufType;
//typedef boost::shared_ptr<MemBufType> MemBufPtr;
//
//int main() {
//
//
//    MsgItem *msgItem = new MsgItem();
//    msgItem->__set_msgId(1);
//    msgItem->__set_sendingUserId("Ha");
//    msgItem->__set_receiveUserId("pdo");
//    msgItem->__set_content("ldldl");
//    msgItem->__set_time("ieieie");
//    
//    std::string buffer;
//    cout<<"num write: "<<msgItem->writeSerialize(buffer)<<endl;
//    cout<<buffer.data()<<endl;
//    
//    string bufferDes(buffer.data(), buffer.size());
//    cout<<"bufferDes data is: "<<bufferDes<<endl;
//    
//    
//    DataStructure* MsgDB = new DataStructure("ssss");
//    
//    MsgItem * msgItemDes = new MsgItem();
//    cout<<"num read: "<<msgItemDes->readSerialize(bufferDes)<<endl;
//    cout << "value" << msgItemDes->msgId<<endl;
//    cout << "online" << msgItemDes->sendingUserId<<endl;    
//   
//
//    
//
//
//}

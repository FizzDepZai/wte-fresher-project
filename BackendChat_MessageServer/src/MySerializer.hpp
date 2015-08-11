/* 
 * File:   TSerializer.h
 * Author: namnq
 *
 * Created on February 7, 2011, 9:46 AM
 */

#ifndef TSERIALIZER_H
#define    TSERIALIZER_H

#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/transport/TBufferTransports.h>
#include <exception>
using namespace std;
template <class _Type, class _ProtocolType = ::apache::thrift::protocol::TBinaryProtocol>
class TSerializer
///Serializer for Thrift data type
{
public:
    typedef _Type Type;
    typedef _ProtocolType ProtocolType;
    typedef ::apache::thrift::transport::TMemoryBuffer MemBufType;
    typedef boost::shared_ptr<MemBufType> MemBufPtr;

    TSerializer() {
    }

    TSerializer(MemBufPtr memBuf_) : _memBuf(memBuf_) {
    }

    TSerializer(const void *buffer_, size_t length_, MemBufType::MemoryPolicy memPolicy_)
    : _memBuf(new MemBufType(reinterpret_cast<uint8_t*> (const_cast<void*> (buffer_)), length_, memPolicy_)) {

    }
     TSerializer(const void *buffer_, size_t length_)
    : _memBuf(new MemBufType(reinterpret_cast<uint8_t*> (const_cast<void*> (buffer_)), length_)) {

    }

     size_t serialize(const Type &value) {
        if (_memBuf.get() == 0)
            _memBuf = MemBufPtr(new MemBufType());
        try {
            ProtocolType prot(_memBuf);
            size_t returnNumBytes = value.write(&prot);
            return returnNumBytes;
        } catch (exception& e) {
            cout << e.what();
            return 0;
        }
    }
    size_t deserialize(Type &value) {
        if (_memBuf.get() == 0)
            return 0;

        try {

            ProtocolType prot(_memBuf);
            return value.read(&prot);

        } catch (exception& e) {
            cout << e.what();

            return 0;
        }
    }

    void* getBuffer() const {
        if (_memBuf.get() == 0)
            return 0;
        uint8_t *ptr = 0;
        uint32_t sz = 0;
        _memBuf->getBuffer(&ptr, &sz);
        return ptr;
    }

    std::string getBufferAsString() {
        if (_memBuf.get() == 0)
            return "";
        return _memBuf->getBufferAsString();
    }

    size_t getLength() const {
        if (_memBuf.get() == 0)
            return 0;
        uint8_t *ptr = 0;
        uint32_t sz = 0;
        _memBuf->getBuffer(&ptr, &sz);
        return sz;
    }

    void resetBuffer() {
        if (_memBuf.get() != 0)
            _memBuf->resetBuffer();
    }
     
public:
    MemBufPtr _memBuf;

};

#endif    /* TSERIALIZER_H */
//using
//
//
//TSerializer<ZBFAppInfo_Value> serializer(vbuf, vsiz);
//ZBFAppInfo_Value val;
//serializer.deserialize(val);

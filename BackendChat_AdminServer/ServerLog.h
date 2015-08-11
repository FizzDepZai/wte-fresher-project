/* 
 * File:   ServerLog.h
 * Author: root
 *
 * Created on January 8, 2014, 8:20 PM
 */

#ifndef SERVERLOG_H
#define	SERVERLOG_H
#include "Poco/Logger.h"
#include "Poco/FileChannel.h"
#include "Poco/AutoPtr.h"
using Poco::Logger;
using Poco::FileChannel;
using Poco::AutoPtr;
class ServerLog {
public:
    enum LOG_TYPE{
        ERROR,
        ACTIVITY,
        SERVICE, 
        DATA_MSGID,
        DATA_MSGITEM,
        DATA_MSGOFFLINE,
        WORKER_WRITEDB
    };
    ServerLog();
    ServerLog(const ServerLog& orig);
    virtual ~ServerLog();
    /*
     * return logger for error
     */
    static Logger& getLoggerForError(LOG_TYPE logtype);
    /*
     * return logger for activity
     */
    static Logger& getLoggerForActivity(LOG_TYPE logtype);
    /*
     * return logger for data
     */
    static Logger& getLoggerForData(LOG_TYPE logtype);
private:
    static Logger& getLogger(LOG_TYPE logtype);
};

#endif	/* SERVERLOG_H */


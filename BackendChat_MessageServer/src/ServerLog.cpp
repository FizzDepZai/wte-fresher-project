/* 
 * File:   ServerLog.cpp
 * Author: root
 * 
 * Created on January 8, 2014, 8:20 PM
 */

#include "ini/ServerLog.h"

ServerLog::ServerLog() {
}

ServerLog::ServerLog(const ServerLog& orig) {
}

ServerLog::~ServerLog() {
}

Logger& ServerLog::getLogger(LOG_TYPE logtype) {
    switch (logtype) {
        case ACTIVITY:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/ActivityLog/activity.log");
            pChannel->setProperty("path", "../../../PocoLog/ActivityLog/activity.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("ActivityLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
        case SERVICE:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/Service/service.log");
            pChannel->setProperty("path", "../../../PocoLog/Service/service.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("ActivityLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
        case DATA_MSGID:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/DataLog/save_msgId.log");
            pChannel->setProperty("path", "../../../PocoLog/DataLog/save_msgId.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("DataLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
        case DATA_MSGITEM:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/DataLog/save_msgItem.log");
            pChannel->setProperty("path", "../../../PocoLog/DataLog/save_msgItem.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("DataLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
        case DATA_MSGOFFLINE:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/DataLog/save_msgOffline.log");
            pChannel->setProperty("path", "../../../PocoLog/DataLog/save_msgOffline.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("DataLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
        case WORKER_WRITEDB:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/WorkerLog/writeDB.log");
            pChannel->setProperty("path", "../../../PocoLog/WorkerLog/writeDB.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("DataLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
        case ERROR:
        {
            AutoPtr<FileChannel> pChannel(new FileChannel);
//            pChannel->setProperty("path", "PocoLog/ErrorLog/error.log");
            pChannel->setProperty("path", "../../../PocoLog/ErrorLog/error.log");
            pChannel->setProperty("rotation", "200K");
            pChannel->setProperty("archive", "timestamp");
            Logger::root().setChannel(pChannel);
            Logger& logger = Logger::get("ErrorLogger"); // inherits root channel
            logger.setChannel(pChannel);
            return logger;
        }
    }
}

Logger& ServerLog::getLoggerForActivity(LOG_TYPE logtype) {
    getLogger(logtype);
}

Logger& ServerLog::getLoggerForData(LOG_TYPE logtype) {
    getLogger(logtype);
}

Logger& ServerLog::getLoggerForError(LOG_TYPE logtype) {
    getLogger(logtype);
}
/* 
 * File:   ServerManagement.h
 * Author: Thanhpv
 *
 * Created on January 7, 2014, 12:23 PM
 */

#ifndef SERVERMANAGEMENT_H
#define	SERVERMANAGEMENT_H
#include "BackEndServer.h"
#include <Poco/Util/ServerApplication.h>
#include <Poco/Util/OptionSet.h>
#include <Poco/Util/Option.h>
#include <Poco/Util/OptionCallback.h>
#include <Poco/Util/HelpFormatter.h>

using Poco::Util::ServerApplication;
using Poco::Util::OptionSet;
using Poco::Util::Option;
using Poco::Util::OptionCallback;
using Poco::Util::HelpFormatter;
class ServerManagement : public ServerApplication {
public:
    ServerManagement();
    virtual ~ServerManagement();
    int main(const std::vector<std::string>& args);
    void initialize(Application& self);
    void reinitialize(Application& self);
    void uninitialize();
    void defineOptions(OptionSet& options);
    void handleOption(const std::string& name, const std::string& value);
private:
    BackEndServer* messageServer;
    void displayHelp();
    void handlePortConfig(const std::string& name, const std::string& value);
    void handleThreadConfig(const std::string& name, const std::string& value);
    void handleStartServer();
    void handleResetServer();
    void stopServer();
    
};

#endif	/* SERVERMANAGEMENT_H */


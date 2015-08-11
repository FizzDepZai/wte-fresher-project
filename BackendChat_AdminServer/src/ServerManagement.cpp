/* 
 * File:   ServerManagement.cpp
 * Author: Thanhpv
 * 
 * Created on January 7, 2014, 12:23 PM
 */

#include "ini/ServerManagement.h"

using namespace std;

ServerManagement::ServerManagement() {

}

ServerManagement::~ServerManagement() {
}

int ServerManagement::main(const std::vector<std::string>& args) {
    terminate();
    return 0;
}

void ServerManagement::initialize(Application& self) {
    loadConfiguration(); // load default configuration files
    ServerApplication::initialize(self);
}

void ServerManagement::reinitialize(Application& self) {
    ServerApplication::reinitialize(self);
}

void ServerManagement::uninitialize() {
    ServerApplication::uninitialize();
}

void ServerManagement::defineOptions(OptionSet& options) {
    ServerApplication::defineOptions(options);
    options.addOption(Option("help", "h", "display help information")
            .required(false)
            .repeatable(false));
    options.addOption(
            Option("port", "p", "set the port of server")
            .required(false)
            .repeatable(false)
            .argument("port"));
    options.addOption(
            Option("totalThread", "tT", "set the total thread of workpool")
            .required(false)
            .repeatable(false)
            .argument("total"));
    options.addOption(
            Option("start", "s", "start the server")
            .required(false)
            .repeatable(false));
    options.addOption(
            Option("reset", "rs", "reset port and total thread the server")
            .required(false)
            .repeatable(false));
    Option().callback(OptionCallback<ServerManagement> (this, &ServerManagement::handleOption));
}

void ServerManagement::handleOption(const std::string& name, const std::string& value) {
    if (name == "help") {//tuc la nhung chuoi <= help tat ca ve ko viet hoa
        displayHelp();
        stopOptionsProcessing();
    } else if (name == "port") {
        cout << "in handle config set port" << endl;
        handlePortConfig(name, value);
    } else if (name == "totalThread") {
        cout << "in handle config set total thread" << endl;
        handleThreadConfig(name, value);
    } else if (name == "start") {
        cout << "start server" << endl;
        handleStartServer();
    } else if (name == "reset") {
        cout << "reset server" <<endl;
        handleResetServer();
    }
}

void ServerManagement::handleStartServer() {
    adminServer = BackEndServer::createBackEndServer();
    Poco::Thread runServerThread;
    runServerThread.start(*adminServer);
    waitForTerminationRequest();
    printf("Stopping server....\n");
    //stop BackEndServer
    adminServer->stop();
    //reset config file
    stopServer();
}

void ServerManagement::displayHelp() {
    HelpFormatter helpFormatter(options());
    helpFormatter.setCommand(commandName());
    helpFormatter.setUsage("OPTIONS_TMP");
    helpFormatter.setHeader("MyServerApp Options:");
    helpFormatter.format(std::cout); //in ra man hinh nhung cai o tren
}

void ServerManagement::handlePortConfig(const std::string& name, const std::string& value) {
    std::ifstream iportConfig;
    //    threadConfig.open("properties/BackEndSerVerProperties.properties", fstream::app); //nam trong thu muc chua file thuc thi
    iportConfig.open("../../../properties/BackEndSerVerProperties.properties"); //run tren terminal
    
    if (!iportConfig.is_open()) {
        cout << "fail to open" << endl;
    }
    /*
     * 
     */
    string port = "serverPort: ";
    string str;
    vector<string> arrayL;
    while ( getline(iportConfig, str)){
        arrayL.push_back(str);
    }
    bool exist = false;
    for(int i=3; i<5 && i<arrayL.size(); i++){
        if(arrayL[i].find("serverPort", 0) == 0){//da co
            arrayL[i] = port.append(value);
            exist = true;
            break;
        }
    }
    if(!exist){
        arrayL.push_back(port.append(value));
    }
    iportConfig.close();
    /*
     *
     */
    std::ofstream oportConfig;
    remove("../../../properties/BackEndSerVerProperties.properties");
    oportConfig.open("../../../properties/BackEndSerVerProperties.properties");
    for(int i=0; i<arrayL.size(); i++){
        oportConfig << arrayL[i] <<endl;
    }
    oportConfig.close();
}

void ServerManagement::handleThreadConfig(const std::string& name, const std::string& value) {
    printf("handling Thread Config\n");
    std::ifstream ithreadConfig;
    //    threadConfig.open("properties/BackEndSerVerProperties.properties", fstream::app); //nam trong thu muc chua file thuc thi
    ithreadConfig.open("../../../properties/BackEndSerVerProperties.properties"); //run tren terminal
    
    if (!ithreadConfig.is_open()) {
        cout << "fail to open" << endl;
    }
    /*
     * 
     */
    string totalThread = "TotalThread: ";
    string str;
    vector<string> arrayL;
    while (getline(ithreadConfig, str)){
        arrayL.push_back(str);
    }
    bool exist = false;
    for(int i=3; i<5 && i<arrayL.size(); i++){
        if(arrayL[i].find("TotalThread", 0) == 0){//da co
            arrayL[i] = totalThread.append(value);
            exist = true;
            break;
        }
    }
    if(!exist){
        arrayL.push_back(totalThread.append(value));
    }
    ithreadConfig.close();
    /*
     *
     */
    std::ofstream othreadConfig;
    remove("../../../properties/BackEndSerVerProperties.properties");
    othreadConfig.open("../../../properties/BackEndSerVerProperties.properties");
    for(int i=0; i<arrayL.size(); i++){
        othreadConfig << arrayL[i] <<endl;
    }
    othreadConfig.close();
}

//xoa 2 cai config, chi giu lai default

void ServerManagement::stopServer() {
    handleResetServer();
}
void ServerManagement::handleResetServer(){
    printf("reconfig properties file....\n");
    std::ofstream resetConfig;
    //    remove("properties/BackEndSerVerProperties.properties");
    remove("../../../properties/BackEndSerVerProperties.properties"); //run tren terminal
    //    resetConfig.open("properties/BackEndSerVerProperties.properties");
    resetConfig.open("../../../properties/BackEndSerVerProperties.properties"); //run tren terminal
    resetConfig << "serverMessagePort: 9091" << endl;
    resetConfig << "serverAdminPort: 8989" << endl;
    resetConfig << "workPoolTotalThread: 240" << endl;
    resetConfig.close();
}
POCO_SERVER_MAIN(ServerManagement);
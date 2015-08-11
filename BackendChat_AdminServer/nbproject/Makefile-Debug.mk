#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/src/BackEndServer.o \
	${OBJECTDIR}/src/ChatProjectMain.o \
	${OBJECTDIR}/src/DataStructure.o \
	${OBJECTDIR}/src/Database.o \
	${OBJECTDIR}/src/EmotionItemIdGen.o \
	${OBJECTDIR}/src/JobWorker.o \
	${OBJECTDIR}/src/KeyGenerate.o \
	${OBJECTDIR}/src/LinkListId.o \
	${OBJECTDIR}/src/LinkedListDatabase.o \
	${OBJECTDIR}/src/LinkedListJobWorker.o \
	${OBJECTDIR}/src/LinkedListWriteToDBWorker.o \
	${OBJECTDIR}/src/ServerLog.o \
	${OBJECTDIR}/src/ServerManagement.o \
	${OBJECTDIR}/src/Test.o \
	${OBJECTDIR}/src/TestSinhVien.o \
	${OBJECTDIR}/src/ThriftTypesTests.o \
	${OBJECTDIR}/src/WorkNotification.o \
	${OBJECTDIR}/src/Workers.o \
	${OBJECTDIR}/src/WriteToDBWorker.o \
	${OBJECTDIR}/src/WriteToDBWorkers.o \
	${OBJECTDIR}/src/ini/GroupEmotionItemIdGen.o \
	${OBJECTDIR}/src/ini/thrift/ChatProject.o \
	${OBJECTDIR}/src/ini/thrift/chatProject_constants.o \
	${OBJECTDIR}/src/ini/thrift/chatProject_types.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=-LKClib -lthrift -lthriftz -lkyotocabinet -lpthread -lthriftnb `pkg-config --libs libevent` `pkg-config --libs libevent_pthreads` -dynamic `pkg-config --libs zlib` -lz -lstdc++ -lrt -lm -lc -lPocoFoundation -lPocoFoundationd -lPocoUtil -lPocoUtild -std=gnu++11 -std=c++11 jsoncpplibs/libjson_linux-gcc-4.8_libmt.a jsoncpplibs/libjson_linux-gcc-4.8_libmt.so  

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/backendchat_adminserver

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/backendchat_adminserver: jsoncpplibs/libjson_linux-gcc-4.8_libmt.a

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/backendchat_adminserver: jsoncpplibs/libjson_linux-gcc-4.8_libmt.so

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/backendchat_adminserver: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/backendchat_adminserver ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/src/BackEndServer.o: src/BackEndServer.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/BackEndServer.o src/BackEndServer.cpp

${OBJECTDIR}/src/ChatProjectMain.o: src/ChatProjectMain.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ChatProjectMain.o src/ChatProjectMain.cpp

${OBJECTDIR}/src/DataStructure.o: src/DataStructure.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/DataStructure.o src/DataStructure.cpp

${OBJECTDIR}/src/Database.o: src/Database.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/Database.o src/Database.cpp

${OBJECTDIR}/src/EmotionItemIdGen.o: src/EmotionItemIdGen.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/EmotionItemIdGen.o src/EmotionItemIdGen.cpp

${OBJECTDIR}/src/JobWorker.o: src/JobWorker.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/JobWorker.o src/JobWorker.cpp

${OBJECTDIR}/src/KeyGenerate.o: src/KeyGenerate.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/KeyGenerate.o src/KeyGenerate.cpp

${OBJECTDIR}/src/LinkListId.o: src/LinkListId.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/LinkListId.o src/LinkListId.cpp

${OBJECTDIR}/src/LinkedListDatabase.o: src/LinkedListDatabase.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/LinkedListDatabase.o src/LinkedListDatabase.cpp

${OBJECTDIR}/src/LinkedListJobWorker.o: src/LinkedListJobWorker.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/LinkedListJobWorker.o src/LinkedListJobWorker.cpp

${OBJECTDIR}/src/LinkedListWriteToDBWorker.o: src/LinkedListWriteToDBWorker.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/LinkedListWriteToDBWorker.o src/LinkedListWriteToDBWorker.cpp

${OBJECTDIR}/src/ServerLog.o: src/ServerLog.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ServerLog.o src/ServerLog.cpp

${OBJECTDIR}/src/ServerManagement.o: src/ServerManagement.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ServerManagement.o src/ServerManagement.cpp

${OBJECTDIR}/src/Test.o: src/Test.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/Test.o src/Test.cpp

${OBJECTDIR}/src/TestSinhVien.o: src/TestSinhVien.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/TestSinhVien.o src/TestSinhVien.cpp

${OBJECTDIR}/src/ThriftTypesTests.o: src/ThriftTypesTests.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ThriftTypesTests.o src/ThriftTypesTests.cpp

${OBJECTDIR}/src/WorkNotification.o: src/WorkNotification.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/WorkNotification.o src/WorkNotification.cpp

${OBJECTDIR}/src/Workers.o: src/Workers.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/Workers.o src/Workers.cpp

${OBJECTDIR}/src/WriteToDBWorker.o: src/WriteToDBWorker.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/WriteToDBWorker.o src/WriteToDBWorker.cpp

${OBJECTDIR}/src/WriteToDBWorkers.o: src/WriteToDBWorkers.cpp 
	${MKDIR} -p ${OBJECTDIR}/src
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/WriteToDBWorkers.o src/WriteToDBWorkers.cpp

${OBJECTDIR}/src/ini/GroupEmotionItemIdGen.o: src/ini/GroupEmotionItemIdGen.cpp 
	${MKDIR} -p ${OBJECTDIR}/src/ini
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ini/GroupEmotionItemIdGen.o src/ini/GroupEmotionItemIdGen.cpp

${OBJECTDIR}/src/ini/thrift/ChatProject.o: src/ini/thrift/ChatProject.cpp 
	${MKDIR} -p ${OBJECTDIR}/src/ini/thrift
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ini/thrift/ChatProject.o src/ini/thrift/ChatProject.cpp

${OBJECTDIR}/src/ini/thrift/chatProject_constants.o: src/ini/thrift/chatProject_constants.cpp 
	${MKDIR} -p ${OBJECTDIR}/src/ini/thrift
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ini/thrift/chatProject_constants.o src/ini/thrift/chatProject_constants.cpp

${OBJECTDIR}/src/ini/thrift/chatProject_types.o: src/ini/thrift/chatProject_types.cpp 
	${MKDIR} -p ${OBJECTDIR}/src/ini/thrift
	${RM} "$@.d"
	$(COMPILE.cc) -g -D_GLIBCXX_CSIGNAL -IKClib -IThriftlib -IPocolib -Isrc/ini/jsoncpp `pkg-config --cflags libevent` `pkg-config --cflags libevent_pthreads` `pkg-config --cflags zlib` -std=c++98  -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/src/ini/thrift/chatProject_types.o src/ini/thrift/chatProject_types.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/backendchat_adminserver

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc

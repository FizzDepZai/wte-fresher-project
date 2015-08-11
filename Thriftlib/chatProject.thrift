/**
* CHAT PROJECT THRIFT
*/

namespace java ChatProject
namespace cpp  ChatProject


//--------STATUS CODE----------------

/**
* Status code return
*/
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
}

/**
* Room status
*/
enum RoomStatus {
	OPEN = 1,
	CLOSED = 2,
	FULL = 3
}

//---------------POPULAR STRUCT (ADMIN & USER)------------------


/**
* Room information
*/
struct RoomItem {
	1: string roomId,
	2: string roomName,
	3: RoomStatus roomStatusId,
	4: i32 UserOnlineNumber
}

/**
* User information in list: id, userName, path to avatar
*/
struct UserItem {
	1: string userId,
	2: string userName,
	3: string avatarPath
}

/**
* Emotion item information
*/
struct EmotionItem {
	1: i64 emotionId,
	2: string keyInput,
	3: string style,
	4: string src,
	5: string title,
	6: string groupId
}

/**
* Emotion group
*/
struct GroupEmotion {
	1: string groupId,
	2: string groupName,
	3: list<EmotionItem> emotionList
}

//------------------------ADMIN STRUCT--------------------

/**
* Emotion statistic by day(system time)
*/
struct EmotionStatistic {
	1: string emotionId,
	2: i32 date, 
	3: i32 usedCount
}

/**
* Room statistic by day(system time)
*/
struct RoomStatistic {
	1: string roomId,
	2: i32 date,
	3: i32 ViewCount
}

//-------------------USER STRUCT---------------------

/**
* Message Item
*/
struct MsgItem {
	1: i64 msgId,
	2: string sendingUserId,
	3: string receiveUserId,
	4: string content,
	5: string time,
	6: string type
}

/**
* Message Id struct
*/
struct MsgId{
	1: string msgId
}

/**
* List Msg Id
*/
struct MsgIdList{
	1: list<i64> msgIdList
}

/**
*
*/



struct MsgItemId{
	1: i64 msgItemId
}

/**
*
*/
struct GroupId{
	1: string groupId
}

/**
*
*/
struct EmotionId{
	1: i64 emotionId
}

/**
*
*/
struct RoomIdDate{
	1: string roomIdDate
}

/**
*
*/
struct EmotionIdDate{
	1: string emotionIdDate
}

/**
*
*/

struct RoomId{
	1: string roomId
}

/**
*
*/
struct ListEmotionId{
	1: list<i64> listEmotionId
}
/**
*
*/
struct ListFriendOfflineMsg{
	1: string userId,
	2: list<string> listFriendOfflineMsg
}

/**
* use for admin login
*/
struct UserName{
	1: string userName
}
/**
* use for admin login
*/
struct Password{
	1: string passWord
}
//------------------------------------------SERVICE-----------------------------------------------

service ChatProject {
	
	//-------------------------POPULAR SERVICE-------------------------
	/**
	* Get all chat room from database
	*/
	list<RoomItem> getAllChatRoom();

	/**
	* Get list online user in room
	*/
	list<UserItem> getListUserInRoom(1: string roomId);

	/**
	* Get all emotion groups
	*/
	list<GroupEmotion> getAllEmotionGroup();

	/**
	* Get an emotion group
	*/
	GroupEmotion getEmotionGroup(1: string emotionGroupId);


	//------------------------ADMIN SERVICE-----------------------------
	/**
	* Add new room
	*/
	void addRoom(1: RoomItem newRoom);

	/**
	* Edit room
	*/
	void editRoom(1: RoomItem room);

	/**
	* Delete room
	*/
	void deleteRoom(1: string roomId);

	/**
	* Add many emotions to an emotion group
	*/
	void addEmotion(1:string emotionItem);

	/**
	* Edit emotion
	*/
	void editEmotion(1: i64 emotionId, 2: EmotionItem emotionItem);

	/**
	* Delete emotion
	*/
	void deleteEmotion(1: i64 emotionId);

	/**
	* Kick user in room
	*/
	void kickUser(1: string userId);

	/**
	* Get room statistic by date
	*/
	RoomStatistic getRoomStatisticByDate(1: i32 date);

	/**
	* Get emotion statistic by date
	*/
	EmotionStatistic getEmotionStatisticByDate(1: i32 date);


	/**
	* delete all emotion in group 
	*/
	bool deleteAllEmotionInGroup(1:string groupEmotionId);

	/**
	* delete group emotion with groupEmotionId
	*/
	bool deleteGroupEmotion(1:string groupEmotionId);

	/**
	* add new group 
	* return groupId
	*/
	string addGroupEmotion(1:string groupEmotionName);


	/**
	* get all group emotion
	*/
	string gelAllGroupEmotion();


	/**
	* Load all emotion
	*/
	string loadAllEmotion();


	/**
	* get emotion with group id
	*/
	string getEmotionWithGroup(1:string groupId);


	/**
	* check image url exist in db
	*/
	bool checkImageEmotionExist(1:string imageStyle,2:string groupEmotionId);

	//------------------------------------CHAT SERVICE---------------------------------------------
	
	/**
	* Send message item from server connection to server chat
	*/
	bool sendMessageInternal(1:MsgItem msgItem,  2:bool messageOnline);

	/**
	* Save list message id to database (back-end)
	*/
	void saveListMsgId(1: list<i64> listMsgId);

	/**
	* Save offline message notify
	*/
	void saveOfflineMsgNotify(1: string userId);

	/**
	* Get message list between two users from position of current message (total message in front-end) (front-end - middleware)
	*/
	list<MsgItem> getMessageList(1: string userId1, 2: string userId2, 3: i32 totalMsg);

	/**
	* Get list of message id (limit) of two users (middleware-backend)
	*/
	list<i64> getMessageIdList(1: string userId1, 2: string userId2, 3: i32 totalMsg);

	/**
	* Get a message item between two users by message id (middleware - back-end)
	*/
	MsgItem getMessageItem(1: i64 MsgId);

	/**
	* Save room's messages
	*/
	void saveRoomMsg(1: MsgItem msg, 2: string roomId);

	/**
	* Get all room's messages
	*/
	list<MsgItem> getRoomMsg(1: string roomId);

	/**
	* Get all users in room
	*/
	list<UserItem> getUserInRoom(1: string roomId);

	/**
	* Get user profile
	*/
	UserItem getUserProfile(1: string userId);

	/**
	* Get friend list of an user
	*/
	list<UserItem> getFriendList(1: string userId);

	/**
	* Get new message id
	*/ 
	i64 getNewMsgId();

	/**
	* Notify user online
	*/
	void notifyUserOnline(1:string userId);

	/**
	* Notify list user offline
	*/
	void notifyListUserOffline(1:list<string> listUserIdOffline);
	
	/**
	* check friend online
	* return friend list with status online
	*/
	list<bool> checkFriendOnline(1:list<string> listFriendId);

	/**
	* Get friend list from zalo (string-JSON)
	*/
	string getFriendListZalo(1: string userId, 2: string oAuthCode);

	/**
	* Get profile of an user from zalo (string-JSON)
	*/
	string getProfileZalo(1: string userId, 2: string oAuthCode);

	/**
	* Check offline message 
	* return list friendId has offline message
	*/
	list<string> checkOfflineMessage(1:string userId);

	/**
	* Set login session
	*/
	void setLogin(1: string sessionId);

	/**
	* Check if user log in
	*/
	bool isLogin(1: string sessionId);

	/**
	* Set user logout
	*/
	void setLogout(1: string sessionId);
	/**
	* get image id
	*/
	i64 getImageId();
	/**
	* register admin
	*/
	void adminRegister(1: string userName, 2: string password);
	/**
	* check admin
	*/
	bool checkAdmin(1: string userName, 2: string password);
	/**
	* get all admin
	*/
	list<string> getAllAdmin();
}

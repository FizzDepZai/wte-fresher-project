var zav = {
    global: {
        token: "", //Token request api switchboard
        userInfo: new Object(),
        userActive: new Object(),
        contacts: new Object(),
        timer: null,
        firsrLoadContact: true,
        callStatus: "Free",
        sbDomain: "http://dev.voicecall.zapps.vn", //Switchboard Domain
        heightLoginDlg: 0, // height of login dialog
        widthLoginDlg: 0, // width of login dialog
        heightAppDlg: 0, // height of app dialog
        widthAppDlg: 0,  // width of app dialog
        widthRightContent: 0 //width of right content (left-bar: 48px, middle-bar: 275px)
    },
    util: {
        makeUnselectable: function (node) {
            if (node.nodeType == 1) {
                if (node.nodeName.toUpperCase() != "INPUT") {
                    node.setAttribute("unselectable", "on");
                }
            }
            var child = node.firstChild;
            while (child) {
                zav.util.makeUnselectable(child);
                child = child.nextSibling;
            }
        },
        checkLogin: function () {
            if (zav.global.userInfo.uid == null ||
                zav.global.userInfo.uid == "undefined" ||
                zav.global.userInfo.zvs == null ||
                zav.global.userInfo.zvs == "undefined" ||
                zav.global.token == null ||
                zav.global.token == "undefined") {
                return false;
            } else if (zav.global.userInfo.uid > 0) {
                return true;
            }
            return true;
        },
        initUserInfo: function () {
            //call mfc get session & uid
            //zav.global.userInfo.uid = zav.winExtApp.waGetUid();
            //zav.global.userInfo.zvs = zav.winExtApp.waGetSession();   
            zav.global.userInfo.uid = 67773282;
            zav.global.userInfo.zvs = "3sPY.67773282.128.b7tC-HpdAPOSvocMViGZadx48v9-n2EIVrc7B2ox8v8"
            zav.global.token = "toktest";
            zav.global.userInfo.avatarUrl = "http://s240.avatar.talk.zdn.vn/d/c/9/b/1/240/b6c02f9e674d0ac8258d77a87ad7c309.jpg"
        },
        isNull: function (obj) {
            if ((typeof obj !== "undefined") &&
                (obj !== null)) {
                return false;
            }
            return true;
        }
    },
    switchboard: {
        requestCode: "http://dev.voicecall.zapps.vn/requestcode",
        loginViaPhone: "http://dev.voicecall.zapps.vn/loginviaphone",
        contact: "http://dev.voicecall.zapps.vn/contact",
        callSession: "http://dev.voicecall.zapps.vn/getcallsession", //?zvs=xyz&calleeid=2
        messageNotify: {
            nullPhoneNumber: "Please enter your phone number",
            wrongPhoneNumber: "The number you entered doesn't appear to be valid. Please check the number and try again",
            networkError: "An Internet connection is required to activate your Zalo account. Check your connection and try again.",
            connecting: "Connecting to Zalo ...",
            emptyToken: "Unauthorized access, please restart zalo application.",
            invalidCode: "Your code is invalid, please try again."
        },
        nameBtn: {
            tryBtn: "Try Again",
            nullBtn: ""
        }
    },
    callStatus: {
        CALLING: "Calling...",
        RINGING: "Ringing...",
        INTALK: "00:00:00",
        ENDING: "Ending...",
        REJECTED: "No Answer!",
        CANCELED: "Call Cancelled!",
        ENDED: "Ended call!",
        BUSY: "Busy",
        FREE: "Free"
    }
};
/* @JAVASCRIPT windowExternalWrapper */
(function (a, b) {
    //api call mfc zalo application common
    a.winExt = {
        waSetWindowSize: function (width, height) {
            //return b.external.waSetWindowSize(width, height);
            return 1;
        },
        waShowWindow: function (show) {
            //return b.external.waShowWindow(show);
            return 1;
        },
        waQuitApplication: function () {
            //return b.external.waQuitApplication();
            return 1;
        },
        waSetWindowTitle: function (title) {
            //return b.external.waSetWindowTitle(title);
            return 1;
        },
        waMinimizeToTray: function () {
            //return b.external.waMinimizeToTray();
            return 1;
        },
        waShowBalloon: function (c, e, d) {
            //return b.external.waShowBalloon(c, e, d);
            return 1;
        },
        waOnDocumentReady: function () {
            //return b.external.waOnDocumentReady();
            return 1;
        }
    };

    //api call mfc login dialog
    a.winExtLogin = {
        waOnCancel: function () {
            //return b.external.waOnCancel();
            return 1;
        },
        waOnLoginSuccess: function (uid, session) {
            //return b.external.waOnLoginSuccess(uid, session);
            return 1;
        }
    }

    //ap call mfc application dialog

    a.winExtApp = {
        waGetSession: function(){
            //return b.external.waGetSession();
            return 1;
        },
        waGetUid: function () {
            //return b.external.waGetUid();
            return 1;
        },
        //For flow call
        waMakingVideoCall: function (uid, callSession) {
            //return b.external.waMakingVideoCall(uid, callSession);
            return 1;
        },
        waAnsweringCall: function () {
            //return b.external.waAnsweringCall();
            return 1;
        },
        waDecliningCall: function () {
            //return b.external.waDecliningCall();
            return 1;

        },
        waEndingCall: function () {
            //return b.external.waEndingCall();
            return 1;
        }
    }
} (zav, window));

function onResizedWindow() {
    zav.screenApp.resetAppUI();
}
//api for call mfc (for video call) 
function onConntected() {
    zav.conversation.appendData("onConntected");
    zav.appStatus.hide();
}
function onOffline() {
    zav.conversation.appendData("onOffline");
    zav.appStatus.offline();
}
function onConnecting() {
    zav.conversation.appendData("onConnecting");
    zav.appStatus.connecting();
}
function onIncomingCall(userId) {
    zav.conversation.appendData("onIncomingCall:" + userId);
    return zav.screenCalling.onIncomingCall(userId);
};
function onRingingCall() {
    zav.conversation.appendData("onRingingCall");
    return zav.screenCalling.onRingingCall();
};
function onIntalkedCall() {
    zav.conversation.appendData("onIntalkedCall");
    return zav.screenCalling.onIntalkedCall();
};
function onRejectedCall() {
    zav.conversation.appendData("onRejectedCall");
    return zav.screenCalling.onRejectedCall();
};
function onBusyedCall() {
    zav.conversation.appendData("onBusyedCall");
    return zav.screenCalling.onBusyedCall();
};

function onEndedCall() {
    zav.conversation.appendData("onEndedCall");
    return zav.screenCalling.onEndedCall();
};
(function (a) {
    a.appStatus = {
        nodeStatus: $("#contacts .app-status"),
        connecting: function () {
            a.appStatus.nodeStatus.removeClass("hidden");
            a.appStatus.nodeStatus.find(".name-status").html('<span class="status-img"><img alt="" src="./img/zalo.loading.gif" /></span><span class="status-text">Connecting</span>');
        },
        syncing: function () {
            a.appStatus.nodeStatus.removeClass("hidden");
            a.appStatus.nodeStatus.find(".name-status").html('<span class="status-img"><img alt="" src="./img/zalo.loading.gif" /></span><span class="status-text">Syncing</span>');
        },
        offline: function() {
            a.appStatus.nodeStatus.removeClass("hidden");
            a.appStatus.nodeStatus.find(".name-status").html("Offline");
        },
        custom: function(text) {
            a.appStatus.nodeStatus.removeClass("hidden");
            a.appStatus.nodeStatus.find(".name-status").html(text);
        },
        hide: function () {
            a.appStatus.nodeStatus.addClass("hidden");
        }
    };
} (zav));


(function (a) {
    a.boxSearch = {
        //register all event reference box search ui, contact
        register: function () {
            //event search box show default text
            $("#search-text").focus(function (srcc) {
                if ($(this).val() == $(this)[0].title) {
                    $(this).removeClass("deftext-active");
                    $(this).val("");
                }
            });
            //event type name search detech to show clear button
            $("#search-text").keyup(function () {
                var text = $(this).val();
                if (text !== "") {
                    $(".contact-items").addClass("hidden");
                    $("#middle-bar #contacts .remove-btn").removeClass("hidden");
                    //Search with text
                    textSearch = ".contact-name:contains('" + text + "')"
                    nodeSearch = $(textSearch);
                    numResult = nodeSearch.length;
                    if(numResult > 0) {
                        a.boxSearch.showNumberContacts(numResult);
                        nodeSearch.parent().parent().removeClass("hidden");
                    } else {
                        $("#contacts #items .total-contacts").html("<span>No matches found for" + '<div>"'+ text +'"</div>' +"</span>");
                    }

                } else {
                    a.boxSearch.showNumberContacts(0);
                    $(".contact-items").removeClass("hidden");
                    $("#middle-bar #contacts .remove-btn").addClass("hidden");
                }
            });

            //reset default text search
            $("#search-text").blur(function () {
                if ($(this).val() == "") {
                    $(this).addClass("deftext-active");
                    $(this).val($(this)[0].title);
                }
            });

            //clear text search
            $(".remove-btn").click(function () {
                $("#search-text").val("");
                $("#search-text").blur();
                $(this).addClass("hidden");
                a.boxSearch.showNumberContacts(0);
                $(".contact-items").removeClass("hidden");
            });
        },
        showNumberContacts: function(number, text){
            if(number > 0) {
                $("#contacts #items .total-contacts").html(numResult + " Contacts");
            } else {
                $("#contacts #items .total-contacts").html(zav.global.contacts.size() + " Contacts");
            }
        }
    };
} (zav));

(function (a) {
    a.leftMenu = {
        //register all event reference left menu
        register: function () {
            //event for left menu
            $("#left-menu .menu-btn").click(function () {
                $(".menu-btn").removeClass("active");
                $(this).addClass("active");
            });
        }
    };
} (zav));

(function (a, b) {
    // EDIT CHAT BOX ID HERE
    a.screenApp = {
        nodeRightContent: $("#right-content"),
        nodeNoConv: $("#right-content #no-conversation"),
        nodeConvInfo: $("#right-content #chat-box"),
        nodeChatEditor: $("#right-content #chat-editor"),
        nodeAvaActive: $("#conversation-info .talker-avatar img"),
        nodeNameActive: $("#conversation-info .talker-name span"),
        nodeAvaContactActive: $("#contacts .contact-calling .contact-calling-avatar img"),
        nodeNameContactActive: $("#contacts .contact-calling .contact-calling-name"),
        nodeDivineConv: $("#conversation-info .conversation-divide"),
        register: function () {
            a.screenApp.resetAppUI();
            a.screenApp.showNoConversation(true);
        },
        resetAppUI: function () {
            a.screenApp.showRightContent(false);
            // Get the dimensions of the viewport
            a.global.widthAppDlg = window.innerWidth ||
                        document.documentElement.clientWidth ||
                        document.body.clientWidth;
            a.global.heightAppDlg = window.innerHeight ||
                         document.documentElement.clientHeight ||
                         document.body.clientHeight;
            a.global.widthRightContent = a.global.widthAppDlg - 48 - 275 - 1;
            if(a.global.widthRightContent % 2 == 0){
                a.global.widthRightContent++;
            }  else {
            }

            $("#wrapper").height(a.global.heightAppDlg);
            a.screenApp.nodeRightContent.height(a.global.heightAppDlg);
            a.screenApp.nodeRightContent.width(a.global.widthRightContent);
            a.screenApp.nodeDivineConv.css("backgroundSize", a.global.widthRightContent + "px");
            a.screenApp.alignNoConversation();
            a.screenApp.slimScrollContacts();
            a.screenApp.autoSetWidthTextChat();
            a.screenApp.showRightContent(true);
        },
        slimScrollContacts: function () {
            scrollHeight = a.global.heightAppDlg - 45;
            scrollHeight += "px";
            $('#contain-items').slimScroll({
                height: scrollHeight
            });
            $('#contacts .slimScrollDiv').height(scrollHeight);
            $('#contain-items').height(scrollHeight);
        },
        alignNoConversation: function() {
            //minus: (img(270) + margin-bottom(20) + 2 x text(19 + 7)) / 2 = 164
            marginTop = a.global.heightAppDlg / 2 - 171;
            a.screenApp.nodeNoConv.find("img").css("margin-top", marginTop); 
        },
        autoSetWidthTextChat: function() {
            //minus: ((photo-intput(23) + margin(6 + 10)) + separate(2) + (sticker-intput(18) + margin(10 + 10)) + (send-button(60) + margin(6+6))) = 151
            widthTextInput = a.global.widthRightContent - 151;
            //minus: left(4) + right(4)
            widthMiddeTextInput = widthTextInput - 15;
            a.screenApp.nodeChatEditor.find(".text-input").width(widthTextInput);
            a.screenApp.nodeChatEditor.find(".middle-text-input").width(widthMiddeTextInput);
        },
        hideAllElementRight: function() {
            a.screenApp.nodeRightContent.find(".top-right-content").addClass("hidden");
        },
        showRightContent: function(show) {
            if(show) {
                a.screenApp.nodeRightContent.removeClass("hidden");
            } else {
                a.screenApp.nodeRightContent.addClass("hidden");
            }            
        },
        showNoConversation: function(show) {
            if(show) {
                a.screenApp.nodeNoConv.removeClass("hidden");
            } else {
                a.screenApp.nodeNoConv.addClass("hidden");
            }
        },
        showConversation: function(show) {
            if(show){
                a.screenApp.nodeConvInfo.removeClass("hidden");
            } else {
                a.screenApp.nodeConvInfo.addClass("hidden");
            }
        },
        showChatEditor: function(show) {
            if(show){
                a.screenApp.nodeChatEditor.removeClass("hidden");
            } else {
                a.screenApp.nodeChatEditor.addClass("hidden");
            }            
        },
        setOnClickContact: function () {
            $(".contact-items").click(function () {
                if(a.global.callStatus == a.callStatus.FREE) {
                    var curNode = $(this);
                    $("#contacts #contain-items .contact-items").removeClass("active");
                    curNode.addClass("active");

                    a.global.userActive.avatarUrl = curNode.find(".contact-avatar img").attr("src");
                    a.global.userActive.uid = curNode.find(".contact-uid").attr("value");
                    a.global.userActive.displayName = curNode.find(".contact-name").html();

                    a.screenApp.updateUserActive();
                    if (a.global.firsrLoadContact) {
                        a.global.firsrLoadContact = false;
                        a.screenApp.showNoConversation(false);
                        a.screenApp.showConversation(true);
                        a.screenApp.showChatEditor(true);
                    }
                }
            });
        },
        updateUserActive: function(){
            a.screenApp.nodeAvaActive.attr("src", a.global.userActive.avatarUrl);
            a.screenApp.nodeNameActive.html(a.global.userActive.displayName);    
            //update user calling
            a.screenApp.nodeAvaContactActive.attr("src", a.global.userActive.avatarUrl);
            a.screenApp.nodeNameContactActive.html(a.global.userActive.displayName);                 
        }
    };
    //manager view: cover calling, popup calling
    a.screenCalling = {
        //register all event reference cover ui, popup
        popupRinging: $("#popup-ringing"),
        contactCalling: $("#contacts .contact-calling"),
        coverCalling: $("#calling-cover"),
        statusCalling: $("#calling-cover .calling-status"),
        statusContactCalling: $(".contact-calling .contact-calling-info .calling-type-text"),
        register: function(){
            // register event call on panel conversation info
            $(".talker-call .btn-video-call").click(function () {
                a.screenCalling.requestCallSession();
            });
            // register event on popup calling
            $("#popup-ringing .btn-answer").click(function(){
                // set active user call

                // show cover call
                a.screenCalling.onAnsweringCall();
            });            
            $("#popup-ringing .btn-decline").click(function(){
                a.screenCalling.onDecliningCall();
            });
            $("#popup-ringing .popup-action .exit-call").click(function(){
                a.screenCalling.onDecliningCall();
            });
            $("#contacts .contact-calling .contact-calling-end").click(function(){
                a.screenCalling.onEndingCall();
            });
            // register event on toolbar cover
            $("#calling-cover .calling-toolbar .end-call").click(function() {
                a.screenCalling.onEndingCall();
            });
        },
        showCover: function(show) {
            if(show) {
                a.screenCalling.coverCalling.removeClass("hidden");
            } else {
                a.screenCalling.coverCalling.addClass("hidden");
            }
        },
        showContactCalling: function(show) {
            if(show) {
                a.screenCalling.contactCalling.removeClass("hidden");
            } else {
                a.screenCalling.contactCalling.addClass("hidden");
            }
        },
        renderCoverCalling: function() {
            userInfo = a.global.userActive;
            a.screenCalling.coverCalling.find(".calling-avatar .img-avatar img").attr("src", userInfo.avatarUrl);
            a.screenCalling.coverCalling.find(".calling-name span").html(userInfo.displayName);
            a.screenCalling.updateCallStatus(a.callStatus.CALLING);         
        },
        updateCallStatus: function(status) {
            a.global.callStatus = status;
            a.screenCalling.statusCalling.html(status);
        },
        renderPopupCalling: function(){
            userInfo = a.global.userActive;
            a.screenCalling.popupRinging.find(".caller-avatar img").attr("src", userInfo.avatarUrl);
            a.screenCalling.popupRinging.find(".caller-name span").html(userInfo.displayName);
            a.screenCalling.popupRinging.removeClass("hidden");        
        },
        requestCallSession: function() {
            $.ajax({
                type: "POST",
                url: a.switchboard.callSession,
                async: false,
                data: {
                    zvs: a.global.userInfo.zvs,
                    calleeId: a.global.userActive.uid
                },
                dataType: "jsonp",
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    if (data.error >= 0) {
                        zav.appStatus.hide();
                        a.screenCalling.makingVideoCall(data.data);
                    } else {
                        zav.appStatus.custom("Invalid CallSession");
                    }
                }});
        },
        makingVideoCall: function(callSession){
            if(a.global.callStatus == a.callStatus.FREE) {
                //update status
                a.global.callStatus = a.callStatus.CALLING;
                //render calling cover
                a.screenCalling.renderCoverCalling();
                //showContactCalling
                 a.screenCalling.showContactCalling(true);
                //hide conversation info
                a.screenApp.showConversation(false);
                //show calling cover
                a.screenCalling.showCover(true);
                //call api mfc makingVideoCall
                if(a.winExtApp.waMakingVideoCall(a.global.userActive.uid, callSession) < 0){
                    //making call fail
                    a.screenCalling.updateCallStatus(a.callStatus.CANCELED);
                    a.screenCalling.onCanceledCall();
                }
                return 0;
            }
            return -1;
        },
        //for mfc call js
        onRingingCall: function(){
            if(a.global.callStatus == a.callStatus.CALLING) {
                //update status
                a.screenCalling.updateCallStatus(a.callStatus.RINGING);
                return 0;
            }
            return -1;            
        },
        onIncomingCall: function(uid){
            userInfo = a.global.contacts.get(uid);
            if (userInfo) {
                 if(a.global.callStatus == a.callStatus.FREE) {
                    //set user active
                    a.contacts.setUserActive(userInfo);
                    //render user active
                    a.screenApp.updateUserActive();
                    a.screenCalling.showCover(false);
                    a.screenApp.showNoConversation(false);
                    a.screenApp.showConversation(true);
                    a.screenApp.showChatEditor(true);
                    //render pop up calling
                    a.screenCalling.renderPopupCalling();
                    a.screenCalling.showContactCalling(true);
                    a.screenCalling.updateCallStatus(a.callStatus.RINGING);
                    return 0;
                }
            }
            return -1;            
        },
        onDecliningCall: function() {
            if(a.global.callStatus == a.callStatus.RINGING) {
                a.screenCalling.popupRinging.addClass("hidden");
                a.screenCalling.showContactCalling(false);
                // call api mfc onDecliningCall
                a.winExtApp.waDecliningCall();
                a.screenCalling.updateCallStatus(a.callStatus.FREE);
            }
            return -1;
        },
        onAnsweringCall: function() {
            //render cover calling
            a.screenCalling.renderCoverCalling();
            //hide popup calling
            a.screenCalling.popupRinging.addClass("hidden");
            //hide all element on top right content
            a.screenApp.hideAllElementRight();
            // show cover calling
            a.screenCalling.coverCalling.removeClass("hidden");
            // call api mfc onAnsweringCall
            a.winExtApp.waAnsweringCall();
        },
        onEndingCall: function(){
            if(a.global.callStatus == a.callStatus.CALLING ||
               a.global.callStatus == a.callStatus.RINGING ||
               a.global.callStatus == a.callStatus.INTALK) {
               if(!a.util.isNull(a.global.timer)) {
                    a.global.timer.stop();
                }
                //update status
                a.screenCalling.updateCallStatus(a.callStatus.ENDING);
                // async call api ending
                 setTimeout(function() {
                     a.winExtApp.waEndingCall();
                },1);
                return 0;
            }
            return -1;            
        },
        //for mfc call js
        onIntalkedCall: function(){
            if(a.global.callStatus == a.callStatus.CALLING ||
               a.global.callStatus == a.callStatus.RINGING) {
                a.screenCalling.updateCallStatus(a.callStatus.INTALK);
                a.global.timer = new DataStructure.Timer();
                a.global.timer.start(a.global.timer, a.screenCalling.statusCalling, a.screenCalling.statusContactCalling);
                return 0;
            }
            return -1;           
        },
        onEndedCall: function(){
            if(a.global.callStatus == a.callStatus.RINGING ||
               a.global.callStatus == a.callStatus.INTALK ||
               a.global.callStatus == a.callStatus.ENDING) {
                if(!a.util.isNull(a.global.timer)) {
                    a.global.timer.stop();
                }
                //hide popup calling
                a.screenCalling.popupRinging.addClass("hidden");
                //showContactCalling
                 a.screenCalling.showContactCalling(false);
                a.screenCalling.updateCallStatus(a.callStatus.ENDED);
                a.screenCalling.onCanceledCall();
                return 0;
            }
            return -1;           
        },
        onRejectedCall: function(){
            if(a.global.callStatus == a.callStatus.CALLING ||
               a.global.callStatus == a.callStatus.RINGING) {
               a.screenCalling.updateCallStatus(a.callStatus.REJECTED);
               a.screenCalling.onCanceledCall();
               return 0;
            }
            return -1;           
        },
        onBusyedCall: function(){
            if(a.global.callStatus == a.callStatus.CALLING ||
               a.global.callStatus == a.callStatus.RINGING) {
               a.screenCalling.updateCallStatus(a.callStatus.BUSY);
               a.screenCalling.onCanceledCall();
               return 0;
            }
            return -1;           
        },
        onCanceledCall: function(){
            setTimeout(function() {
                //update status call
                a.global.callStatus = a.callStatus.FREE;
                //render user active
                a.screenApp.updateUserActive();
                a.screenCalling.showCover(false);
                a.screenCalling.showContactCalling(false);
                a.screenApp.showConversation(true);
            },1500);
        }
    };
} (zav, window));


(function (a) {
    a.contacts = {
        register: function(){
            //load contact
            //contact node : $("#contain-items")
            var numTry = 0;
            a.global.contacts = new DataStructure.Map();
           // a.contacts.load($("#contain-items"), zav.global.userInfo.zvs, numTry);        
        },
        load: function (contactNode, session, numTry) {
            var numTry = 0;
            $.ajax({
                type: "POST",
                url: zav.switchboard.contact,
                data: {
                    zvs: session
                },
                dataType: "jsonp",
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    if (data.error >= 0) {
                        a.contacts.parse(contactNode, data.data);
                        zav.appStatus.hide();
                    } else {
                        zav.appStatus.syncing();
                    }
                    return false;
                },
                fail: function () {
                    numTry++;
                    if(numTry < 3) {
                        zav.appStatus.syncing();
                        zav.contacts.load($("#contain-items"), zav.global.userInfo.zvs, numTry); 
                    } else {
                        zav.appStatus.offline();
                    }
                    return false;
                },
                error: function (jqXHR, textStatus) {
                    numTry++;
                    if(numTry < 3) {
                        zav.appStatus.syncing();
                        zav.contacts.load($("#contain-items"), zav.global.userInfo.zvs, numTry); 
                    } else {
                        zav.appStatus.offline();
                    }
                    return false;
                },
                timeout: 3000 // sets timeout to 3 seconds
                });
        },
        parse: function (contactNode, contactList) {
            var contactArray = jQuery.parseJSON(contactList);
            var numberContact = contactArray.length;
            var contactHTML = "";
            if(numberContact > 0) {
                for (var i = 0; i < numberContact; ++i) {
                    contactObj = contactArray[i];
                    //render avatar
                    contactHTML += "<div class='contact-items' unselectable='on'>" +
                                        "<div class='contact-avatar' unselectable='on'>" +
                                            "<img alt='ava' src='";
                    contactHTML += contactObj.avatarUrl;
                    contactHTML += "' unselectable='on'></div>";
                    //render display name
                    contactHTML += "<div class='contact-info left-float' unselectable='on'>" +
		                                "<div class='contact-name' unselectable='on'>";
                    contactHTML += contactObj.displayName;
                    contactHTML += "</div><div class='contact-call' unselectable='on'></div>";
                    //render uid
                    contactHTML += "<div class='contact-uid hidden' value='";
                    contactHTML += contactObj.uid;
                    contactHTML += "'></div></div></div>";
                
                    a.global.contacts.put(contactObj.uid, contactObj);
                }
                contactHTML += "<div class='text-center total-contacts' unselectable='on'>";
                contactHTML += a.global.contacts.size();
                contactHTML += " Contacts</div>";
                $(".no-items").addClass("hidden");
                $("#contacts").css("backgroundColor", "#f9f9f9"); 
                contactNode.append(contactHTML);
                a.screenApp.setOnClickContact();
                a.util.makeUnselectable(document.getElementById("wrapper"));
            }
            //window.external.waConnectToServerSip();
        },
        setUserActive: function (userInfo) {
            a.global.userActive = userInfo;
        }
    };
} (zav));

(function (a) {
    a.conversation = {
        register: function () {
            //receive command and execute temporary for test 
            var commandInput = $("#chat-editor .middle-text-input");
            a.conversation.slimScrollConversation();
            $("#chat-editor .send-btn").click(function () {
                command = commandInput.val();
                switch (command) {
                    case "onIncomingCall":
                        {
                            onIncomingCall(73264082);
                            break;
                        }
                    case "onRingingCall":
                        {
                            onRingingCall();
                            break;
                        }
                    case "onIntalkedCall":
                        {
                            onIntalkedCall();
                            break;
                        }
                    case "onRejectedCall":
                        {
                            onRejectedCall();
                            break;
                        }
                    case "onBusyedCall":
                        {
                            onBusyedCall();
                            break;
                        }
                    case "onEndedCall":
                        {
                            onEndedCall();
                            break;
                        }
                    case "onConntected":
                        {
                            onConntected();
                            break;
                        }
                    default:
                        {
                            //a.conversation.appendData(command);
                            break;
                        }
                }
                return false;
            });
        },
        appendData: function(data){
            // var html = "";
            //     html += "<ul><div class='conversation-item right'>"
            //     html += "    <div class='wrapper-ava'>"
            //     html +=         "<img src='" + zav.global.userInfo.avatarUrl + "' class='msg-ava'/>"
            //     html +=      "</div>"
            //     html += "    <div class='wrapper-bubble'>"
            //     html += "        <div class='msg-bubble'>"
            //     html += "            <div class='msg-content'>"
            //     html +=                 data
            //     html += "             </div>"

            //     html +=             "<div class='msg-status'>";
            //     html +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>Deliverd<div>"
            //     html +=             "</div>"
            //     html +=             "</div>"
            //     html += "        </div>"
            //     html += "   </div>"
            //     html += "</div></ul>"
            //     html += "<div class='cb'><div>"



            // $("#conversation-content").append(html);            
        },
        slimScrollConversation: function () {
            scrollHeight = a.global.heightAppDlg - 45 - 100;
            scrollHeight += "px";
            $('#conversation-content').slimScroll({
                height: scrollHeight
            });
            $('#conversation-content .slimScrollDiv').height(scrollHeight);
        }
    };
} (zav));

// OVERWRITES old selector
$.expr[":"].contains = $.expr.createPseudo(function(arg) {
    return function( elem ) {
        return $(elem).text().toUpperCase().indexOf(arg.toUpperCase()) >= 0;
    };
});


$(function () {
    zav.appStatus.connecting();
    zav.winExt.waOnDocumentReady();
    zav.util.initUserInfo();
    //register leftMenu
    zav.leftMenu.register();
    //register contacts
    zav.contacts.register();
    //register screen app
    zav.screenApp.register();
    //register screen calling
    zav.screenCalling.register();
    //register box search
    zav.boxSearch.register();
    //register conversation search
    zav.conversation.register();
});

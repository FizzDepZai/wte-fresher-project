(function (a, b) {
    a.notifyAlert = {
        notify: function (message) {
            nodeContent = $("#text-alert .text-content");
            nodeContent.html(message);
            nodeContent.removeClass("hidden");
        },
        removeNotify: function () {
            nodeContent = $("#text-alert .text-content");
            nodeContent.addClass("hidden");
            nodeContent.html("");
        }
    };
} (zav, window));


(function (a, b) {
    a.screenTrans = {
        nodeContent: $("#step-trans"),
        show: function (active, message, nameBtn) {
            nodeContent.removeClass("hidden");
             //behavior of variable active
            if(active){
                nodeContent.find(".call-icon").removeClass("deactive");
            } else {
                nodeContent.find(".call-icon").addClass("deactive");
            }
            //change message
            nodeContent.find(".text-trans").html(message);
            //behavior of variable nameBtn
            if(nameBtn != ""){
                nodeContent.find(".nav-trans").removeClass("hidden");
                nodeContent.find("#trans-btn").html(nameBtn);
            } else {
                nodeContent.find(".nav-trans").addClass("hidden");
            }
        },
        hide: function () {
            $("#step-trans").addClass("hidden");
        }
    };
} (zav, window));

$(function () {
    zav.util.makeUnselectable(document.getElementById("wrapper"));
    zav.util.initUserInfo();

    $("#step1 #yes-btn").click(function () {
        if (zav.util.checkLogin()) {
            zav.winExtLogin.waOnLoginSuccess(zav.global.userInfo.uid, zav.global.userInfo.zvs);
        } else {
            $("#step1").addClass("hidden");
            $("#step2").removeClass("hidden");
        }
    });

    $("#step1 #no-btn").click(function () {
        zav.winExtLogin.waOnCancel();
    });

    $("#step2 #continue-btn").click(function () {
        //clear old notify message
        zav.notifyAlert.removeNotify();

        var _phoneNumber = $("#phone-number").val();
        if (_phoneNumber == "") {
            zav.notifyAlert.notify(zav.switchboard.messageNotify.nullPhoneNumber);
            return false;
        }
        //hidden step2
        $("#phone-number").blur();
        $("#step2").addClass("hidden");
        zav.screenTrans.show(true, zav.switchboard.messageNotify.connecting, zav.switchboard.nameBtn.nullBtn);
        //send request code
        $.ajax({
            type: "POST",
            url: zav.switchboard.requestCode,
            data: {
                phoneNumber: _phoneNumber
            },
            dataType: "jsonp",
            xhrFields: {
                withCredentials: true
            },
            success: function (data) {
                zav.screenTrans.hide();
                if (data.error >= 0) {
                    zav.global.token = data.data;
                    $("#step3").removeClass("hidden");
                    $("#activation-code").focus();
                } else {
                    $("#step2").removeClass("hidden");
                    zav.notifyAlert.notify(data.message);
                }
                return false;
            },
            fail: function () {
                $(".step-login").addClass("hidden");
                zav.screenTrans.show(false, zav.switchboard.messageNotify.networkError, zav.switchboard.nameBtn.tryBtn);
                return false;
            },
            error: function (jqXHR, textStatus) {
                $(".step-login").addClass("hidden");
                zav.screenTrans.show(false, zav.switchboard.messageNotify.networkError, zav.switchboard.nameBtn.tryBtn);
                return false;
            },
            timeout: 3000 // sets timeout to 3 seconds
        });
    });

    $("#activation-code").keyup(function () {
        nodeEnter = $("#step3 #enter-btn");
        var code = $(this).val();
        if (code.length >= 4) {
            nodeEnter.removeClass("disabled");
        } else {
            if (!nodeEnter.hasClass("disabled")) {
                nodeEnter.addClass("disabled");
            }
        }
    });

    $("#step3 #back-btn").click(function () {
        $("#step3").addClass("hidden");
        $("#step2").removeClass("hidden");
    });

    $("#step3 #enter-btn").click(function () {
        if (!$(this).hasClass("disabled")) {
            var _code = $("#activation-code").val();
            if (_code == "" || _code.length < 4) {
                zav.notifyAlert.notify(zav.switchboard.messageNotify.invalidCode);
                return false;
            }
            if (zav.global.token != "") {
                $("#activation-code").blur();
                $.ajax({
                    type: "POST",
                    url: zav.switchboard.loginViaPhone,
                    data: {
                        tok: zav.global.token,
                        code: _code
                    },
                    xhrFields: {
                        withCredentials: true
                    },
                    dataType: "jsonp",
                    success: function (data) {
                        if (data.error >= 0) {
                            zav.global.userInfo = jQuery.parseJSON(data.data);
                            zav.winExtLogin.waOnLoginSuccess(zav.global.userInfo.uid, zav.global.userInfo.zvs);
                        } else {
                            zav.notifyAlert.notify(data.message);
                        }
                        return false;
                    },
                    fail: function () {
                        $(".step-login").addClass("hidden");
                        zav.screenTrans.show(false, zav.switchboard.messageNotify.networkError, zav.switchboard.nameBtn.tryBtn);
                        return false;
                    },
                    error: function (jqXHR, textStatus) {
                        $(".step-login").addClass("hidden");
                        zav.screenTrans.show(false, zav.switchboard.messageNotify.networkError, zav.switchboard.nameBtn.tryBtn);
                        return false;
                    },
                    timeout: 3000 // sets timeout to 3 seconds
                });
            } else {
                //empty token
                zav.notifyAlert.notify(zav.switchboard.messageNotify.emptyToken);
                return false;
            }
        } else {
            //btn disabled
            return false;
        }
    });
});


        // map emoticons' info
       var emoticons_map={};
       var groupEmotion_map = {};

        $(document).ready(function(){
                     

           $(document).on('click','.ace-thumbnails [data-rel="colorbox"]' , function(e){
                        jQuery('.ace-thumbnails [data-rel="colorbox"]').colorbox();
                                   e.preventDefault();

                  });
           $(document).on('click','ul.tabemotion li' , function(e){
                     $(".tabemoctn").css("display","none");
                     var idTabClicked = $(this).attr('id');
                     idTabClicked = idTabClicked.substring(idTabClicked.length-1,idTabClicked.length);
                      if($("ul.tabemotion li div").hasClass('current')==true){
                        $("ul.tabemotion li div").removeClass("current");
                         $("ul.tabemotion li div").css("background","#97b7c5");

                      }
                      $("#titleGroupEmotion"+ idTabClicked  + " div").addClass("current");
                      $("#titleGroupEmotion"+ idTabClicked  + " div").css("background","#fff");
                      $("#group"+ idTabClicked).css("display","");


                  });       
                  

        
        
     })


            /**
            * gui message chat den server chat 
            */
            function chatImage(imageURL) {
                var message = imageURL;
                if (connected)
                {
                    $.ajax({
                        type: "POST",
                        async: true,
                        data: {userName: $("#userId").val(), userNameOfReceiver:  receiverId, message: message,type :'image', oauthCode: CONFIG.OAUTH_CODE},
                        url: "http://"+CONFIG.CHAT_HOST+":"+portServer+"/chat",
                        success: (function(response) {
                            if (response !== 'undefined'){
                             var responseData  = JSON.parse(response);
                             var contentChat = responseData.content;
                             //contentChat = emotion_parse(contentChat);
                             //lay username hien thi thong tin phia tren them vao box
                             /*
                             messageItem  = '<div class="itemdiv dialogdiv"> '+ '<div class="user"><img alt="Users avatar" '
                                    +'src="'+CONFIG.HOST_RESOURCE +'/assets/avatars/user.jpg"/></div>'
                                    +'  <div class="body"> <div class="time"><i class="icon-time"></i> <span class="orange">'+responseData.time+'</span></div>'
                                    + ' <div class ="name"><a href="#" >' +  $('#displayNameViews').text() + '</a> </div>'    
                                     +'<div class ="text ace-thumbnails">'
                                    + ' <a href="' + contentChat +'" data-rel="colorbox"> '
                                    +'<img src = "'+contentChat+'" width="60" height="60"> '
                                    +' </a></div> </div>  </div>';
                                    */

                                    var messageItem ="";




                                      messageItem += "<ul><div class='conversation-item left '>"
                                              
                                                messageItem += "    <div class='wrapper-bubble'>"
                                                messageItem += "        <div class='msg-bubble-left'>"
                                                messageItem += "            <div class='msg-content-left text ace-thumbnails'> "
                                                messageItem +=                 ' <a href="' + contentChat +'" data-rel="colorbox"> '+'<img src = "'+contentChat+'" width="60" height="60"> '
                                                messageItem += "            </a></div>"

                                                messageItem +=             "<div class='msg-status-image-left'>";
                                                messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+responseData.time+"<div>"
                                                messageItem +=             "</div>"
                                                messageItem +=             "</div>"
                                                messageItem += "        </div>"
                                                messageItem += "   </div>"
                                                messageItem += "</div></ul>"
                                                messageItem += "<div class='cb'><div>"

                             $('#chatWindow').append(messageItem);
                             jQuery('.ace-thumbnails [data-rel="colorbox"]').colorbox();

                             totalMessage++; 
                             

                            }else{
                                //error
                            }
                        }),
                    });
                }
                else
                {
                    $('#chatWindow').append("<p>"  + " - ERROR: You must lock your receiver before.</p>");
                }

            }

            /*
            *   Parse specific text to emoticon
            * return tag img chua style emotion
            */
            function emotion_parse(text) {
              var  patterns = [],
                 metachars = /[[\]{}()*+?.\\|^$\-,&#\s]/g;

              // build a regex pattern for each defined property
              for (var i in emoticons_map) {
                //if (emoticons_map.hasOwnProperty(i)){ // escape metacharacters
                  patterns.push('('+i.replace(metachars, "\\$&")+')');
               // }
              }

              // build the regular expression and replace
              return text.replace(new RegExp(patterns.join('|'),'g'), function (match) {
                return typeof emoticons_map[match] != 'undefined' ?
                       '<img src="'+emoticons_map[match].style+'"/>' :
                       match;
              });
            }
            /*
            *   Create html from emotions map
            */
            function createListImagines(groupEmotionId)
            {

                    var result = "";
                    for(var item in emoticons_map)
                    {

                     // condition add to group emotion
                      if(emoticons_map[item].group == groupEmotionId){
                          if(emoticons_map[item].keyInput != ""){

                                result += "<li onclick='addEmotion(\"" +item+ " \")'> ";
                                result += " <img width='24' height'24' ";
                                result += " style=\"background:url(" + emoticons_map[item].style +  ") no-repeat 50% 50%\" ";
                                result += " src='" + emoticons_map[item].src + "'";
                                result += " title='" + emoticons_map[item].title + " ' ";
                                result += " alt='" + emoticons_map[item].alt + " ' ";
                                result += "name=' " + item + " ' " + ">";
                                result += "</li>";
                          }else{
                                result += "<li onclick='chatImage(\"" +emoticons_map[item].style + " \")'> ";
                                result += " <img width='24' height'24' ";
                                result += " style=\"background:url(" + emoticons_map[item].style +  ") no-repeat 50% 50%\" ";
                                result += " src='" + emoticons_map[item].src + "'";
                                result += " title='" + emoticons_map[item].title + " ' ";
                                result += " alt='" + emoticons_map[item].alt + " ' ";
                                result += "name=' " + item + " ' " + ">";
                                result += "</li>";
                          }
                      }
                    }
                    return result;

            }
          

            function loadGroupEmotion(){
               $.ajax({
                        type: "POST",
                        async: true,
                        url: "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+"/load_groupemotion",
                        success: (function(response) {
                            if (response !== 'undefined'){
                             //emoticons_map =  $.parseJSON('[' + response + ']');      
                            //var str_emotion_json = JSON.stringify(response);
                             groupEmotion_map= JSON.parse(response);
                            //for(var item in emotion_json){
                            //    emoticons_map[item.keyInput]  = item;
                           // }
                           // for(var i =0;i<emotion_json.length;i++){
                         
                           //  groupEmotion_map[emotion_json[i].id]  = emotion_json[i].name;
                           // }
                            // loadEmotions();


                            for(var item in groupEmotion_map){

                                  var idGroupEmotion = parseInt(item) ;
                                  //them tab window
                                  var tabEmotion = ' <div  class="tab_emotion tabemoctn " id = "group' + idGroupEmotion+ '">'                                      
                                                +'<ul class="itememotion">'
                                                 +'<div class="zmscrollableui" style="width: 420px; height: 177px;">'
                                                 +' <div class="zmscrollableui_wrapper" tabindex="0">'
                                                  +' <div class="zmscrollableui_body" style="width: 420px;">'
                                                   +' <div id="idContentSet" class="zmscrollableui_content">'
                                                   +' </div></div></div>'
                                            +'<div class="zmscrollableui_bar unselectable" unselectable="on" style="visibility: hidden;">'
                                             +'  <div class="zmscrollableui_gripper">    </div> </div> </div></ul> </div> ';

                                      
                                              
                              
                                //var tabEmotionTitle = ' <li style="font-size:14px;"><div rel="emotion" id = "titleGroupEmotion'+ idGroupEmotion+'"  >'+ groupEmotion_map[item]+'</div></li>';
                                var tabEmotionTitle = ' <li style="font-size:14px;" id = "titleGroupEmotion'+idGroupEmotion+'"><div rel="emotion">'+ groupEmotion_map[item]+'</div></li>';
                              $(".tabemotion").append(tabEmotionTitle);
                              $(".ctnemotion").append(tabEmotion);
                               //group emotion dau tien show       

                               if(idGroupEmotion !=0){
                                    $("#group"+ idGroupEmotion).css("display","none");
                                }else{
                                  $("#titleGroupEmotion"+ idGroupEmotion + " div").addClass("current");

                                }       
                            }
                            //load css for box of emotion
                            $("ul.tabemotion li").css({
                                "display":"block", 
                                "float":"left",
                                "font-weight":"bold", 
                                "margin-left":"10px"
                              });
                             $("ul.tabemotion li div").css({
                                "background":"#97b7c5",
                                "line-height":"23px",
                                "height":"23px",
                                "padding":"0 10px", 
                                "float":"left",
                                "color":"#fff",
                                "border":"1px" ,
                                "solid": "#97b7c5"
                              });
                              $("ul.tabemotion li div.current").css({
                                "background":"#fff", 
                                "color":"#444",
                                "border":"1px" ,
                                "solid": "#a9c4cf",
                               "border-bottom":"1px", 
                                "solid": "#fff"
                              });




                              $("ul.tabemotion li div").hover(function(){
                                      // rgb(255, 255, 255) = #fff
                                    //if($(this).css("background") != 'rgb(255, 255, 255)'){
                                          $(this).css({
                                                "background":"#fff", 
                                                "color":"#444",
                                                "border":"1px", 
                                                "solid": "#a9c4cf",
                                                "border-bottom":"1px", 
                                                "solid": "#fff"
                                          });
                              
                                    },function(){
                                      if($(this).hasClass('current')==false){
                                             $(this).css({
                                               "background":"#97b7c5",
                                              "color":"#fff",
                                              "border":"1px" ,
                                              "solid": "#97b7c5"
                                          });
                                   }
                                       
                                  }); 
                            // document.getElementById("idContentSet").innerHTML = createListImagines();          
                        
                            }else{
                                alert("Error1!!! getGroupEmotion");
                            }
                        }),
                        error: (function(error) {
                            alert("Error!!! getGroupEmotion");
                    })
                });
            }

            /*
            *   Load emotions content 
            */
            function loadEmotions()
            {
             
                  $.ajax({
                        type: "POST",
                        async: true,
                        url: "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+"/load_emotion",
                        success: (function(response) {
                            if (response !== 'undefined'){
                             //emoticons_map =  $.parseJSON('[' + response + ']');      
                            //var str_emotion_json = JSON.stringify(response);
                            var emotion_json= JSON.parse(response);
                            //for(var item in emotion_json){
                            //    emoticons_map[item.keyInput]  = item;
                           // }
                           for(var i =0;i<emotion_json.length;i++){
                          //add keyInput cho emotion
                            if(emotion_json[i].keyInput == ""){
                              emotion_json[i].keyInput = "[e]"+emotion_json[i].alt +"[/e]";
                            }
                            
                            emoticons_map[emotion_json[i].keyInput]  = emotion_json[i];
                           }
                             //document.getElementById("idContentSet").innerHTML = createListImagines(); 
                             for(var groupEmotionId in groupEmotion_map){ 
                                $("#group"+ groupEmotionId + "  #idContentSet").append(createListImagines(groupEmotionId));
                            }
          
                              //$("#idContentSet").append(createListImagines());
                            }else{
                                alert("Error1!!! getEmotion1");
                            }
                        }),
                        error: (function(error) {
                            alert("Error!!! getEmotion");
                    })
                });
                
                
                             //document.getElementById("idContentSet").innerHTML = createListImagines();          

            }


           
          

            /*
            *   Add specific text when user click on an emoticon
            * true: show emotion ngay, false hien keyInput
            */
            function addEmotion(icon)
            {
                icon = icon.trim();
                /*
                    //kiem icon trong danh sach emotion
                             
                              var emotionImage =' <img width="24" height= "24" ' 
                                 + 'style="background:url(' + emoticons_map[icon].style  +' ) no-repeat 50% 50%"'
                                +'src='+ emoticons_map[icon].src 
                                + ' title=' + emoticons_map[icon].title
                                + ' alt="'+ emoticons_map[icon].alt
                                + ' name='+ icon+ '> ';
                       

                                  $('#chatWindow').append(emotionImage);
                                  totalMessage++;       
                */
                     var input = $( "#chatInput" );
                      // cach ra giua emotions va text la space
                     input.val( input.val() + " " + icon + " " );
                     $("#tinypopover-emoticons").hide();

                    

            }            



           /**
           * tao connection cho user ngay khi vao trang index den server chat tuong ung
           */
          function createConnection()
            {
               
                $.ajax({
                    type: "POST",
                    async: true,
                    cache: false,
                    url: "http://fresherchat.zapps.vn/connected",
                    success: function (responsePort) {
                        portServer = responsePort;
                        $('#content_header').append("<p>"+ portServer);

                        runPoll();
                            
                 }  
             });
            }

           
             /**
            * Get list friend from Business server
            */
            function getFriendList() {
                $.ajax({
                        type: "POST",
                        async: true,
                        data: {userId:$("#userId").val(), oauthCode: CONFIG.OAUTH_CODE},
                      url: "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+"/friendlist",
                        success: (function(response) {
                            if (response !== 'undefined'){
                                for(var i = 0; i < response.length; i++) {
                                    var str_FriendJSON = JSON.stringify(response[i]);
                                    var f_userId = JSON.parse(str_FriendJSON).userId.toString(),
                                    f_displayName = JSON.parse(str_FriendJSON).displayName,
                                    f_avatar = JSON.parse(str_FriendJSON).avatar,
                                    f_hasOffline = JSON.parse(str_FriendJSON).hasOffline;
                                    linkProfile =  "localhost:" +CONFIG.BUSINESS_PORT+  "/profile?userId=" + f_userId + "&oauthCode=" + CONFIG.OAUTH_CODE ;
                                    //add new friend id to array friend id

                                    arrFriendZaloId[i] = f_userId;
                                    var indexFriend = i;
          
 

                                    var friendItem = ("<div  class = \"contact-items\" unselectable='on' id = \"friend" + indexFriend + "\" onclick = \"addUserChat(\'" + f_displayName + "\',\'" + f_userId + "\',\'" + indexFriend + "\');\">" + 
                                        "<div class=\"contact-avatar\" unselectable=\"on\"> " +
                                            "<img alt=\"ava\" src=\""+ f_avatar + "\" unselectable='on'  id = 'friend-image-avatar'/>" +
                                        "</div>" +
                                        "<div class=\"contact-info left-float\" unselectable='on' >" +
                                            "<div class=\"contact-name\" id =\"friend-name\"  unselectable='on' >"+ 
                                                        f_displayName +
                                            "</div>" +
                                            "<div class=\"contact-call hidden\" id = \"user-status\" unselectable='on'></div>" +
                                            "<div  style=\"width:100px; text-align:center; color:#C0C0C0 ;\" class = \"contact-calling-type\" id = \"notify-message\" unselectable='on'></div>" +

                                        "</div>" + "</div>");

                                        // +"<div class=\"contact-calling-end left-float text-center\">" +
                                        //     "<div class=\"calling-end-img left-float\"></div>" +
                                        //     "<div class=\"calling-end-text left-float text-center\" id =\"user-status\"></div>" +
                                        // "</div>" 
                                   
                                      $(".no-items").addClass("hidden");
                                       $(".app-status").addClass("hidden");
                                     $("#contacts").css("backgroundColor", "#f9f9f9"); 
                                    $("#contain-items").append(friendItem); 
                                    zav.screenApp.setOnClickContact();
                                    if(f_hasOffline==true){
                                         var selectorNotifyMessage = "#friend"+indexFriend +"  #notify-message";
                                          $(selectorNotifyMessage).text('Tin nhắn mới');
                                    }
                                }

                               friendList =  JSON.stringify(arrFriendZaloId);
                               checkFriendOnline();
                            }else{
                                //error
                                $("#contain-items").append("<p>"  + " - ERROR when getting</p>");
                            }
                        }),
                        error: (function(error) {
                            alert("Error!!! load friend list ");
                    })
                });
                
            }
            
            
            /**
            * chay connect long polling 
            * userId la input hidden tren views
            */
           function runPoll()
            {
                $.ajax({
                    type: "POST",
                    async: true,
                    cache: false,
                    data: { userId:$("#userId").val(), oauthCode: CONFIG.OAUTH_CODE },
                    url: "http://"+CONFIG.CHAT_HOST+":"+portServer+"/poll",
                    timeout: 30000,
                    complete: runPoll,
                    success: (function(response) {
                        //tra response ve tu server chat
                        if (response === 'undefined')
                        {
                            $('#content_header').append("<p>SUCCESS BUT UNDEFINED.</p>");
                        }
                        else
                        {
                            //response empty khi poll timeout
                            if(response!=""){
                                 //neu dang focus vao khung chat nguoi gui den
                                 var friendListArray = JSON.parse(friendList);

                                if(receiverId == response[0].from){
                                //lay message duoc tra ve va append vao chat box
                                    for(var i =0;i<response.length;i++){
                                             var contentChat = emotion_parse(response[i].content);
                                             var selectorNameSending = "#friend"+friendListArray.indexOf(response[i].from)+"  #friend-name";  
                                             var messageItem ="" ;
                                             var selectorImgSrc = "#friend"+friendListArray.indexOf(response[i].from)+"  #friend-image-avatar";  
                                             var imgSrc  = $(selectorImgSrc).attr("src");

                                             if(response[i].type == "text"){  
                                                //  messageItem  = '<div class="itemdiv dialogdiv"> '+ '<div class="user"><img alt="Users avatar" '
                                                // +'src="http://s240.avatar.talk.zdn.vn/default"/></div>'
                                                // +'  <div class="body"> <div class="time"><i class="icon-time"></i> <span class="orange">'+response[i].time+'</span></div>'
                                                // + ' <div class ="name"><a href="#">'+ $(selectorNameSending).text() + '</a> </div>'    
                                                // +'<div class = "text">'+contentChat+' </div> </div>  </div>';

                                                messageItem += "<ul><div class='conversation-item  right'>"
                                                messageItem += "    <div class='wrapper-ava'>"
                                                messageItem +=         "<img src='" + imgSrc  + "' class='msg-ava'/>"
                                                messageItem +=      "</div>"
                                                messageItem += "    <div class='wrapper-bubble'>"
                                                messageItem += "        <div class='msg-bubble'>"
                                                messageItem += "            <div class='msg-content'>"
                                                messageItem +=                 contentChat
                                                messageItem += "             </div>"

                                                messageItem +=             "<div class='msg-status'>";
                                                messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+response[i].time+"<div>"
                                                messageItem +=             "</div>"
                                                messageItem +=             "</div>"
                                                messageItem += "        </div>"
                                                messageItem += "   </div>"
                                                messageItem += "</div></ul>"
                                                messageItem += "<div class='cb'><div>"


                                               $('#chatWindow').append(messageItem);




                                            }else if (response[i].type == "image"){
                                                //  messageItem  = '<div class="itemdiv dialogdiv"> '+ '<div class="user"><img alt="Users avatar" '
                                                // +'src="http://s240.avatar.talk.zdn.vn/default"/></div>'
                                                // +'  <div class="body"> <div class="time"><i class="icon-time"></i> <span class="orange">'+response[i].time+'</span></div>'
                                                // + ' <div class ="name"><a href="#">'+ $(selectorNameSending).text() + '</a> </div>'    
                                                // +'<div class ="text ace-thumbnails">'
                                                // + ' <a href="' + contentChat +'" data-rel="colorbox"> '
                                                // +'<img src = "'+contentChat+'" width="60" height="60"> '
                                                // +' </a></div> </div>  </div>';
                                                messageItem += "<ul><div class='conversation-item  right'>"
                                                messageItem += "    <div class='wrapper-ava'>"
                                                messageItem +=         "<img src='" + imgSrc  + "' class='msg-ava'/>"
                                                messageItem +=      "</div>"
                                                messageItem += "    <div class='wrapper-bubble'>"
                                                messageItem += "        <div class='msg-bubble'>"
                                                messageItem += "            <div class='msg-content text ace-thumbnails'> "
                                                messageItem +=                 ' <a href="' + contentChat +'" data-rel="colorbox"> '+'<img src = "'+contentChat+'" width="60" height="60"> '
                                                messageItem += "            </a></div>"

                                                messageItem +=             "<div class='msg-status-image'>";
                                                messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+response[i].time+"<div>"
                                                messageItem +=             "</div>"
                                                messageItem +=             "</div>"
                                                messageItem += "        </div>"
                                                messageItem += "   </div>"
                                                messageItem += "</div></ul>"
                                                messageItem += "<div class='cb'><div>"
  


                                               $('#chatWindow').append(messageItem);
                                              jQuery('.ace-thumbnails [data-rel="colorbox"]').colorbox();

                                            }

                                            totalMessage++;
                                    }
                                }else{
                                    //khi khong focus vao khung chat thi notify ten nguoi gui o friendlist
                                    //lay index cua object friend tu array friend list chua cac id , truy xuat den item-friend index tuong ung 
                                    var selectorNotifyMessage = "#friend"+friendListArray.indexOf(response[0].from)+"  #notify-message";
                                    $(selectorNotifyMessage).text('Tin nhắn mới');
                                }
                            }
                        }
                    }),
                    error: (function(error) {
                        $('#chatWindow').append("<p>"  + " - ERROR when polling.</p>");
                    })
                });

            }
            

       

    
    
            /**
            * gui message chat den server chat 
            */
            function chat() {
                var message = $("#chatInput").val();
                 $("#chatInput").val('');
                if (connected)
                {
                  (function remove_tags(html)
                  {
                   var tmp = document.createElement("DIV");
                   tmp.innerHTML = html; 
                   return tmp.textContent||tmp.innerText; 
                 })(message);

                    $.ajax({
                        type: "POST",
                        async: true,
                        data: {userName: $("#userId").val(), userNameOfReceiver:  receiverId, message: message, type:'text', oauthCode: CONFIG.OAUTH_CODE},
                        url: "http://"+CONFIG.CHAT_HOST+":"+portServer+"/chat",
                        success: (function(response) {
                            if (response !== 'undefined'){
                             var responseData  = JSON.parse(response);
                             var contentChat = responseData.content;
                             contentChat = emotion_parse(contentChat);
                             //lay username hien thi thong tin phia tren them vao box
                            // var messageItem  = '<div class="itemdiv dialogdiv"> '+ '<div class="user"><img alt="Users avatar" '
                            //         +'src="'+CONFIG.HOST_RESOURCE +'/assets/avatars/user.jpg"/></div>'
                            //         +'  <div class="body"> <div class="time"><i class="icon-time"></i> <span class="orange">'+responseData.time+'</span></div>'
                            //         + ' <div class ="name"><a href="#" >' +  $('#displayNameViews').text() + '</a> </div>'    
                            //        +'<div class ="text">'+contentChat+' </div> </div>  </div>';


                              var messageItem = "";
                                  messageItem += "<ul><div class='conversation-item  left'>"
                                 
                                  messageItem += "    <div class='wrapper-bubble'>"
                                  messageItem += "        <div class='msg-bubble-left'>"
                                  messageItem += "            <div class='msg-content-left'>"
                                  messageItem +=                 contentChat
                                  messageItem += "             </div>"

                                  messageItem +=             "<div class='msg-status-left'>";
                                  messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+responseData.time+"<div>"
                                  messageItem +=             "</div>"
                                  messageItem +=             "</div>"
                                  messageItem += "        </div>"
                                  messageItem += "   </div>"
                                  messageItem += "</div></ul>"
                                  messageItem += "<div class='cb'><div>"

       

                              $("#chatWindow").append(messageItem);  

                             totalMessage++;       
                             
                                 
                            }else{
                                //error
                            }
                        }),
                        
                    });
                }
                else
                {
                    $('#chatWindow').append("<p>"  + " - ERROR: You must lock your receiver before.</p>");
                }

            }


           

            /**
            * thay doi noi dung chat box tuong ung voi user
            */
            function processAjaxData(user, urlPath) {
               // document.getElementById("text").innerHTML = "<p>" + "This is chat content with " + user + "</p>";
                $('#chatWindow').text('');
                  var getOldLink = '<a style=" font-size:120%; margin-bottom:5px;"' 
                                  +' href="#" onclick="getOldMessage(receiverId);return false;" id = "old-link"> Get Old Message</a>'
                 $('#chatWindow').append(getOldLink);    
               
                document.title = user;
                var response = {'html': "<p>" + "This is chat content with" + user + "</p>", 'pageTittle': user};
                window.history.pushState({"html": response.html, "pageTitle": response.pageTitle}, "", urlPath);
            }

            /**
            * them user vao khung chat khi click tren friend list
            */
            function addUserChat(username,friendUserId, indexFriend) {
                 if(currentFriendIndexChat == indexFriend){
                    return;
                }
                else {
                    currentFriendIndexChat =  indexFriend;
                }
               
                //reset cac background  friend con lai la color middlebar;
                var friendListArray = JSON.parse(friendList);
                for(var i =0;i<friendListArray.length;i++){
                    
                    $('#friend'+i).css("background-color", "transparent");
                }

                //get element choose
                var selectorFriend = "#friend"+indexFriend;
                var selectorNotifyMessage = selectorFriend+ " #notify-message";


                //set background #ffffff
                $(selectorFriend).css("background-color", "#ffffff");

                //set lai text notify la empty
                $(selectorNotifyMessage).text('');

                linkProfile =  "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+ "/profile?userId=" + friendUserId + "&oauthCode=" + CONFIG.OAUTH_CODE  ;
                //"<a href='http://"  + linkProfile +"'>" +


                receiverId = friendUserId;
                $('#name-user-receive').text(username);
               // $("#name-user-receive").attr('href', linkProfile);
                processAjaxData(username, "/" + username);  
                totalMessage = 0;
                //thay doi noi dung chat box sau do getOld message vao chat box
                getOldMessage(friendUserId);
                $("#right-content #conversation-info").removeClass("hidden");
            }

          
            
            /**
            * lay tat ca tin nhan luu tren middware giua 2 user
            * userId: value duoc luu tren hidden cua view
            */
            function getOldMessage(friendUserId){

                  receiverId = friendUserId;
                  $.ajax({
                        type: "POST",
                        async: true,
                        data: {userName: $("#userId").val(), userNameOfReceiver:  receiverId, totalMessageOfUser: totalMessage },
                        url: "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+"/old_message",
                        success: (function(response) {
                            if (response !== 'undefined'){
                                  for(var i = response.length-1;i>=0;i--){
                                         var friendListArray = JSON.parse(friendList);
                                           var userSendingOnViews ;
                                          var selectorImgSrc;  
                                          var imgSrc;

                                         if(response[i].from== $("#userId").val()){
                                            userSendingOnViews = $('#displayNameViews').text();
                                          }else{
                                            //message from friend
                                           var selectorNameSending = "#friend"+friendListArray.indexOf(response[i].from)+"  #friend-name";                                    
                                           userSendingOnViews =  $(selectorNameSending).text();
                                            selectorImgSrc = "#friend"+friendListArray.indexOf(response[i].from)+"  #friend-image-avatar";  
                                            imgSrc  = $(selectorImgSrc).attr("src");
                                           }

                                        var contentChat = emotion_parse(response[i].content);  
                                        //  var messageItem  = '<div class="itemdiv dialogdiv"> '+ '<div class="user"><img alt="Users avatar" '
                                        // +'src="http://s240.avatar.talk.zdn.vn/default"/></div>'
                                        // +'  <div class="body"> <div class="time"><i class="icon-time"></i> <span class="orange">'+response[i].time+'</span></div>'
                                        // + ' <div class ="name"><a href="#">'+ userSendingOnViews + '</a> </div>'    

                                        // +'<div class="text">'+contentChat+' </div> </div>  </div>';
                                         var messageItem = "";

                                         if(response[i].type  == "text"){
                                             if($("#userId").val() == response[i].from){
                                                    messageItem += "<ul><div class='conversation-item  left'>"
                                                   
                                                    messageItem += "    <div class='wrapper-bubble'>"
                                                    messageItem += "        <div class='msg-bubble-left'>"
                                                    messageItem += "            <div class='msg-content'>"
                                                    messageItem +=                 contentChat
                                                    messageItem += "             </div>"
                                                    messageItem +=             "<div class='msg-status-left'>";
                                                    messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+response[i].time+"<div>"
                                                    messageItem +=             "</div>"
                                                    messageItem +=             "</div>"
                                                    messageItem += "        </div>"
                                                    messageItem += "   </div>"
                                                    messageItem += "</div></ul>"
                                                    messageItem += "<div class='cb'><div>"
                                            }else{
                                                    //user friend
                                                     messageItem += "<ul><div class='conversation-item  right'>"
                                                    messageItem += "    <div class='wrapper-ava'>"
                                                    messageItem +=         "<img src='" + imgSrc  + "' class='msg-ava'/>"
                                                    messageItem +=      "</div>"
                                                    messageItem += "    <div class='wrapper-bubble'>"
                                                    messageItem += "        <div class='msg-bubble'>"
                                                    messageItem += "            <div class='msg-content'>"
                                                    messageItem +=                 contentChat
                                                    messageItem += "             </div>"
                                                    messageItem +=             "<div class='msg-status'>";
                                                    messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+response[i].time+"<div>"
                                                    messageItem +=             "</div>"
                                                    messageItem +=             "</div>"
                                                    messageItem += "        </div>"
                                                    messageItem += "   </div>"
                                                    messageItem += "</div></ul>"
                                                    messageItem += "<div class='cb'><div>"
                                            }
                                      }else if(response[i].type =="image"){

                                            if($("#userId").val() == response[i].from){
                                                messageItem += "<ul><div class='conversation-item  left'>"
                                                messageItem += "    <div class='wrapper-bubble'>"
                                                messageItem += "        <div class='msg-bubble-left'>"
                                                messageItem += "            <div class='msg-content text ace-thumbnails'> "
                                                messageItem +=                 ' <a href="' + contentChat +'" data-rel="colorbox"> '+'<img src = "'+contentChat+'" width="60" height="60"> '
                                                messageItem += "            </a></div>"
                                                messageItem +=             "<div class='msg-status-image-left'>";
                                                messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+responseData.time+"<div>"
                                                messageItem +=             "</div>"
                                                messageItem +=             "</div>"
                                                messageItem += "        </div>"
                                                messageItem += "   </div>"
                                                messageItem += "</div></ul>"
                                                messageItem += "<div class='cb'><div>"
                                            }else{
                                                    //user friend
                                                     messageItem += "<ul><div class='conversation-item  right'>"
                                                    messageItem += "    <div class='wrapper-ava'>"
                                                    messageItem +=         "<img src='" + imgSrc  + "' class='msg-ava'/>"
                                                    messageItem +=      "</div>"
                                                    messageItem += "    <div class='wrapper-bubble'>"
                                                    messageItem += "        <div class='msg-bubble'>"
                                                     messageItem += "            <div class='msg-content text ace-thumbnails'> "
                                                    messageItem +=                 ' <a href="' + contentChat +'" data-rel="colorbox"> '+'<img src = "'+contentChat+'" width="60" height="60"> '
                                                     messageItem += "            </a></div>"
                                                    messageItem +=             "<div class='msg-status'>";
                                                    messageItem +=                 "<div style='position: absolute; right:10px; bottom:2px'  class='status'>"+response[i].time+"<div>"
                                                    messageItem +=             "</div>"
                                                    messageItem +=             "</div>"
                                                    messageItem += "        </div>"
                                                    messageItem += "   </div>"
                                                    messageItem += "</div></ul>"
                                                    messageItem += "<div class='cb'><div>"
                                            }







                                                
                                      }
                                        $('#old-link').after(messageItem);

                                        totalMessage++;
                                    }
                              
                            }else{

                                    //error
                            }
                        }),
                        
                    });

            }
            
        /**
        * goi den server business kiem tra trang thai online cua friend list
        * return list boolean trang thai user theo index trong friend list
        */
        function checkFriendOnline(){
             $.ajax({
                type: "POST",
                async: true,
                data: {friendList : friendList},
                url: "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+"/friend_online",
                success: (function(response) {
                    if (response !== 'undefined'){
                      //return ket qua success     
                      for(var i =0 ; i < response.length;i++){
                            var selectorFriendStatus = "#friend"+i+"  #user-status";
                            if(response[i]==true){
                                //online                           
                               // $(selectorFriendStatus).text("Online");
                               $(selectorFriendStatus).removeClass("hidden");
                            }else{
                                //offline
                               // $(selectorFriendStatus).text("");
                               if($(selectorFriendStatus).hasClass("hidden") == false){
                                   $(selectorFriendStatus).addClass("hidden");
                               }
                            }
                       }
                    }else{

                        //error
                    }
                }),
                error: (function(error) {
                           // alert("Error!!! check friend online ");
                    })

            });
        };  


        
       

        function getDisplayName() {
            $.ajax({
                        type: "GET",
                        async: true,
                        data: {userId:$("#userId").val(), oauthCode: CONFIG.OAUTH_CODE, option:'displayName'},
                        url: "http://"+CONFIG.BUSINESS_HOST +":"+CONFIG.BUSINESS_PORT+  "/profile",
                        success: (function(response) {
                            if (response !== 'undefined'){
                             $('#displayNameViews').text(response.displayName);
                              //change image avatar of user
                              $("#avatarUser").attr('src', response.avatar);

                            }else{
                                alert("Error1!!! getDisplayName");
                            }
                        }),
                        error: (function(error) {
                            alert("Error!!! getDisplayName");
                    })
                });
        }
      

     
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model.valueobject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.eclipse.jetty.continuation.Continuation;

/**
 *
 * @author tript
 */
public class UserChatPOJO {

    private String userId;
    private Continuation continuation;
    private List<MessagePOJO> listMessage = new LinkedList<>();
    private  String oauthCode;
    public String getOauthCode() {
        return oauthCode;
    }

    public void setOauthCode(String oauthCode) {
        this.oauthCode = oauthCode;
    }
    public void addNewMessage(MessagePOJO message) {
        listMessage.add(message);
    }

    public MessagePOJO getMessage(int index) {
        return listMessage.get(index);
    }

    public int getListMessageSize() {
        return listMessage.size();
    }

    public void clearMessageList() {
        listMessage.clear();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void User(String userId) {
        this.userId = userId;
    }

    public Continuation getContinuation() {
        return continuation;
    }

    public void setContinuation(Continuation continuation) {
        this.continuation = continuation;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model.valueobject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author tript Create on 3:03:01 PM Dec 17, 2013
 */
public class MessagePOJO implements java.io.Serializable{

    private final String sendingUserId;
    private final String receiveUserId;
    private final String content;
    private final String dateTimeSend;
    private  String type;

   
    private final long messageId;
    private static final long serialVersionUID = 1L;
    
    
     public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getSendingUserId() {
        return sendingUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public String getContent() {
        return content;
    }

    public String getDateTimeSend() {
        return dateTimeSend;
    }

    public long getMessageId() {
        return messageId;
    }
    
    public MessagePOJO(String sendingUserId, String receiveUserId, String content, String dateTimeSend, long messageId,String type) {
        this.sendingUserId = sendingUserId;
        this.receiveUserId = receiveUserId;
        this.content = content;
        this.dateTimeSend = dateTimeSend;
        this.messageId = messageId;
        this.type = type;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

}

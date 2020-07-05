/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver.model;

import java.io.Serializable;

/**
 *
 * @author harshit
 */
public class Message implements Serializable {
    private String sender,receiver;
    private String msg;
    private String senderuid,receiveruid;

    public Message(String sender, String receiver, String msg, String senderuid, String receiveruid) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.senderuid = senderuid;
        this.receiveruid = receiveruid;
    }

    public String getSenderuid() {
        return senderuid;
    }

    public String getReceiveruid() {
        return receiveruid;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMsg() {
        return msg;
    }
    
    
}

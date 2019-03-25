/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;
import java.util.Date;

public class Message {

    public Message() {
    }

    private int id;
    private String senderUsername;
    private String recieverUsername;
    private String message;
    private Timestamp date;
    private int isRead;

    public Message(int id, String senderUsername, String recieverUsername, String message, Timestamp date, int isRead) {
        this.id = id;
        this.senderUsername = senderUsername;
        this.recieverUsername = recieverUsername;
        this.message = message;
        this.date = date;
        this.isRead = isRead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getRecieverUsername() {
        return recieverUsername;
    }

    public void setRecieverUsername(String recieverUsername) {
        this.recieverUsername = recieverUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "sender:" + senderUsername + "  reciever:" + recieverUsername + "  message:" + message + "  date:" + date + "\n";
    }
    public String toString2(){
        return "New messages from "+senderUsername;
    }
}

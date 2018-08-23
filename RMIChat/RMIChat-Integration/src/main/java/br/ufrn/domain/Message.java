package br.ufrn.domain;

import java.util.Date;

public class Message {

    public String groupId;
    public String userName;
    public String content;
    public Date sendTime;

    private Message(String groupId, String userName, String content) {
        this.groupId = groupId;
        this.userName = userName;
        this.content = content;
        this.sendTime = new Date();
    }

    public static Message createMessage(String groupId, String userName, String content) {
        return new Message(groupId, userName, content);
    }

    public boolean fromUser(String userName) {
        return userName.equals(userName);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public Date getSendTime() {
        return sendTime;
    }
}

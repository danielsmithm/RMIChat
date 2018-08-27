package br.ufrn.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Message implements Serializable {

    public String groupId;
    public String groupName;
    public String authorUserName;
    public String content;
    public Date sendTime;

    private Message(String groupId, String groupName, String authorUserName, String content) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.authorUserName = authorUserName;
        this.content = content;
        this.sendTime = new Date();
    }

    public static Message createMessage(String groupId, String groupName, String userName, String content) {
        return new Message(groupId, groupName, userName, content);
    }

    public boolean fromUser(String userName) {
        return userName.equals(userName);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public String getContent() {
        return content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(groupId, message.groupId) &&
                Objects.equals(authorUserName, message.authorUserName) &&
                Objects.equals(content, message.content) &&
                Objects.equals(sendTime, message.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, authorUserName, content, sendTime);
    }
}

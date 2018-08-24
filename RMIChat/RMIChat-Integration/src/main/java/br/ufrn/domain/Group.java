package br.ufrn.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group implements Serializable{
   private static int idSequence = 0;

   private String id;
   private String name;
   private Set<User> users;
   private Set<Message> messages;

    private Group(String name, User creator) {
        this.id = String.valueOf(++idSequence);
        this.name = name;
        this.users = new HashSet<>();
        this.messages = new HashSet<>();

        users.add(creator);
    }

    public static Group createGroup(String name, User creator) {
        return new Group(name,creator);
    }

    public boolean containsUser(String userName) {
        return users.stream().anyMatch(user -> user.hasUserName(userName));
    }

    public void joinUserToGroup(User user) {
        users.add(user);
    }

    public void leaveGroup(User user) {
        users.remove(user);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

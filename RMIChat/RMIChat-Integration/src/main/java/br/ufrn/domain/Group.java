package br.ufrn.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group implements Serializable{
   private static int idSequence = 0;

    /**
     * Id do grupo.
     */
   private String id;

    /**
     * Nome do grupo.
     */
   private String name;


    /**
     * Usuários do grupo.
     */
    private Set<User> users;

    /**
     * Mensagens cadastradas no grupo.
     */
    private Set<Message> messages;

    private Group(String name, User creator) {
        this.id = String.valueOf(++idSequence);
        this.name = name;
        this.users = new HashSet<>();
        this.messages = new HashSet<>();

        users.add(creator);
    }

    /**
     * Método fábrica estático para o grupo.
     *
     * @param name
     * @param creator
     * @return
     */
    public static Group createGroup(String name, User creator) {
        return new Group(name,creator);
    }

    /**
     * Retorna se o usuário está no grupo.
     *
     * @param userName
     * @return
     */
    public boolean containsUser(String userName) {
        return users.stream().anyMatch(user -> user.hasUserName(userName));
    }

    /**
     * Adiciona o usuário ao grupo.
     *
     * @param user
     */
    public void joinUserToGroup(User user) {
        users.add(user);
    }

    /**
     * Retira o usuário do grupo.
     *
     * @param user
     */
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

    @Override
    public String toString() {
        return String.format("%s - %s ",getId(),getName());
    }
}

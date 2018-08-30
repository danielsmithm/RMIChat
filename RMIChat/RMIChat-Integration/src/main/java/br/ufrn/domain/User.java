package br.ufrn.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe que representa um usuário do sistema.
 */
public class User implements Serializable {
    private String userName;

    private User(String userName) {
        this.userName = userName;
    }

    /**
     * Método fábrica estático para usuário.
     *
     * @param userName
     * @return
     * @throws IllegalArgumentException
     */
    public static User createUser(String userName) throws IllegalArgumentException{
        if(userName == null || userName.isEmpty()){
            throw new IllegalArgumentException("The username is required.");
        }

        return new User(userName);
    }

    public String getUserName() {
        return userName;
    }

    public boolean hasUserName(String userName) {
        return this.userName.equals(userName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}

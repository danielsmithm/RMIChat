package br.ufrn.repository;

import br.ufrn.domain.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserRepository implements Serializable {

    public Set<User> users;

    public UserRepository() {
        this.users = new HashSet<>();
    }

    public boolean existsUserName(String userName){
        return users.stream()
                .filter(user -> user.hasUserName(userName)).count() != 0;
    }

    public void addUser(User user) {
        users.add(user);
    }
}

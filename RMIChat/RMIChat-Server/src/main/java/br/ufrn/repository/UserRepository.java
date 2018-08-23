package br.ufrn.repository;

import br.ufrn.domain.User;

import java.util.HashSet;
import java.util.Set;

public class UserRepository {

    public Set<User> users;

    public UserRepository() {
        this.users = new HashSet<>();
    }

    public boolean existsUserName(String userName){
        return users.stream()
                .anyMatch(user -> user.hasUserName(userName));
    }

    public void addUser(User user) {
        users.add(user);
    }
}

package br.ufrn.repository;

import br.ufrn.domain.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/***
 * Repositório para a entidade usuário.
 */
public class UserRepository implements Serializable {

    /**
     * Usuários armazenados.
     */
    public Set<User> users;

    public UserRepository() {
        this.users = new HashSet<>();
    }

    /**
     * Retorna se existe um usuário cadastrado com este username.
     * @param userName
     * @return
     */
    public boolean existsUserName(String userName){
        return users.stream()
                .filter(user -> user.hasUserName(userName)).count() != 0;
    }

    /**
     * Adiciona o usuário ao repositório.
     *
     * @param user
     */
    public void addUser(User user) {
        users.add(user);
    }
}

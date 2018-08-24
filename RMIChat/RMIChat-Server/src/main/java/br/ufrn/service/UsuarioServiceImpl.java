package br.ufrn.service;

import br.ufrn.domain.User;
import br.ufrn.exceptions.UserAlreadyExistsException;

import br.ufrn.repository.UserRepository;

import java.io.Serializable;
import java.util.logging.Logger;

public class UsuarioServiceImpl implements UserService,Serializable {

    private UserRepository userRepository;

    public UsuarioServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username) throws UserAlreadyExistsException {

        if(userRepository.existsUserName(username)){
            throw new UserAlreadyExistsException("Already exists an user with the request username");
        }

        User user = User.createUser(username);

        userRepository.addUser(user);

        return user;
    }

}

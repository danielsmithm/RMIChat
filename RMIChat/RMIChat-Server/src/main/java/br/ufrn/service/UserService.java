package br.ufrn.service;

import br.ufrn.domain.User;
import br.ufrn.exceptions.UserAlreadyExistsException;

import java.io.Serializable;

public interface UserService{
    User register(String username) throws UserAlreadyExistsException;
}

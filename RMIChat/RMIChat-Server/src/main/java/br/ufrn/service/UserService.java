package br.ufrn.service;

import br.ufrn.domain.User;
import br.ufrn.exceptions.UserAlreadyExistsException;

import java.io.Serializable;

/**
 * Interfaca para o serviço de usuários.
 */
public interface UserService{
    /**
     * Registra um usuário.
     *
     * @param username
     * @return
     * @throws UserAlreadyExistsException
     */
    User register(String username) throws UserAlreadyExistsException;
}

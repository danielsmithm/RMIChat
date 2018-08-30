package br.ufrn.exceptions;

/**
 * Exceção lançada quando é tentado cadastra um usuário que já existe na aplicação.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}

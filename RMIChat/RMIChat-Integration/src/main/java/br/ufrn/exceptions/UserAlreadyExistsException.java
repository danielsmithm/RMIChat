package br.ufrn.exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String s) {
        super(s);
    }
}

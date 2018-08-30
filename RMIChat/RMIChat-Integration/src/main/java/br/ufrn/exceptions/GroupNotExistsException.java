package br.ufrn.exceptions;

/**
 * Exceção lançada quando for efetuada alguma operação com um grupo que não existe.
 */
public class GroupNotExistsException extends Exception {
    public GroupNotExistsException(String message) {
        super(message);
    }
}

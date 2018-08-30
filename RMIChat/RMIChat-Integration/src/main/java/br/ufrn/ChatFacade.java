package br.ufrn;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatFacade extends Remote, Serializable {

    /**
     * Cria um grupo.
     *
     * @param name
     * @param creator
     * @throws RemoteException
     */
    void createGroup(String name, User creator) throws RemoteException;

    /**
     * Busca um grupo pelo seu id.
     *
     * @param groupId
     * @return
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    Group findGroupById(String groupId) throws RemoteException, GroupNotExistsException;

    /**
     * Associa um usuário ao grupo.
     *
     * @param groupId
     * @param user
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    void joinGroup(String groupId, User user) throws RemoteException, GroupNotExistsException;

    /**
     * Retira um usuário de um grupo.
     *
     * @param groupId
     * @param user
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    void quitGroup(String groupId, User user) throws RemoteException, GroupNotExistsException;

    /**
     * Envia uma mensagem para um grupo.
     *
     * @param groupId
     * @param username
     * @param messageContent
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    void sendMessageToGroup(String groupId, String username, String messageContent) throws RemoteException, GroupNotExistsException;

    /**
     * Lista os grupos que o usuário está associado.
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    List<Group> listGroups(String username) throws RemoteException;

    /**
     * Registra o usuário no sistema.
     *
     * @param username
     * @return
     * @throws RemoteException
     * @throws UserAlreadyExistsException
     */
    User register(String username) throws RemoteException, UserAlreadyExistsException;

    /**
     * Registra um messageHandler para permitir a notificação assíncrona de mensagens para esse usuário.
     *
     * @param handler
     * @throws RemoteException
     */
    void registerMessageHandler(MessageHandler handler) throws RemoteException;
}

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

    void createGroup(String name, User creator) throws RemoteException;

    Group findGroupById(String groupId) throws RemoteException, GroupNotExistsException;

    void joinGroup(String groupId, User user) throws RemoteException, GroupNotExistsException;

    void quitGroup(String groupId, User user) throws RemoteException, GroupNotExistsException;

    void sendMessageToGroup(String groupId, String username, String messageContent) throws RemoteException, GroupNotExistsException;

    List<Group> listGroups(String username) throws RemoteException;

    User register(String username) throws RemoteException, UserAlreadyExistsException;

    void registerMessageHandler(MessageHandler handler) throws RemoteException;
}

package br.ufrn;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.factory.ServiceFactory;
import br.ufrn.service.GroupServiceImpl;
import br.ufrn.service.MessagePublisher;
import br.ufrn.service.UserService;

import javax.xml.ws.Service;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChatFacadeImpl extends UnicastRemoteObject implements ChatFacade {

    GroupServiceImpl groupService;
    UserService userService;
    MessagePublisher messagePublisher;

    public ChatFacadeImpl(int port) throws RemoteException {
        super(port);
        ServiceFactory instance = ServiceFactory.getInstance();
        groupService = instance.getGroupService();
        userService = instance.getUserService();
        messagePublisher = instance.getMessagePublisher();
    }


    @Override
    public void createGroup(String name, User creator) throws RemoteException {
        groupService.createGroup(name,creator);
    }

    @Override
    public Group findGroupById(String groupId) throws RemoteException {
        return groupService.findGroupById(groupId);
    }

    @Override
    public void joinGroup(String groupId, User user) throws RemoteException, GroupNotExistsException {
        groupService.joinGroup(groupId, user);
    }

    @Override
    public void quitGroup(String groupId, User user) throws RemoteException, GroupNotExistsException {
        groupService.quitGroup(groupId,user);
    }

    @Override
    public void sendMessageToGroup(String groupId, String username, String messageContent) throws RemoteException, GroupNotExistsException {
        groupService.sendMessageToGroup(groupId,username,messageContent);
    }

    @Override
    public List<Group> listGroups(String username) throws RemoteException {
        return groupService.listGroups(username);
    }

    @Override
    public User register(String username) throws RemoteException, UserAlreadyExistsException {
        return userService.register(username);
    }

    @Override
    public void registerMessageHandler(MessageHandler handler) throws RemoteException {
        messagePublisher.registerMessageHandler(handler);
    }

}

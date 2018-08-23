package br.ufrn;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.service.GroupService;
import br.ufrn.service.MessagePublisher;
import br.ufrn.service.UserService;

import java.rmi.RemoteException;
import java.util.List;

public class ChatFacadeImpl implements ChatFacade {

    private GroupService groupService;
    private MessagePublisher messagePublisher;
    private UserService userService;

    public ChatFacadeImpl(GroupService groupService, MessagePublisher messagePublisher, UserService userService) {
        this.groupService = groupService;
        this.messagePublisher = messagePublisher;
        this.userService = userService;
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

}

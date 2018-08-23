package br.ufrn.service;

import br.ufrn.domain.Group;
import br.ufrn.domain.Message;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;

import br.ufrn.repository.GroupRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private MessagePublisher messagePublisher;

    public GroupServiceImpl(GroupRepository groupRepository, MessagePublisher messagePublisher) {
        this.groupRepository = groupRepository;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void createGroup(String name, User creator) {

        Group group = Group.createGroup(name,creator);

        groupRepository.add(group);
    }

    @Override
    public Group findGroupById(String groupId){
        return groupRepository.findGroupById(groupId);
    }

    @Override
    public void joinGroup(String groupId, User user) throws GroupNotExistsException {
        Group groupToJoin = groupRepository.findGroupById(groupId);

        if(groupToJoin == null){
            throw new GroupNotExistsException("The group not exists.");
        }

        groupToJoin.joinUserToGroup(user);
    }

    @Override
    public void quitGroup(String groupId, User user) throws GroupNotExistsException {
        Group groupToQuit = groupRepository.findGroupById(groupId);

        if(groupToQuit == null){
            throw new GroupNotExistsException("The group not exists.");
        }

        groupToQuit.leaveGroup(user);

    }

    @Override
    public void sendMessageToGroup(String groupId, String username, String messageContent) throws GroupNotExistsException {
        Group groupToSendMessage = groupRepository.findGroupById(groupId);

        if(groupToSendMessage == null){
            throw new GroupNotExistsException("The group not exists.");
        }

        Message message = Message.createMessage(groupId,username,messageContent);

        groupToSendMessage.addMessage(message);

        Set<String> usersToNotify = groupToSendMessage.getUsers()
                .stream()
                .map(user -> user.getUserName())
                .collect(Collectors.toSet());

        messagePublisher.publishMessageCreation(message, usersToNotify);

    }

    @Override
    public List<Group> listGroups(String username) {
        return groupRepository.findGroupsByUser(username);
    }
}

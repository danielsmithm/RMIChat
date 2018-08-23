package br.ufrn.service;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;

import java.util.List;

public interface GroupService {
    void createGroup(String name, User creator);

    Group findGroupById(String groupId);

    void joinGroup(String groupId, User user) throws GroupNotExistsException;

    void quitGroup(String groupId, User user) throws GroupNotExistsException;

    void sendMessageToGroup(String groupId, String username, String messageContent) throws GroupNotExistsException;

    List<Group> listGroups(String username);
}

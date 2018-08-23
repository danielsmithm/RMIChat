package br.ufrn.repository;

import br.ufrn.domain.Group;
import br.ufrn.domain.Message;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupRepository {

    private Set<Group> groups;

    public GroupRepository() {
        this.groups = new HashSet<>();
    }

    public Group findGroupById(String groupId){
        return groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst()
                .get();
    }

    public void add(Group group){
        this.groups.add(group);
    }

    public List<Message> findMessagesByUser(String userName){

        return groups.stream()
                .map(group -> group.getMessages())
                .flatMap(messages -> messages.stream())
                .filter(message -> message.fromUser(userName))
                .collect(Collectors.toList());

    }

    public List<Group> findGroupsByUser(String userName){

        return groups.stream()
                .filter(group -> group.containsUser(userName))
                .collect(Collectors.toList());
    }

}

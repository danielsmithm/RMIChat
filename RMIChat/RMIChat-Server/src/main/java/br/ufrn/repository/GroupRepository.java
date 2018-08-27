package br.ufrn.repository;

import br.ufrn.domain.Group;
import br.ufrn.domain.Message;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupRepository implements Serializable {

    private Set<Group> groups;

    private GroupRepository() {
        this.groups = new HashSet<>();
    }

    public static GroupRepository createGroupRepository() {
        return new GroupRepository();
    }

    public Group findGroupById(String groupId){
        Optional<Group> first = groups.stream()
                .filter(group -> group.getId().equals(groupId))
                .findFirst();

        if(first.isPresent()){
            return first.get();
        }

        //If the element don't exists
        return null;
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

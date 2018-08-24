package br.ufrn.factory;

import br.ufrn.repository.GroupRepository;
import br.ufrn.repository.UserRepository;
import br.ufrn.service.*;

public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    private final UsuarioServiceImpl usuarioService;
    private final MessagePublisher messagePublisher;
    private final GroupServiceImpl groupService;

    public ServiceFactory(){
        this.usuarioService = new UsuarioServiceImpl(new UserRepository());
        this.messagePublisher = new MessagePublisher();
        this.groupService = new GroupServiceImpl(GroupRepository.createGroupRepository(), messagePublisher);
    }

    public static synchronized ServiceFactory getInstance(){
        return instance;
    }

    public UsuarioServiceImpl getUserService() {
        return usuarioService;
    }

    public MessagePublisher getMessagePublisher() {
        return messagePublisher;
    }

    public GroupServiceImpl getGroupService() {
        return groupService;
    }

}

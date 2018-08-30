package br.ufrn.service;

import br.ufrn.repository.GroupRepository;
import br.ufrn.repository.UserRepository;

/**
 * Classe Fábrica para os serviços necessários na aplicação.
 * Para garantir a instanciação externamente por meio da fábrica, os construtores das classes deve possuir o modificador de acesso protected.
 */
public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    /**
     * Instância única da aplicação para o serviço de usuários.
     */
    private final UserServiceImpl userService;

    /**
     * Instância única da aplicação para o publicador de mensagens.
     */
    private final MessagePublisher messagePublisher;

    /**
     * Instância única da aplicação para o serviço de grupos.
     */
    private final GroupServiceImpl groupService;

    public ServiceFactory(){
        this.userService = new UserServiceImpl(new UserRepository());
        this.messagePublisher = new MessagePublisherImpl();
        this.groupService = new GroupServiceImpl(GroupRepository.createGroupRepository(), messagePublisher);
    }

    public static synchronized ServiceFactory getInstance(){
        return instance;
    }

    public UserServiceImpl getUserService() {
        return userService;
    }

    public MessagePublisher getMessagePublisher() {
        return messagePublisher;
    }

    public GroupServiceImpl getGroupService() {
        return groupService;
    }

}

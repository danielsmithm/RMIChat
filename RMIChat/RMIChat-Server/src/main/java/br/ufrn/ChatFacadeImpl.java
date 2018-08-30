package br.ufrn;

import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;
import br.ufrn.service.ServiceFactory;
import br.ufrn.service.GroupService;
import br.ufrn.service.MessagePublisher;
import br.ufrn.service.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementação da fachada da aplicação.
 */
public class ChatFacadeImpl extends UnicastRemoteObject implements ChatFacade {

    /**
     * Dependência para o serviço de grupos.
     */
    private GroupService groupService;

    /**
     * Dependencia para o serviço de usuários.
     */
    private UserService userService;

    /**
     * Dependência para o publicador de mensagens.
     */
    private MessagePublisher messagePublisher;

    /**
     * Armazena o usuário padrão da aplicação servidor.
     */
    private final User serverUser;

    /**
     * Cria um servidor de chat na porta passada por parâmetro.
     *
     * @param port
     * @throws RemoteException
     */
    public ChatFacadeImpl(int port) throws RemoteException {
        super(port);
        locateDependencies();

        this.serverUser = registerServerUser(this);
        createBroadcastGroup(this, serverUser);
    }

    /**
     * Busca as dependências utilizando o service factory.
     */
    private void locateDependencies() {
        ServiceFactory instance = ServiceFactory.getInstance();
        this.groupService = instance.getGroupService();
        this.userService = instance.getUserService();
        this.messagePublisher = instance.getMessagePublisher();
    }

    /**
     * Cria um grupo.
     *
     * @param name
     * @param creator
     * @throws RemoteException
     */
    @Override
    public void createGroup(String name, User creator) throws RemoteException {
        groupService.createGroup(name,creator);
    }

    /**
     * Busca um grupo pelo seu id.
     *
     * @param groupId
     * @return
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    @Override
    public Group findGroupById(String groupId) throws RemoteException, GroupNotExistsException {
        return groupService.findGroupById(groupId);
    }

    /**
     * Associa um usuário ao grupo.
     *
     * @param groupId
     * @param user
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    @Override
    public void joinGroup(String groupId, User user) throws RemoteException, GroupNotExistsException {
        groupService.joinGroup(groupId, user);
        groupService.sendMessageToGroup(groupId,serverUser.getUserName(),String.format("%s joined the group.",user.getUserName()));
    }

    /**
     * Retira um usuário de um grupo.
     *
     * @param groupId
     * @param user
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    @Override
    public void quitGroup(String groupId, User user) throws RemoteException, GroupNotExistsException {
        groupService.quitGroup(groupId,user);
        groupService.sendMessageToGroup(groupId,serverUser.getUserName(),String.format("%s left.",user.getUserName()));
    }

    /**
     * Envia uma mensagem para um grupo.
     *
     * @param groupId
     * @param username
     * @param messageContent
     * @throws RemoteException
     * @throws GroupNotExistsException
     */
    @Override
    public void sendMessageToGroup(String groupId, String username, String messageContent) throws RemoteException, GroupNotExistsException {
        groupService.sendMessageToGroup(groupId,username,messageContent);
    }

    /**
     * Lista os grupos que o usuário está associado.
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    @Override
    public List<Group> listGroups(String username) throws RemoteException {
        return groupService.listGroups(username);
    }

    /**
     * Registra o usuário no sistema.
     *
     * @param username
     * @return
     * @throws RemoteException
     * @throws UserAlreadyExistsException
     */
    @Override
    public User register(String username) throws RemoteException, UserAlreadyExistsException {
        return userService.register(username);
    }

    /**
     * Registra um messageHandler para permitir a notificação assíncrona de mensagens para esse usuário.
     *
     * @param handler
     * @throws RemoteException
     */
    @Override
    public void registerMessageHandler(MessageHandler handler) throws RemoteException {
        messagePublisher.registerMessageHandler(handler);
    }

    @Override
    public List<Group> listGroups() throws RemoteException {
        return groupService.listGroups();
    }

    /**
     * Cria o grupo de broadcast padrão.
     *
     * @param chatFacade
     * @param serverUser
     * @throws RemoteException
     */
    private static void createBroadcastGroup(ChatFacade chatFacade, User serverUser) throws RemoteException {
        chatFacade.createGroup("Broadcast",serverUser);
    }

    /**
     * Cria o usuário padrão do servidor.
     *
     * @param chatFacade
     * @return
     * @throws RemoteException
     */
    private User registerServerUser(ChatFacade chatFacade) throws RemoteException {
        try{
            getLogger().info("Registrando usuário do servidor");
            User activeUser = chatFacade.register("server");
            getLogger().info("Usuário do servidor registrado com sucesso.");

            return activeUser;
        }catch (UserAlreadyExistsException e) {
            getLogger().log(Level.SEVERE,"Não foi possível criar o usuario do servidor. A aplicação abortou.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Cria o logger.
     *
     * @return
     */
    private Logger getLogger(){
        return Logger.getLogger(ServerApplication.class.getName());
    }

}

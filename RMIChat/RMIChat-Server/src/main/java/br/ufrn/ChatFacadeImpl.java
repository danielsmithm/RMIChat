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

public class ChatFacadeImpl extends UnicastRemoteObject implements ChatFacade {

    private GroupService groupService;
    private UserService userService;
    private MessagePublisher messagePublisher;
    private final User serverUser;


    public ChatFacadeImpl(int port) throws RemoteException {
        super(port);
        locateDependencies();

        this.serverUser = registerServerUser(this);
        createBroadcastGroup(this, serverUser);
    }

    private void locateDependencies() {
        ServiceFactory instance = ServiceFactory.getInstance();
        this.groupService = instance.getGroupService();
        this.userService = instance.getUserService();
        this.messagePublisher = instance.getMessagePublisher();
    }

    @Override
    public void createGroup(String name, User creator) throws RemoteException {
        groupService.createGroup(name,creator);
    }

    @Override
    public Group findGroupById(String groupId) throws RemoteException, GroupNotExistsException {
        return groupService.findGroupById(groupId);
    }

    @Override
    public void joinGroup(String groupId, User user) throws RemoteException, GroupNotExistsException {
        groupService.joinGroup(groupId, user);
        groupService.sendMessageToGroup(groupId,serverUser.getUserName(),String.format("%s entrou no grupo.",user.getUserName()));
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

    private static void createBroadcastGroup(ChatFacade chatFacade, User serverUser) throws RemoteException {
        chatFacade.createGroup("Broadcast",serverUser);
    }

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

    private Logger getLogger(){
        return Logger.getLogger(ServerApplication.class.getName());
    }

}

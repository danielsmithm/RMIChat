package br.ufrn;

import br.ufrn.configuration.RmiConfiguration;
import br.ufrn.domain.User;
import br.ufrn.exceptions.UserAlreadyExistsException;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerApplication {

    private static Logger logger = Logger.getLogger(ServerApplication.class.getName());

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
        logger.log(Level.INFO,"Iniciando servidor de chat.");

        ChatFacade chatFacade = bindApplicationFacade();

        User serverUser = registerServerUser(chatFacade);
        createBroadcastGroup(chatFacade, serverUser);

        logger.log(Level.INFO,"Servidor de chat iniciado com sucesso.");
    }

    private static void createBroadcastGroup(ChatFacade chatFacade, User serverUser) throws RemoteException {
        chatFacade.createGroup("Broadcast",serverUser);
    }

    private static User registerServerUser(ChatFacade chatFacade) throws RemoteException {

        try{
            logger.info("Registrando usuário do servidor");
            User activeUser = chatFacade.register("server");
            logger.info("Usuário do servidor registrado com sucesso.");

            return activeUser;
        }catch (UserAlreadyExistsException e) {
            logger.log(Level.SEVERE,"Não foi possível criar o usuario do servidor. A aplicação abortou.");
            throw new RuntimeException(e);
        }

    }

    private static ChatFacade bindApplicationFacade() throws RemoteException, MalformedURLException {
        logger.info("Efetuando o binding da fachada da aplicação.");

        System.setProperty("sun.rmi.registry.registryFilter", "java.**;br.ufrn.**");
        LocateRegistry.createRegistry(RmiConfiguration.RMI_PORT);

        ChatFacade chatFacade = createChatFacade();
        Naming.rebind(RmiConfiguration.URL_CHAT_FACADE, chatFacade);

        logger.info("Bind efetuado com sucesso.");

        return chatFacade;
    }

    private static ChatFacade createChatFacade() throws RemoteException {
        return new ChatFacadeImpl(RmiConfiguration.RMI_PORT);
    }

}

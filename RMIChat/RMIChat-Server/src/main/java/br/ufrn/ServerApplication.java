package br.ufrn;

import br.ufrn.configuration.RmiConfiguration;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ExportException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe príncipal do servidor de chat.
 */
public class ServerApplication {

    private static Logger logger = Logger.getLogger(ServerApplication.class.getName());

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {

        try {
            logger.log(Level.INFO,"Iniciando servidor de chat.");
            ChatFacade chatFacade = bindApplicationFacade();
            logger.log(Level.INFO,"Servidor de chat iniciado com sucesso.");
        }catch (ExportException ex){
            logger.log(Level.SEVERE,ex.getMessage(),ex);
        }

    }

    /**
     * Efetua o binding da fachada de aplicação no RMI registry.
     *
     * @return
     * @throws RemoteException
     * @throws MalformedURLException
     */
    private static ChatFacade bindApplicationFacade() throws RemoteException, MalformedURLException {
        logger.info("Efetuando o binding da fachada da aplicação.");

        System.setProperty("sun.rmi.registry.registryFilter", "java.**;br.ufrn.**");
        LocateRegistry.createRegistry(RmiConfiguration.RMI_PORT);

        ChatFacade chatFacade = createChatFacade();
        Naming.rebind(RmiConfiguration.URL_CHAT_FACADE, chatFacade);

        logger.info("Bind efetuado com sucesso.");

        return chatFacade;
    }

    /**
     * Método fábrica da fachada de aplicação.
     *
     * @return
     * @throws RemoteException
     */
    private static ChatFacade createChatFacade() throws RemoteException {
        return new ChatFacadeImpl(RmiConfiguration.RMI_PORT);
    }

}

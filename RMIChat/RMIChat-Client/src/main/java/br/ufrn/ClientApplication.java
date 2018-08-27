package br.ufrn;

import br.ufrn.configuration.RmiConfiguration;
import br.ufrn.consoleapp.ConsoleChatClient;
import br.ufrn.utils.ServiceLocator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApplication {

    public static void main(String[] args) {

        try {
            ConsoleChatClient consoleChatClient = new ConsoleChatClient(ServiceLocator.lookupFor(RmiConfiguration.URL_CHAT_FACADE));

            consoleChatClient.run();

        } catch (RemoteException e) {
            handleException(e);
        } catch (NotBoundException e) {
            handleException(e);
        } catch (MalformedURLException e) {
            handleException(e);
        }

    }

    private static void handleException(Exception ex) {
        Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
    }
}

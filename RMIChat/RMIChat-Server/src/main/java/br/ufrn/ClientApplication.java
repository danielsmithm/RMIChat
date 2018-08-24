package br.ufrn;

import br.ufrn.configuration.RmiConfiguration;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApplication {

    public static void main(String[] args) {

        try {
            ChatClient chatClient = new ChatClient(ServiceLocator.lookupFor(RmiConfiguration.URL_CHAT_FACADE));

            chatClient.run();

        } catch (RemoteException e) {
            handleException(e.getMessage());
        } catch (NotBoundException e) {
            handleException(e.getMessage());
        } catch (MalformedURLException e) {
            handleException(e.getMessage());
        }

    }

    private static void handleException(String message) {
        Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, message);
    }
}

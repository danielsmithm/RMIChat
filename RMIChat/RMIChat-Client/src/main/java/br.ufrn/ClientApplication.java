package br.ufrn;

import br.ufrn.configuration.RmiConfiguration;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientApplication {

    public static void main(String[] args) {

        try {
            ChatClient chatClient = new ChatClient(ServiceLocator.lookupFor(RmiConfiguration.URL_CHAT_FACADE));

            chatClient.run();

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}

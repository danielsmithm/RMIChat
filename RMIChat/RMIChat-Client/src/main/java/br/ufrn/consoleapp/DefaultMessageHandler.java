package br.ufrn.consoleapp;

import br.ufrn.MessageHandler;
import br.ufrn.domain.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Message handler padrão para console application.
 */
public class DefaultMessageHandler extends UnicastRemoteObject implements MessageHandler {

    private String userName;

    public DefaultMessageHandler(String userName) throws RemoteException {
        super();
        this.userName = userName;
    }

    @Override
    public String getUserName() throws RemoteException {
        return userName;
    }

    @Override
    public void notifyMessage(Message message) throws RemoteException {
        if(!userName.equals(message.getAuthorUserName())) {
            System.out.println(String.format("%s - %s: %s ", message.getGroupName(), message.getAuthorUserName(), message.getContent()));
        }
    }

}

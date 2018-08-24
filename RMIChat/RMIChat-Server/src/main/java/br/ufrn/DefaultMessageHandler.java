package br.ufrn;

import br.ufrn.domain.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DefaultMessageHandler extends UnicastRemoteObject implements MessageHandler {

    private String userName;

    protected DefaultMessageHandler(String userName) throws RemoteException {
        super();
        this.userName = userName;
    }

    @Override
    public String getUserName() throws RemoteException {
        return userName;
    }

    @Override
    public void notifyMessage(Message message) throws RemoteException {
        System.out.println(String.format("%s - %s: %s ", message.getGroupId(), message.getUserName(),message.getContent()));
    }

}

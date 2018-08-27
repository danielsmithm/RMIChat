package br.ufrn;

import br.ufrn.domain.Message;
import br.ufrn.domain.User;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageHandler extends Remote, Serializable {
    String getUserName() throws RemoteException;
    void notifyMessage(Message message) throws RemoteException;
}

package br.ufrn.service;

import br.ufrn.MessageHandler;
import br.ufrn.domain.Message;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagePublisher implements Serializable{

    public static final boolean ALLOW_NOTIFY_MESSAGE_CREATOR = true;

    private Set<MessageHandler> messageHandlers;

    protected MessagePublisher() {
        this.messageHandlers = new HashSet<>();
    }

    public void registerMessageHandler(MessageHandler messageHandler){
        this.messageHandlers.add(messageHandler);

        try {
            getLogger().log(Level.INFO,"Handler registered for user: "+messageHandler.getUserName());
        } catch (RemoteException e) {
            handleRemoteException(e);
        }

    }

    public void publishMessageCreation(Message message, Set<String> usersToNotify) {
        CompletableFuture.runAsync(() -> {
            messageHandlers.parallelStream()
                    .filter(messageHandler -> usersToNotifyContainsHandlerUser(usersToNotify, messageHandler))
                    .filter(messageHandler -> allowNotifyMessageCreator() || handlerUserIsNotMessageCreator(message, messageHandler))
                    .forEach(messageHandler -> notifyMessage(message, messageHandler));
        });
    }

    private boolean usersToNotifyContainsHandlerUser(Set<String> usersToNotify, MessageHandler messageHandler){

        try {
            return usersToNotify.contains(messageHandler.getUserName());
        } catch (RemoteException e) {
            handleRemoteException(e);
        }

        return false;

    }

    private boolean handlerUserIsNotMessageCreator(Message message, MessageHandler messageHandler){

        try {
            return !messageHandler.getUserName().equals(message.getAuthorUserName());
        } catch (RemoteException e) {
            handleRemoteException(e);
        }

        return false;
    }

    private void notifyMessage(Message message, MessageHandler messageHandler){

        try {
            messageHandler.notifyMessage(message);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }

    }

    private static boolean allowNotifyMessageCreator(){
        return ALLOW_NOTIFY_MESSAGE_CREATOR;
    }

    private void handleRemoteException(RemoteException e) {
        getLogger().log(Level.SEVERE,e.getMessage());
    }

    private Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }

}

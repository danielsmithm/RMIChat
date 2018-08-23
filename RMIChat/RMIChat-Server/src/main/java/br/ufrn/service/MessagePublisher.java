package br.ufrn.service;

import br.ufrn.MessageHandler;
import br.ufrn.domain.Message;

import java.util.HashSet;
import java.util.Set;

public class MessagePublisher {

    private Set<MessageHandler> messageHandlers;

    public MessagePublisher() {
        this.messageHandlers = new HashSet<>();
    }

    public void registerMessageHandler(MessageHandler messageHandler){
        this.messageHandlers.add(messageHandler);
    }

    public void publishMessageCreation(Message message, Set<String> usersToNotify) {
        messageHandlers.parallelStream()
                .filter(messageHandler -> usersToNotify.contains(messageHandler.getUserName()))
                //.filter(messageHandler -> !messageHandler.getUserName().equals(message.getUserName()))
                .forEach(messageHandler -> messageHandler.notifyMessage(message));
    }
}

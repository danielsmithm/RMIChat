package br.ufrn.service;

import br.ufrn.MessageHandler;
import br.ufrn.domain.Message;

import java.util.Set;

public interface MessagePublisher {
    void registerMessageHandler(MessageHandler messageHandler);

    void publishMessageCreation(Message message, Set<String> usersToNotify);
}

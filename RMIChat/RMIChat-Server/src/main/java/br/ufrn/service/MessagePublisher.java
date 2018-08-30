package br.ufrn.service;

import br.ufrn.MessageHandler;
import br.ufrn.domain.Message;

import java.util.Set;

/**
 * Interface para o message publisher.
 */
public interface MessagePublisher {

    /**
     * Registra o message handler passado por parâmetro.
     *
     * @param messageHandler
     */
    void registerMessageHandler(MessageHandler messageHandler);

    /**
     *  Publica a criação da mensagem para os usuários passados.
     *  <br>
     *  A implementação deste método deverá efetuar o envio da notificação
     *  para os handler's associados aos usuários a serem notificados de forma assíncrona,
     *  visto que o número de usuários a notificar pode ser alto e a notificação ser uma operação bloqueante.
     *
     * @param message
     * @param usersToNotify
     */
    void publishMessageCreation(Message message, Set<String> usersToNotify);
}

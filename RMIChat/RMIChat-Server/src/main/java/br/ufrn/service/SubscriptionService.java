package br.ufrn.service;

import br.ufrn.MessageHandler;
import br.ufrn.exceptions.AlreadySubscribedException;

public interface SubscriptionService {
    void subscribe(MessageHandler subscriber) throws AlreadySubscribedException;
}

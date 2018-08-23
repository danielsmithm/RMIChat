package br.ufrn;

import br.ufrn.domain.Message;
import br.ufrn.domain.User;

public interface MessageHandler {
    String getUserName();
    void notifyMessage(Message message);
}

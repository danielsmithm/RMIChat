package br.ufrn;

import br.ufrn.domain.Message;
import br.ufrn.service.MessagePublisher;
import br.ufrn.service.MessagePublisherImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class MessagePublisherTest {

    private MessagePublisherImpl messagePublisher;
    private MessageHandler handlerMock;

    @Before
    public void setUp(){
        messagePublisher = Mockito.spy(new MessagePublisherImpl());
        handlerMock = Mockito.mock(MessageHandler.class);

        Mockito.doReturn(Mockito.mock(Logger.class)).when(messagePublisher).getLogger();
    }

    @Test
    public void testRegisterMessageHandler(){
        messagePublisher.registerMessageHandler(handlerMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterMessageHandler_passingNullParameter(){
        messagePublisher.registerMessageHandler(null);
    }

    @Ignore
    @Test
    public void testNotifyMessagePublishing(){
        Set<String> usersToCreateHandler = Sets.newSet("foo","bar","fubá","fuba");
        Set<String> usersToNotify = Sets.newSet("foo,bar");
        Message message = Message.createMessage("1", "Foobar group", "foo", "Foobar");

        List<MessageHandler> handlersForUsers = usersToCreateHandler.stream()
                .map(user -> createMockForMessageHandler(user))
                .collect(Collectors.toList());

        handlersForUsers.forEach(messageHandler -> messagePublisher.registerMessageHandler(messageHandler));

        messagePublisher.publishMessageCreation(message,usersToNotify);

        Mockito.verify(messagePublisher,Mockito.times(2)).notifyMessage(Mockito.any(),Mockito.any());

    }

    private MessageHandler createMockForMessageHandler(String user){

        MessageHandler messageHandlerMock = Mockito.mock(MessageHandler.class);

        try {
            Mockito.when(messageHandlerMock.getUserName()).thenReturn(user);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return messageHandlerMock;
    }

}

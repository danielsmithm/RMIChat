package br.ufrn;

import br.ufrn.domain.Group;
import br.ufrn.domain.Message;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class ChatClient implements Serializable {

    private ChatFacade chatFacade;

    public ChatClient(ChatFacade chatFacade) {
        this.chatFacade = chatFacade;
    }

    public void run() {

        try {

            User activeUser = null;
            Scanner scanner = new Scanner(System.in);
            try{
                System.out.println("Digite um username para registro: ");
                activeUser = chatFacade.register(scanner.next());
            }catch (UserAlreadyExistsException e) {
                throw new RuntimeException(e);
            }

            registerMessageHandlerForUser(activeUser);

            chatFacade.joinGroup("1",activeUser);

            System.out.print("Ingressou ao grupo.");

            List<Group> groups = chatFacade.listGroups(activeUser.getUserName());

            Group group = groups.get(0);
            scanner = new Scanner(System.in);
            while (true){
                String messageContent = scanner.nextLine();
                chatFacade.sendMessageToGroup(group.getId(),activeUser.getUserName(), messageContent);
            }


        } catch (RemoteException e) {
            System.out.println("Ocorreu uma falha de comunicação com o servidor remoto. A aplicação foi encerrada.");
            throw new RuntimeException(e);
        } catch (GroupNotExistsException e) {
            e.printStackTrace();
        }


    }

    private void registerMessageHandlerForUser(User activeUser) throws RemoteException {

        final String userName = activeUser.getUserName();

        MessageHandler handler = new DefaultMessageHandler(userName);

        chatFacade.registerMessageHandler(handler);
    }
}

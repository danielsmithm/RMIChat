package br.ufrn.consoleapp;

import br.ufrn.ChatFacade;
import br.ufrn.MessageHandler;
import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleChatClient implements Serializable {

    private ChatFacade chatFacade;
    private User activeUser;

    public ConsoleChatClient(ChatFacade chatFacade) {
        this.chatFacade = chatFacade;

    }

    public void run() {

        try {

            this.activeUser = registerUser();

            registerMessageHandlerForUser(activeUser);

            Group group = chooseSubscribedGroup(activeUser);

            startChatInGroup(group);

            return;

        } catch (RemoteException e) {
            System.out.println("Ocorreu uma falha de comunicação com o servidor remoto. A aplicação foi encerrada.");
            throw new RuntimeException(e);
        } catch (GroupNotExistsException e) {
            e.printStackTrace();
        }

    }

    private void startChatInGroup(Group group) throws RemoteException, GroupNotExistsException {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String messageContent = scanner.nextLine();
            chatFacade.sendMessageToGroup(group.getId(),activeUser.getUserName(), messageContent);
        }
    }

    private Group chooseSubscribedGroup(User activeUser) throws RemoteException {
        List<Group> groups = chatFacade.listGroups(activeUser.getUserName());

        if(groups != null && !groups.isEmpty()){
            System.out.println("Selecione um grupo: ");
            groups.forEach(group -> System.out.printf("%s - %s",group.getId(),group.getName()));
        }

        Scanner scanner = new Scanner(System.in);

        String userInput = scanner.next();
        if(userInput == null || !userInput.matches("\\d{1,10}")){
            System.out.println("Digite um id do grupo válido.");
        }else{
            Optional<Group> first = groups.stream()
                    .filter(group -> group.getId().equals(userInput.trim()))
                    .findFirst();

            if(first.isPresent()){
                return first.get();
            }else{
                System.out.println("Digite um id do grupo válido.");
            }

        }

        return null;

    }

    private User registerUser() throws RemoteException {
        User activeUser;

        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Digite um username para registro: ");

            activeUser = chatFacade.register(scanner.next());
            chatFacade.joinGroup("1",activeUser);

            System.out.print("Ingressou ao grupo.");
        }catch (UserAlreadyExistsException | GroupNotExistsException e) {
            throw new RuntimeException(e);
        }

        return activeUser;

    }

    private void registerMessageHandlerForUser(User activeUser) throws RemoteException {

        final String userName = activeUser.getUserName();

        MessageHandler handler = new DefaultMessageHandler(userName);

        chatFacade.registerMessageHandler(handler);
    }

}

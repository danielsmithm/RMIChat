package br.ufrn;

import br.ufrn.configuration.RmiConfiguration;
import br.ufrn.domain.Group;
import br.ufrn.domain.Message;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;

import br.ufrn.repository.GroupRepository;
import br.ufrn.repository.UserRepository;
import br.ufrn.service.GroupServiceImpl;
import br.ufrn.service.MessagePublisher;
import br.ufrn.service.UsuarioServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;

public class ServerApplication {

    public static void main(String[] args) throws UserAlreadyExistsException, GroupNotExistsException {

        try {
            LocateRegistry.createRegistry(RmiConfiguration.RMI_PORT);
            ChatFacade chatFacade = createChatFacade();
            Naming.rebind(RmiConfiguration.URL_CHAT_FACADE, chatFacade);
        } catch (RemoteException e) {
            System.out.println("Não foi possível criar a aplicação chat.");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Não foi possível criar a aplicação chat.");
            e.printStackTrace();
        }

    }

    private static ChatFacade createChatFacade() {
        UsuarioServiceImpl usuarioService = new UsuarioServiceImpl(new UserRepository());
        MessagePublisher messagePublisher = new MessagePublisher();
        GroupServiceImpl groupService = new GroupServiceImpl(new GroupRepository(), messagePublisher);

        return new ChatFacadeImpl(groupService,messagePublisher,usuarioService);
    }

}

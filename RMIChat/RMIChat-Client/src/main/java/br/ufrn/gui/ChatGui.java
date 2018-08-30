package br.ufrn.gui;

import br.ufrn.ChatFacade;
import br.ufrn.MessageHandler;
import br.ufrn.domain.Group;
import br.ufrn.utils.ServiceLocator;
import br.ufrn.configuration.RmiConfiguration;
import br.ufrn.domain.Message;
import br.ufrn.domain.User;
import br.ufrn.exceptions.GroupNotExistsException;
import br.ufrn.exceptions.UserAlreadyExistsException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ChatGui extends JFrame{

    private Group group;
    private final ChatFacade chatFacade;
    private final User activeUser;

    private JPanel chatPannel;
    private JTextArea messageTextArea;
    private JButton sendMessageButton;
    private JList<String> messageList;

    public static void main(String[] args) {

        try {
            ChatFacade chatFacade = ServiceLocator.lookupFor(RmiConfiguration.URL_CHAT_FACADE);

            User activeUser = registerUser(chatFacade);

            new GroupChooseGui(chatFacade,activeUser);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null,"Wasn't possible estabilish a connection to server. Try again later.");
        } catch (NotBoundException e) {
            JOptionPane.showMessageDialog(null,"A binding for the remote object wasn't found.");
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null,"An configuration error in the application was found and wasn't possible start the app.");
        }

    }

    public ChatGui(User activeUser, Group group, ChatFacade chatFacade){
        this.activeUser = activeUser;
        this.chatFacade = chatFacade;
        this.group = group;
        initGui();
    }

    private void initGui() {
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 480);
        setPreferredSize(getSize());
        setTitle("RMIChat");

        JPanel wrapperPannel = new JPanel();
        wrapperPannel.setPreferredSize(getSize());

        this.chatPannel = createChatPannel();

        wrapperPannel.add(this.chatPannel);
        wrapperPannel.add(createMessageSenderPannel());

        add(wrapperPannel);
        pack();
        setVisible(true);

        try {
            registerMessageHandler();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private JPanel createMessageSenderPannel() {
        JPanel messageSendPanel = new JPanel();

        messageSendPanel.setSize(800,140);
        messageSendPanel.setPreferredSize(messageSendPanel.getSize());

        this.messageTextArea = new JTextArea();

        this.messageTextArea.setSize(480,140);
        this.messageTextArea.setPreferredSize(this.messageTextArea.getSize());

        this.messageTextArea.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        chatFacade.sendMessageToGroup(group.getId(),activeUser.getUserName(),messageTextArea.getText());
                        messageTextArea.setText("");
                    } catch (RemoteException e1) {
                        JOptionPane.showMessageDialog(null,"An communication error ocurred while send the message to server. Try again later.");
                    } catch (GroupNotExistsException e1) {
                        JOptionPane.showMessageDialog(null, "The corresponding group to this chat message was not found. It may be deleted. ");
                    }
                }
            }
        });

        messageSendPanel.add(this.messageTextArea);

        return messageSendPanel;
    }

    private JPanel createChatPannel() {
        JPanel chatPannel = new JPanel();

        chatPannel.setSize(600,320);
        chatPannel.setPreferredSize(chatPannel.getSize());

        this.messageList = new JList<String>();
        this.messageList.setPreferredSize(chatPannel.getPreferredSize());
        this.messageList.setModel(new DefaultListModel<>());

        JScrollPane scrollPane = new JScrollPane(new JScrollPane(this.messageList));
        scrollPane.setPreferredSize(messageList.getPreferredSize());

        chatPannel.add(scrollPane);

        return chatPannel;
    }

    private static User registerUser(ChatFacade chatFacade) throws RemoteException {
        User activeUser;

        Scanner scanner = new Scanner(System.in);
        try{
            String usernameForRegistry = JOptionPane.showInputDialog("Digite um nome de usuário para registro.");

            activeUser = chatFacade.register(usernameForRegistry);
            chatFacade.joinGroup("1",activeUser);

            System.out.print("Ingressou ao grupo.");
        }catch (UserAlreadyExistsException | GroupNotExistsException e) {
            throw new RuntimeException(e);
        }

        return activeUser;

    }

    private void registerMessageHandler() throws RemoteException {

        final String userName = activeUser.getUserName();

        MessageHandler handler = new ChatGui.GUIMessageHandler();

        chatFacade.registerMessageHandler(handler);
    }

    private class GUIMessageHandler extends UnicastRemoteObject implements MessageHandler{

        protected GUIMessageHandler() throws RemoteException {
            super();
        }

        @Override
        public String getUserName() throws RemoteException {
            return activeUser.getUserName();
        }

        @Override
        public void notifyMessage(Message message) throws RemoteException {
            DefaultListModel<String> model = (DefaultListModel<String>) messageList.getModel();

            String formatedMessage = null;
            String dateInHoursMinutesFormat = new SimpleDateFormat("HH:mm").format(message.getSendTime());
            if(isServerMesssage(message)){
                formatedMessage = String.format("(%s) %s", dateInHoursMinutesFormat,message.getContent());
            }else{
                formatedMessage = String.format("(%s) %s - %s",dateInHoursMinutesFormat,message.getAuthorUserName(),message.getContent());
            }

            model.addElement(formatedMessage);
        }

        private boolean isServerMesssage(Message message){
            return message.authorUserName.equals("server");
        }
    }

}

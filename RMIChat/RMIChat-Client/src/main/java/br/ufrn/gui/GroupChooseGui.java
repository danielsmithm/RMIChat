package br.ufrn.gui;

import br.ufrn.ChatFacade;
import br.ufrn.domain.Group;
import br.ufrn.domain.User;
import br.ufrn.utils.ExceptionHandlingUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Implementação da GUI de escolha do grupo.
 */
public class GroupChooseGui extends JFrame {

    private final User activeUser;
    private final ChatFacade chatFacade;
    private List<Group> existentGroups;

    private JList<Group> messageList;
    private JButton groupChooseButton;

    public GroupChooseGui(ChatFacade chatFacade, final User activeUser) throws RemoteException {
        this.activeUser = activeUser;
        this.chatFacade = chatFacade;

        loadGroups(chatFacade, activeUser);

        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 480);
        setPreferredSize(getSize());
        setTitle("RMIChat");

        JPanel wrapperPannel = new JPanel();
        wrapperPannel.setPreferredSize(getSize());

        JButton createChatButton = new JButton();
        createChatButton.setText("Quit chat");
        createChatButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String groupName = JOptionPane.showInputDialog("Enter a group name.");

                    if(groupName != null){
                        setVisible(false);
                        chatFacade.createGroup(groupName,activeUser);
                        GroupChooseGui.openGroupChooseGui(chatFacade,activeUser);
                    }else{
                        JOptionPane.showMessageDialog(null,"Insert a valid group name.");
                    }

                } catch (RemoteException e1) {
                    ExceptionHandlingUtils.handleRemoteExcetpion();
                }

            }
        });

        wrapperPannel.add(createChatButton);
        wrapperPannel.add(createGuiChoosePanel());
        wrapperPannel.add(createChooseGroupButtonPannel());

        add(wrapperPannel);
        pack();
        setVisible(true);
    }

    static void openGroupChooseGui(ChatFacade chatFacade, User activeUser) throws RemoteException {
        new GroupChooseGui(chatFacade,activeUser);
    }

    private void loadGroups(ChatFacade chatFacade, User activeUser) throws RemoteException {
        this.existentGroups = chatFacade.listGroups();
    }

    private Component createGuiChoosePanel() {

        JPanel guiChoosePanel = new JPanel();

        guiChoosePanel.setSize(600,320);
        guiChoosePanel.setPreferredSize(guiChoosePanel.getSize());

        this.messageList = new JList<Group>();
        this.messageList.setPreferredSize(guiChoosePanel.getPreferredSize());

        final DefaultListModel<Group> model = new DefaultListModel<>();

        existentGroups.forEach(group -> model.addElement(group));

        this.messageList.setModel(model);

        JScrollPane scrollPane = new JScrollPane(new JScrollPane(this.messageList));
        scrollPane.setPreferredSize(messageList.getPreferredSize());

        guiChoosePanel.add(scrollPane);

        groupChooseButton = new JButton();
        groupChooseButton.setText("Choose group");
        groupChooseButton.addActionListener(createGroupChooseHandler());

        guiChoosePanel.add(this.groupChooseButton);

        return guiChoosePanel;
    }

    private Component createChooseGroupButtonPannel() {

        JPanel guiChoosePanel = new JPanel();

        guiChoosePanel.setSize(600,320);
        guiChoosePanel.setPreferredSize(guiChoosePanel.getSize());

        groupChooseButton = new JButton();
        groupChooseButton.setText("Choose group");
        groupChooseButton.addActionListener(createGroupChooseHandler());

        guiChoosePanel.add(this.groupChooseButton);

        return guiChoosePanel;
    }

    private AbstractAction createGroupChooseHandler() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfSelectedItemsOnList = messageList.getSelectedIndices().length;

                if(numberOfSelectedItemsOnList == 1){
                    int selectIndex = messageList.getSelectedIndices()[0];
                    Group group = messageList.getModel().getElementAt(selectIndex);

                    setVisible(false);

                    new ChatGui(activeUser, group, chatFacade);

                }else if(numberOfSelectedItemsOnList == 0){
                    JOptionPane.showMessageDialog(null,"Select a group.");
                }else{
                    JOptionPane.showMessageDialog(null,"Select a single group.");
                }
            }
        };
    }

}

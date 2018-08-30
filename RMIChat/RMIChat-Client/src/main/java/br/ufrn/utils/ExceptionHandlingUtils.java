package br.ufrn.utils;

import javax.swing.*;

public class ExceptionHandlingUtils {

    public static void handleGroupNotExistsException() {
        JOptionPane.showMessageDialog(null, "The corresponding group to this chat message was not found. It may be deleted. ");
    }

    public static void handleRemoteExcetpion() {
        JOptionPane.showMessageDialog(null,"An communication error ocurred while send the message to server. Try again later.");
    }
}

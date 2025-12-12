package studentapp.gui;

import javax.swing.JOptionPane;
import java.awt.Component;

public class DialogUtils {

    /**
     * Shows a simple information message.
     * @param parent The parent component (can be null).
     * @param message The message to display.
     */
    public static void showInfoMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error message.
     * @param parent The parent component (can be null).
     * @param message The error message to display.
     */
    public static void showErrorMessage(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
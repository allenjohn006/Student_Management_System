package studentapp.main;

import studentapp.db.DBConnection;
import studentapp.gui.StudentGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager; // <-- 1. IMPORT THIS

public class Main {
    public static void main(String[] args) {
        
        // 2. ADD THIS ENTIRE BLOCK
        try {
            // This is the magic line that makes it look native
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 1. Initialize the database (create table if not exists)
        DBConnection.initializeDatabase();
        
        // 2. Run the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StudentGUI frame = new StudentGUI();
                frame.setLocationRelativeTo(null); // Center the window
                frame.setVisible(true);
            }
        });
    }
}
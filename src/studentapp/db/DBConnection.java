package studentapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File; // <-- 1. ADD THIS IMPORT

public class DBConnection {
    private static final String URL = "jdbc:sqlite:studentapp"; // (Name is 'studentapp' based on your file)

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        
        // --- 2. UPDATED SQL ---
        String sql = "CREATE TABLE IF NOT EXISTS students ("
                   + " id INTEGER PRIMARY KEY,"
                   + " name TEXT NOT NULL,"
                   + " course TEXT NOT NULL,"
                   + " marks INTEGER,"
                   + " email TEXT,"
                   + " phone TEXT,"
                   + " enrollment_date TEXT,"
                   + " image_path TEXT" // <-- 3. ADDED THIS LINE
                   + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sql);
            System.out.println("Database initialized (table 'students' is ready).");
            
            // --- 4. CREATE UPLOADS FOLDER ---
            File uploadsDir = new File("uploads");
            if (!uploadsDir.exists()) {
                uploadsDir.mkdir();
                System.out.println("Created 'uploads' directory.");
            }
            
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}
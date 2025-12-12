package studentapp.db;

import studentapp.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase implements DatabaseOperations {

    @Override
    public boolean addStudent(Student student) {
        // --- UPDATED SQL (8 fields) ---
        String sql = "INSERT INTO students(id, name, course, marks, email, phone, enrollment_date, image_path) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getCourse());
            pstmt.setInt(4, student.getMarks());
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getPhone());
            pstmt.setString(7, student.getEnrollmentDate());
            pstmt.setString(8, student.getImagePath()); // <-- NEW PARAMETER
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        // --- UPDATED SQL (7 fields + ID) ---
        String sql = "UPDATE students SET name = ?, course = ?, marks = ?, "
                   + "email = ?, phone = ?, enrollment_date = ?, image_path = ? "
                   + "WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getCourse());
            pstmt.setInt(3, student.getMarks());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getEnrollmentDate());
            pstmt.setString(7, student.getImagePath()); // <-- NEW PARAMETER
            pstmt.setInt(8, student.getId()); // ID is now parameter 8
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        // This method is unchanged
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Student getStudent(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // --- USE 8-ARGUMENT CONSTRUCTOR ---
                return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("course"),
                    rs.getInt("marks"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("enrollment_date"),
                    rs.getString("image_path") // <-- NEW FIELD
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting student: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // --- USE 8-ARGUMENT CONSTRUCTOR ---
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("course"),
                    rs.getInt("marks"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("enrollment_date"),
                    rs.getString("image_path") // <-- NEW FIELD
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all students: " + e.getMessage());
        }
        return students;
    }
}
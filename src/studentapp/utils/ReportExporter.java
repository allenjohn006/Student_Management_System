package studentapp.utils;

import studentapp.model.GradeCalculator;
import studentapp.model.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReportExporter {

    /**
     * Exports the list of students to a text file.
     * @param students The list of students to export.
     * @param filename The name of the file to create (e.g., "report.txt").
     * @return true if successful, false otherwise.
     */
    public static boolean exportToTxt(List<Student> students, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            
            writer.println("============================================================================================================================");
            writer.println("                                                  STUDENT REPORT                                                         ");
            writer.println("============================================================================================================================");
            writer.printf("Total Students: %d%n%n", students.size());
            
            // --- UPDATED HEADER ---
            writer.printf("%-5s | %-20s | %-15s | %-5s | %-5s | %-25s | %-15s | %-10s | %-30s%n", 
                          "ID", "Name", "Course", "Marks", "Grade", "Email", "Phone", "Enrolled", "Image Path");
            writer.println("------+----------------------+-----------------+-------+-------+---------------------------+-----------------+------------+--------------------------------");

            for (Student s : students) {
                String grade = GradeCalculator.calculateGrade(s.getMarks());
                
                // --- UPDATED ROW ---
                writer.printf("%-5d | %-20s | %-15s | %-5d | %-5s | %-25s | %-15s | %-10s | %-30s%n",
                              s.getId(), 
                              s.getName(), 
                              s.getCourse(), 
                              s.getMarks(), 
                              grade, 
                              s.getEmail(), 
                              s.getPhone(), 
                              s.getEnrollmentDate(),
                              s.getImagePath() != null ? s.getImagePath() : "N/A"); // Add image path
            }
            
            writer.println("============================================================================================================================");
            writer.println("                                                         END OF REPORT                                                     ");
            writer.println("============================================================================================================================");
            return true;
            
        } catch (IOException e) {
            System.err.println("Failed to export report: " + e.getMessage());
            return false;
        }
    }
}
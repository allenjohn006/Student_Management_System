package studentapp.gui; // <-- THIS IS THE MISSING LINE THAT FIXES THE ERRORS

import studentapp.db.DatabaseOperations;
import studentapp.db.StudentDatabase;
import studentapp.model.GradeCalculator; 
import studentapp.model.Student;
import studentapp.utils.ReportExporter;
import studentapp.utils.ValidationHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentGUI extends JFrame {

    // (All component declarations are the same)
    private DatabaseOperations dbOps;
    private StudentForm studentForm;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnSearch, btnExport;
    private JLabel lblAvgMarks, lblHighestMarks, lblLowestMarks;

    public StudentGUI() {
        dbOps = new StudentDatabase();

        // --- Frame Setup ---
        setTitle("Student Management System");
        // --- 1. MADE WINDOW WIDER ---
        setSize(1350, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // (Window icon code is unchanged)
        try {
            ImageIcon frameIcon = new ImageIcon(getClass().getResource("/studentapp/icons/icons8-education-16.png"));
            setIconImage(frameIcon.getImage());
        } catch (Exception e) { System.err.println("Window Icon not found: " + e.getMessage()); }
        
        // --- 1. North: The Form ---
        studentForm = new StudentForm(); // This is now the wider form
        add(studentForm, BorderLayout.NORTH);

        // --- 2. Center: The Table ---
        // (Column names are unchanged from the previous step)
        String[] columnNames = {"ID", "Name", "Course", "Marks", "Grade", "Email", "Phone", "Enrollment Date"};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // (Table styling code is unchanged)
        javax.swing.table.JTableHeader header = studentTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setOpaque(true);
        studentTable.setRowHeight(25);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // --- 3. South: Buttons and Stats Panel ---
        // (All the code for the buttons and stats panel is unchanged)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        try { btnAdd = new JButton("Add Student", new ImageIcon(getClass().getResource("/studentapp/icons/icons8-plus-16.png"))); } catch (Exception e) { btnAdd = new JButton("Add Student"); }
        try { btnUpdate = new JButton("Update Student", new ImageIcon(getClass().getResource("/studentapp/icons/icons8-edit-16.png"))); } catch (Exception e) { btnUpdate = new JButton("Update Student"); }
        try { btnDelete = new JButton("Delete Student", new ImageIcon(getClass().getResource("/studentapp/icons/icons8-delete-trash-16.png"))); } catch (Exception e) { btnDelete = new JButton("Delete Student"); }
        try { btnSearch = new JButton("Search Student", new ImageIcon(getClass().getResource("/studentapp/icons/icons8-magnifying-glass-16.png"))); } catch (Exception e) { btnSearch = new JButton("Search Student"); }
        try { btnExport = new JButton("Export Report", new ImageIcon(getClass().getResource("/studentapp/icons/icons8-document-16.png"))); } catch (Exception e) { btnExport = new JButton("Export Report"); }
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnExport);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Class Statistics"));
        lblAvgMarks = new JLabel("Average Marks: -");
        lblHighestMarks = new JLabel("Highest Marks: -");
        lblLowestMarks = new JLabel("Lowest Marks: -");
        Font statsFont = new Font("Segoe UI", Font.BOLD, 14);
        lblAvgMarks.setFont(statsFont);
        lblHighestMarks.setFont(statsFont);
        lblLowestMarks.setFont(statsFont);
        statsPanel.add(lblAvgMarks);
        statsPanel.add(lblHighestMarks);
        statsPanel.add(lblLowestMarks);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.NORTH);
        southPanel.add(statsPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        refreshStudentTable();
        addListeners();
    }
    
    private void addListeners() {
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnSearch.addActionListener(e -> searchStudent());
        btnExport.addActionListener(e -> exportReport());

        // --- REFACTORED TABLE LISTENER ---
        // This gets the full Student object from the DB.
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
                int selectedRow = studentTable.getSelectedRow();
                
                // Get the ID from the table (column 0)
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                
                // Fetch the *full* student object from the database
                // This object now includes the imagePath
                Student s = dbOps.getStudent(id);
                
                if (s != null) {
                    // Set all data (including the photo) in the form
                    studentForm.setStudentData(s);
                }
            }
        });
    }

    // (refreshStudentTable is unchanged)
    private void refreshStudentTable() {
        tableModel.setRowCount(0);
        List<Student> students = dbOps.getAllStudents();
        int totalMarks = 0;
        int highestMarks = 0;
        int lowestMarks = 100;
        if (students.isEmpty()) {
            highestMarks = 0;
            lowestMarks = 0;
        }
        for (Student s : students) {
            int marks = s.getMarks();
            String grade = GradeCalculator.calculateGrade(marks);
            tableModel.addRow(new Object[]{
                s.getId(), s.getName(), s.getCourse(), s.getMarks(), grade, 
                s.getEmail(), s.getPhone(), s.getEnrollmentDate()
            });
            totalMarks += marks;
            if (marks > highestMarks) highestMarks = marks;
            if (marks < lowestMarks) lowestMarks = marks;
        }
        if (students.isEmpty()) {
            lblAvgMarks.setText("Average Marks: N/A");
            lblHighestMarks.setText("Highest Marks: N/A");
            lblLowestMarks.setText("Lowest Marks: N/A");
        } else {
            double avgMarks = (double) totalMarks / students.size();
            lblAvgMarks.setText(String.format("Average Marks: %.2f", avgMarks));
            lblHighestMarks.setText("Highest Marks: " + highestMarks);
            lblLowestMarks.setText("Lowest Marks: " + lowestMarks);
        }
    }

    // (addStudent, updateStudent, deleteStudent are unchanged)
    private void addStudent() {
        Student s = studentForm.getStudentData(); 
        if (s != null) {
            if (dbOps.addStudent(s)) {
                DialogUtils.showInfoMessage(this, "Student added successfully!");
                refreshStudentTable();
                studentForm.clearForm();
            } else {
                DialogUtils.showErrorMessage(this, "Failed to add student (ID might already exist).");
            }
        }
    }

    private void updateStudent() {
        Student s = studentForm.getStudentData();
        if (s != null) {
            if (dbOps.updateStudent(s)) {
                DialogUtils.showInfoMessage(this, "Student updated successfully!");
                refreshStudentTable();
                studentForm.clearForm();
            } else {
                DialogUtils.showErrorMessage(this, "Failed to update student (Student not found).");
            }
        }
    }

    private void deleteStudent() {
        String idStr = studentForm.txtId.getText();
        if (!ValidationHelper.isInteger(idStr)) {
            DialogUtils.showErrorMessage(this, "Please select a student or enter a valid ID to delete.");
            return;
        }
        int id = Integer.parseInt(idStr);
        int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete student " + id + "?", 
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        // --- THIS IS THE FIX (no '.' after YES_OPTION) ---
        if (choice == JOptionPane.YES_OPTION) { 
            if (dbOps.deleteStudent(id)) {
                DialogUtils.showInfoMessage(this, "Student deleted successfully!");
                refreshStudentTable();
                studentForm.clearForm();
            } else {
                DialogUtils.showErrorMessage(this, "Failed to delete student (Student not found).");
            }
        }
    }

    // (searchStudent is unchanged)
    private void searchStudent() {
        String idStr = studentForm.txtId.getText();
        if (!ValidationHelper.isInteger(idStr)) {
            DialogUtils.showErrorMessage(this, "Please enter a valid ID to search.");
            return;
        }
        int id = Integer.parseInt(idStr);
        Student s = dbOps.getStudent(id); 
        
        // --- THIS IS THE FIX (no '.' before null) ---
        if (s != null) { 
            studentForm.setStudentData(s);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if ((int) tableModel.getValueAt(i, 0) == id) {
                    studentTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } else {
            DialogUtils.showInfoMessage(this, "Student with ID " + id + " not found.");
        }
    }

    // This is the .txt export method you provided
    private void exportReport() {
        String filename = "student_report.txt"; // <-- Changed back to .txt
        List<Student> students = dbOps.getAllStudents();
        
        // --- Reverted back to exportToTxt ---
        if (ReportExporter.exportToTxt(students, filename)) { 
            DialogUtils.showInfoMessage(this, "Report exported successfully to " + filename);
        } else {
            DialogUtils.showErrorMessage(this, "Failed to export report.");
        }
    }
}
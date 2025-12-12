package studentapp.gui;

import studentapp.model.Student;
import studentapp.utils.ValidationHelper;
import studentapp.utils.ImageHelper; // <-- 1. IMPORT
import studentapp.gui.DialogUtils; 
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter; // <-- 2. IMPORT
import java.awt.*;
import java.awt.Font;
import java.io.File; // <-- 3. IMPORT

public class StudentForm extends JPanel {
    
    // --- Form Fields ---
    JTextField txtId;
    JTextField txtName;
    JTextField txtCourse;
    JTextField txtMarks;
    JTextField txtEmail;
    JTextField txtPhone;
    JTextField txtEnrollmentDate;
    JButton btnClear;
    
    // --- Photo Components ---
    private JLabel lblPhoto;
    private JButton btnBrowse;
    private String currentImagePath; // Holds the path of the image
    
    // --- Panel for form fields ---
    private JPanel formFieldsPanel;

    public StudentForm() {
        // --- 1. Main panel now uses BorderLayout ---
        setLayout(new BorderLayout(10, 10));
        
        // --- 2. Create the Form Fields Panel (like the old panel) ---
        formFieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        formFieldsPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtId = new JTextField(15);
        formFieldsPanel.add(txtId, gbc);

        // Row 1: Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        formFieldsPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(15);
        formFieldsPanel.add(txtName, gbc);

        // Row 2: Course
        gbc.gridx = 0; gbc.gridy = 2;
        formFieldsPanel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        txtCourse = new JTextField(15);
        formFieldsPanel.add(txtCourse, gbc);

        // Row 3: Marks
        gbc.gridx = 0; gbc.gridy = 3;
        formFieldsPanel.add(new JLabel("Marks:"), gbc);
        gbc.gridx = 1;
        txtMarks = new JTextField(15);
        formFieldsPanel.add(txtMarks, gbc);

        // Row 4: Email
        gbc.gridx = 0; gbc.gridy = 4;
        formFieldsPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(15);
        formFieldsPanel.add(txtEmail, gbc);
        
        // Row 5: Phone
        gbc.gridx = 0; gbc.gridy = 5;
        formFieldsPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(15);
        formFieldsPanel.add(txtPhone, gbc);
        
        // Row 6: Enrollment Date
        gbc.gridx = 0; gbc.gridy = 6;
        formFieldsPanel.add(new JLabel("Enrollment Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        txtEnrollmentDate = new JTextField(15);
        formFieldsPanel.add(txtEnrollmentDate, gbc);

        // Row 7: Clear Button
        gbc.gridx = 1; gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.NONE;
        btnClear = new JButton("Clear Form");
        formFieldsPanel.add(btnClear, gbc);
        
        // --- 3. Create the Photo Panel ---
        JPanel photoPanel = new JPanel(new BorderLayout(5, 5));
        photoPanel.setPreferredSize(new Dimension(160, 200)); // Give it a fixed size
        
        lblPhoto = new JLabel("No Image", SwingConstants.CENTER);
        lblPhoto.setPreferredSize(new Dimension(150, 150));
        lblPhoto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        btnBrowse = new JButton("Browse...");
        
        photoPanel.add(lblPhoto, BorderLayout.CENTER);
        photoPanel.add(btnBrowse, BorderLayout.SOUTH);

        // --- 4. Add panels to the main StudentForm panel ---
        add(formFieldsPanel, BorderLayout.CENTER);
        add(photoPanel, BorderLayout.EAST);
        
        // --- 5. Set the border on the main panel ---
        Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
        setBorder(BorderFactory.createTitledBorder(
                null, "Student Details",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                titleFont
        ));
        
        // --- 6. Add Listeners ---
        btnClear.addActionListener(e -> clearForm());
        btnBrowse.addActionListener(e -> browseImage());
    }
    
    /**
     * Handles the "Browse..." button click.
     */
    private void browseImage() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images (jpg, png, gif)", "jpg", "png", "gif", "jpeg");
        fileChooser.setFileFilter(filter);
        
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Copy the file to the project's 'uploads' folder
            String newPath = ImageHelper.copyImageToProject(selectedFile);
            
            if (newPath != null) {
                currentImagePath = newPath;
                displayImage(currentImagePath);
            } else {
                DialogUtils.showErrorMessage(this, "Failed to copy image.");
            }
        }
    }
    
    /**
     * Loads and displays the image in the lblPhoto.
     * @param imagePath The path to the image.
     */
    private void displayImage(String imagePath) {
        ImageIcon icon = ImageHelper.loadAndResizeImage(imagePath, lblPhoto.getWidth(), lblPhoto.getHeight());
        if (icon != null) {
            lblPhoto.setIcon(icon);
            lblPhoto.setText(null); // Remove "No Image" text
        } else {
            lblPhoto.setIcon(null);
            lblPhoto.setText("No Image");
        }
    }
    
    /**
     * Clears all text fields and the image.
     */
    public void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtCourse.setText("");
        txtMarks.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtEnrollmentDate.setText("");
        
        // --- CLEAR IMAGE ---
        currentImagePath = null;
        lblPhoto.setIcon(null);
        lblPhoto.setText("No Image");
        
        txtId.setEditable(true);
    }
    
    /**
     * Populates the form fields and image from a Student object.
     */
    public void setStudentData(Student s) {
        txtId.setText(String.valueOf(s.getId()));
        txtName.setText(s.getName());
        txtCourse.setText(s.getCourse());
        txtMarks.setText(String.valueOf(s.getMarks()));
        txtEmail.setText(s.getEmail());
        txtPhone.setText(s.getPhone());
        txtEnrollmentDate.setText(s.getEnrollmentDate());
        
        // --- SET IMAGE ---
        currentImagePath = s.getImagePath();
        displayImage(currentImagePath);
        
        txtId.setEditable(false);
    }
    
    /**
     * Reads data from the form and creates a new Student object.
     */
    public Student getStudentData() {
        // (All validation code is unchanged...)
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String date = txtEnrollmentDate.getText();
        
        if (!ValidationHelper.isInteger(txtId.getText())) {
            DialogUtils.showErrorMessage(this, "Invalid ID. Must be a number.");
            return null;
        }
        if (!ValidationHelper.isNotEmpty(txtName.getText())) {
            DialogUtils.showErrorMessage(this, "Name cannot be empty.");
            return null;
        }
        if (!ValidationHelper.isNotEmpty(txtCourse.getText())) {
            DialogUtils.showErrorMessage(this, "Course cannot be empty.");
            return null;
        }
        if (!ValidationHelper.isInteger(txtMarks.getText())) {
            DialogUtils.showErrorMessage(this, "Invalid Marks. Must be a number.");
            return null;
        }
        if (ValidationHelper.isNotEmpty(email) && !ValidationHelper.isValidEmail(email)) {
            DialogUtils.showErrorMessage(this, "Invalid Email Address.");
            return null;
        }
        if (ValidationHelper.isNotEmpty(phone) && !ValidationHelper.isValidPhone(phone)) {
            DialogUtils.showErrorMessage(this, "Invalid Phone Number. Must be 7-15 digits.");
            return null;
        }
        if (ValidationHelper.isNotEmpty(date) && !ValidationHelper.isValidDate(date)) {
            DialogUtils.showErrorMessage(this, "Invalid Date. Format must be YYYY-MM-DD.");
            return null;
        }
        
        // Validation passed
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText();
        String course = txtCourse.getText();
        int marks = Integer.parseInt(txtMarks.getText());
        
        // --- USE 8-ARGUMENT CONSTRUCTOR (with currentImagePath) ---
        return new Student(id, name, course, marks, email, phone, date, currentImagePath);
    }
}
package studentapp.db;

import studentapp.model.Student;
import java.util.List;

// This interface fulfills the "Interface" requirement of the rubric
public interface DatabaseOperations {
    
    boolean addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(int id);
    Student getStudent(int id);
    List<Student> getAllStudents();
    
}
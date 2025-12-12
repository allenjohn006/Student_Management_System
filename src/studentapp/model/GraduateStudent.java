package studentapp.model;

// This class demonstrates Inheritance
public class GraduateStudent extends Student {
    private String thesisTopic;

    public GraduateStudent(int id, String name, String course, int marks, String thesisTopic) {
        // --- THIS IS THE FIX ---
        // Call the parent constructor, passing null for the new fields
        super(id, name, course, marks, null, null, null, null); // <-- ADDED 8TH NULL
        
        this.thesisTopic = thesisTopic;
    }

    public String getThesisTopic() {
        return thesisTopic;
    }

    public void setThesisTopic(String thesisTopic) {
        this.thesisTopic = thesisTopic;
    }
}
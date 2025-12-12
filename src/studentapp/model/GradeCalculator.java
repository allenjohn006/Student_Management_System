package studentapp.model;

public class GradeCalculator {

    /**
     * Calculates a letter grade based on marks.
     * @param marks The student's marks.
     * @return A letter grade (A, B, C, D, F).
     */
    public static String calculateGrade(int marks) {
        if (marks >= 90) {
            return "A";
        } else if (marks >= 80) {
            return "B";
        } else if (marks >= 70) {
            return "C";
        } else if (marks >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}
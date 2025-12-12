package studentapp.utils;

import java.util.regex.Pattern; // <-- 1. ADD THIS IMPORT

public class ValidationHelper {

    // --- NEW REGEX PATTERNS ---
    // Simple email regex
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE
    );
    
    // Simple date regex (YYYY-MM-DD)
    private static final Pattern DATE_PATTERN = Pattern.compile(
        "^\\d{4}-\\d{2}-\\d{2}$"
    );

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isInteger(String text) {
        if (!isNotEmpty(text)) {
            return false;
        }
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // --- NEW VALIDATION METHODS ---

    /**
     * Checks if a string is a valid email address.
     * @param email The string to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) {
            return false; // Or true if email is optional
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Checks if a string is a valid phone number (e.g., 10 digits).
     * @param phone The string to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone)) {
            return false; // Or true if phone is optional
        }
        // Simple check: contains 7-15 digits, allowing spaces or dashes
        String numericPhone = phone.replaceAll("[\\s-]", "");
        return numericPhone.matches("^\\+?[0-9]{7,15}$");
    }

    /**
     * Checks if a string is a valid date (YYYY-MM-DD).
     * @param date The string to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidDate(String date) {
        if (!isNotEmpty(date)) {
            return false; // Or true if date is optional
        }
        return DATE_PATTERN.matcher(date).matches();
    }
}
package studentapp.utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImageHelper {

    private static final String UPLOADS_DIR = "uploads";

    /**
     * Copies a selected image file to the project's 'uploads' directory.
     * @param file The image file to copy.
     * @return The absolute path to the newly copied file, or null on failure.
     */
    public static String copyImageToProject(File file) {
        if (file == null) {
            return null;
        }

        try {
            // Ensure the 'uploads' directory exists
            File uploadsDir = new File(UPLOADS_DIR);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdir();
            }

            // Create a unique filename to avoid conflicts (e.g., using timestamp)
            String fileName = System.currentTimeMillis() + "_" + file.getName();
            Path destPath = Paths.get(uploadsDir.getAbsolutePath(), fileName);
            
            // Copy the file
            Files.copy(file.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
            
            // Return the full, absolute path of the new file
            return destPath.toAbsolutePath().toString();
            
        } catch (IOException e) {
            System.err.println("Error copying image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads an image from a file path and resizes it to fit a label.
     * @param imagePath The absolute path to the image.
     * @param width The target width.
     * @param height The target height.
     * @return An ImageIcon, or null if the image can't be loaded.
     */
    public static ImageIcon loadAndResizeImage(String imagePath, int width, int height) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }
        
        File imgFile = new File(imagePath);
        if (!imgFile.exists()) {
            System.err.println("Image file not found: " + imagePath);
            return null;
        }

        try {
            BufferedImage img = ImageIO.read(imgFile);
            if (img == null) {
                return null;
            }
            
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
            
        } catch (IOException e) {
            System.err.println("Error reading image: " + e.getMessage());
            return null;
        }
    }
}
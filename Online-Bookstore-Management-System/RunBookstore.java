import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RunBookstore {
    public static void main(String[] args) {
        System.out.println("Starting Online Bookstore Management System...");

        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
                System.out.println("Created data directory for file storage.");
            }

            // Create empty JSON files for data storage
            createEmptyJsonFile("data/users.json");
            createEmptyJsonFile("data/books.json");
            createEmptyJsonFile("data/orders.json");
            createEmptyJsonFile("data/payments.json");
            createEmptyJsonFile("data/reviews.json");

            // Run the Spring Boot application
            System.out.println("Launching the application...");
            System.out.println("Please access the application at http://localhost:9090");
            System.out.println("Default admin credentials: username=admin, password=admin123");

            // Launch the application using the main class
            com.bookstore.BookstoreApplication.main(args);

        } catch (Exception e) {
            System.err.println("Error starting the application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createEmptyJsonFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.write(path, "[]".getBytes());
            System.out.println("Created empty JSON file: " + filePath);
        }
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BookstoreDemo {
    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("      ONLINE BOOKSTORE MANAGEMENT SYSTEM DEMO");
        System.out.println("=======================================================");
        System.out.println();

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

            System.out.println("This is a demo of the Online Bookstore Management System.");
            System.out.println("The full application requires Spring Boot to run.");
            System.out.println();
            System.out.println("To run the full application, you need:");
            System.out.println("1. Java 22 (which you have)");
            System.out.println("2. Maven or Gradle build tool");
            System.out.println();
            System.out.println("Here's a demonstration of the system's functionality:");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean exit = false;

            while (!exit) {
                System.out.println();
                System.out.println("=======================================================");
                System.out.println("MAIN MENU");
                System.out.println("=======================================================");
                System.out.println("1. Login as Admin");
                System.out.println("2. Login as Customer");
                System.out.println("3. Login as Vendor");
                System.out.println("4. Login as Delivery Agent");
                System.out.println("5. Browse Books as Guest");
                System.out.println("6. Exit");
                System.out.println("=======================================================");
                System.out.print("Enter your choice (1-6): ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        adminMenu(reader);
                        break;
                    case "2":
                        customerMenu(reader);
                        break;
                    case "3":
                        vendorMenu(reader);
                        break;
                    case "4":
                        deliveryAgentMenu(reader);
                        break;
                    case "5":
                        guestMenu(reader);
                        break;
                    case "6":
                        exit = true;
                        System.out.println("Thank you for using the Online Bookstore Management System Demo!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void adminMenu(BufferedReader reader) throws IOException {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("ADMIN DASHBOARD");
        System.out.println("=======================================================");
        System.out.println("Username: admin");
        System.out.println("Role: Administrator");
        System.out.println();
        System.out.println("Statistics:");
        System.out.println("- Books: 5");
        System.out.println("- Customers: 2");
        System.out.println("- Orders: 3");
        System.out.println("- Vendors: 1");
        System.out.println("- Delivery Agents: 1");
        System.out.println();
        System.out.println("1. Manage Books");
        System.out.println("2. Manage Users");
        System.out.println("3. Manage Orders");
        System.out.println("4. Manage Payments");
        System.out.println("5. Back to Main Menu");
        System.out.println("=======================================================");
        System.out.print("Enter your choice (1-5): ");

        String choice = reader.readLine();

        switch (choice) {
            case "1":
                System.out.println("Book management functionality would be available here.");
                System.out.println("You can add, edit, view, and delete books.");
                break;
            case "2":
                System.out.println("User management functionality would be available here.");
                System.out.println("You can view, add, and delete users of different roles.");
                break;
            case "3":
                System.out.println("Order management functionality would be available here.");
                System.out.println("You can view orders, update status, and assign delivery agents.");
                break;
            case "4":
                System.out.println("Payment management functionality would be available here.");
                System.out.println("You can view and manage payment transactions.");
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void customerMenu(BufferedReader reader) throws IOException {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("CUSTOMER DASHBOARD");
        System.out.println("=======================================================");
        System.out.println("Username: customer");
        System.out.println("Role: Customer");
        System.out.println();
        System.out.println("1. Browse Books");
        System.out.println("2. View Cart");
        System.out.println("3. View Orders");
        System.out.println("4. Edit Profile");
        System.out.println("5. Back to Main Menu");
        System.out.println("=======================================================");
        System.out.print("Enter your choice (1-5): ");

        String choice = reader.readLine();

        switch (choice) {
            case "1":
                System.out.println("Book browsing functionality would be available here.");
                System.out.println("You can view book details, add to cart, and write reviews.");
                break;
            case "2":
                System.out.println("Shopping cart functionality would be available here.");
                System.out.println("You can view items in your cart and proceed to checkout.");
                break;
            case "3":
                System.out.println("Order history functionality would be available here.");
                System.out.println("You can view your orders and their status.");
                break;
            case "4":
                System.out.println("Profile editing functionality would be available here.");
                System.out.println("You can update your personal information and password.");
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void vendorMenu(BufferedReader reader) throws IOException {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("VENDOR DASHBOARD");
        System.out.println("=======================================================");
        System.out.println("Username: vendor");
        System.out.println("Role: Vendor");
        System.out.println();
        System.out.println("1. Manage My Books");
        System.out.println("2. Add New Book");
        System.out.println("3. Edit Profile");
        System.out.println("4. Back to Main Menu");
        System.out.println("=======================================================");
        System.out.print("Enter your choice (1-4): ");

        String choice = reader.readLine();

        switch (choice) {
            case "1":
                System.out.println("Book management functionality would be available here.");
                System.out.println("You can view, edit, and delete your books.");
                break;
            case "2":
                System.out.println("Book addition functionality would be available here.");
                System.out.println("You can add new books to the system.");
                break;
            case "3":
                System.out.println("Profile editing functionality would be available here.");
                System.out.println("You can update your personal and company information.");
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void deliveryAgentMenu(BufferedReader reader) throws IOException {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("DELIVERY AGENT DASHBOARD");
        System.out.println("=======================================================");
        System.out.println("Username: delivery");
        System.out.println("Role: Delivery Agent");
        System.out.println();
        System.out.println("1. View Assigned Orders");
        System.out.println("2. Update Order Status");
        System.out.println("3. Edit Profile");
        System.out.println("4. Back to Main Menu");
        System.out.println("=======================================================");
        System.out.print("Enter your choice (1-4): ");

        String choice = reader.readLine();

        switch (choice) {
            case "1":
                System.out.println("Order viewing functionality would be available here.");
                System.out.println("You can see orders assigned to you for delivery.");
                break;
            case "2":
                System.out.println("Order status update functionality would be available here.");
                System.out.println("You can update the status of orders (e.g., to SHIPPED or DELIVERED).");
                break;
            case "3":
                System.out.println("Profile editing functionality would be available here.");
                System.out.println("You can update your personal information and vehicle details.");
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    private static void guestMenu(BufferedReader reader) throws IOException {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("GUEST USER");
        System.out.println("=======================================================");
        System.out.println();
        System.out.println("1. Browse Books");
        System.out.println("2. Search Books");
        System.out.println("3. Register as Customer");
        System.out.println("4. Back to Main Menu");
        System.out.println("=======================================================");
        System.out.print("Enter your choice (1-4): ");

        String choice = reader.readLine();

        switch (choice) {
            case "1":
                System.out.println("Book browsing functionality would be available here.");
                System.out.println("You can view book details but need to register to purchase.");
                break;
            case "2":
                System.out.println("Book search functionality would be available here.");
                System.out.println("You can search for books by title, author, or category.");
                break;
            case "3":
                System.out.println("Registration functionality would be available here.");
                System.out.println("You can create a customer account to make purchases.");
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
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

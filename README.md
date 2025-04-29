# Online Bookstore Management System

A web-based bookstore management system with file-based storage.

## Project Overview

This application is a comprehensive online bookstore management system that allows different types of users (Customers, Administrators, Vendors, Delivery Agents) to interact with the system. The application uses file-based storage (JSON files) instead of a database.

## Features

- **User Management**: Register, login, and manage different user roles (Customer, Admin, Vendor, Delivery Agent)
- **Book Management**: Add, edit, view, and delete books
- **Order Management**: Create orders, track order status, assign delivery agents
- **Payment Processing**: Process payments for orders
- **Review System**: Add, edit, and delete reviews for books

## Components

1. **Customer**: Browse books, make purchases, write reviews, track orders
2. **Administrator (Admin)**: Manage the system, including books, users, and orders
3. **Vendor (Seller)**: Add and manage their books
4. **Delivery Agent**: Handle book deliveries and update order status
5. **Payment System**: Process transactions for book purchases
6. **Guest User**: Browse books but must register to make purchases

## Technologies Used

- **Backend**: Java 20, Spring Boot 3.4.5
- **Frontend**: HTML, CSS, JavaScript, Thymeleaf, Bootstrap 5.3.3
- **Data Storage**: File-based (JSON)

## Prerequisites

- Java 20
- Maven (optional)

## How to Run

### Option 1: Using Maven (if available)

1. Clone the repository
2. Navigate to the project directory
3. Run the following command:
   ```
   mvn spring-boot:run
   ```

### Option 2: Using Java directly

1. Clone the repository
2. Navigate to the project directory
3. Run the following commands:
   ```
   mkdir -p target/classes
   javac -d target/classes -cp "src/main/java" src/main/java/com/bookstore/BookstoreApplication.java
   java -cp target/classes com.bookstore.BookstoreApplication
   ```

### Option 3: Using the provided batch file (Windows)

1. Clone the repository
2. Navigate to the project directory
3. Double-click on `run.bat` or run it from the command line:
   ```
   run.bat
   ```

## Default Admin Credentials

- **Username**: admin
- **Password**: admin123

## Project Structure

- `src/main/java/com/bookstore`: Java source files
  - `model`: Entity classes
  - `repository`: File-based data access
  - `service`: Business logic
  - `controller`: Web controllers
  - `config`: Configuration classes
- `src/main/resources`: Resource files
  - `templates`: Thymeleaf templates
  - `static`: Static resources (CSS, JS)
- `data`: JSON files for data storage

## License

This project is licensed under the MIT License.

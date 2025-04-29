package com.bookstore.config;

import com.bookstore.model.Admin;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public DataInitializer(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        // Create admin user if no users exist
        if (userService.getAllUsers().isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@bookstore.com");
            admin.setFullName("System Administrator");
            admin.setDepartment("IT");
            userService.saveUser(admin);
            
            System.out.println("Admin user created with username: admin and password: admin123");
        }
        
        // Add sample books if no books exist
        if (bookService.getAllBooks().isEmpty()) {
            createSampleBooks();
            System.out.println("Sample books added to the system");
        }
    }
    
    private void createSampleBooks() {
        Book book1 = new Book();
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setIsbn("9780743273565");
        book1.setPrice(12.99);
        book1.setQuantity(50);
        book1.setCategory("Fiction");
        book1.setDescription("The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan.");
        book1.setVendorId("system");
        bookService.saveBook(book1);
        
        Book book2 = new Book();
        book2.setTitle("To Kill a Mockingbird");
        book2.setAuthor("Harper Lee");
        book2.setIsbn("9780061120084");
        book2.setPrice(14.99);
        book2.setQuantity(40);
        book2.setCategory("Fiction");
        book2.setDescription("To Kill a Mockingbird is a novel by Harper Lee published in 1960. It was immediately successful, winning the Pulitzer Prize, and has become a classic of modern American literature.");
        book2.setVendorId("system");
        bookService.saveBook(book2);
        
        Book book3 = new Book();
        book3.setTitle("1984");
        book3.setAuthor("George Orwell");
        book3.setIsbn("9780451524935");
        book3.setPrice(11.99);
        book3.setQuantity(35);
        book3.setCategory("Science Fiction");
        book3.setDescription("1984 is a dystopian novel by English novelist George Orwell. It was published in June 1949 by Secker & Warburg as Orwell's ninth and final book completed in his lifetime.");
        book3.setVendorId("system");
        bookService.saveBook(book3);
        
        Book book4 = new Book();
        book4.setTitle("The Hobbit");
        book4.setAuthor("J.R.R. Tolkien");
        book4.setIsbn("9780547928227");
        book4.setPrice(15.99);
        book4.setQuantity(30);
        book4.setCategory("Fantasy");
        book4.setDescription("The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction.");
        book4.setVendorId("system");
        bookService.saveBook(book4);
        
        Book book5 = new Book();
        book5.setTitle("Pride and Prejudice");
        book5.setAuthor("Jane Austen");
        book5.setIsbn("9780141439518");
        book5.setPrice(9.99);
        book5.setQuantity(25);
        book5.setCategory("Romance");
        book5.setDescription("Pride and Prejudice is an 1813 romantic novel of manners written by Jane Austen. The novel follows the character development of Elizabeth Bennet, the dynamic protagonist of the book who learns about the repercussions of hasty judgments and comes to appreciate the difference between superficial goodness and actual goodness.");
        book5.setVendorId("system");
        bookService.saveBook(book5);
    }
}

package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    private final BookService bookService;

    @Autowired
    public VendorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"VENDOR".equals(userRole)) {
            return "redirect:/login";
        }

        List<Book> books = bookService.getBooksByVendor(userId);
        model.addAttribute("books", books);

        return "vendor/dashboard";
    }

    @GetMapping("/books/add")
    public String showAddBookForm(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"VENDOR".equals(userRole)) {
            return "redirect:/login";
        }

        Book book = new Book();
        book.setVendorId(userId);
        model.addAttribute("book", book);

        return "vendor/add-book";
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"VENDOR".equals(userRole)) {
            return "redirect:/login";
        }

        book.setVendorId(userId);
        bookService.saveBook(book);

        return "redirect:/vendor/dashboard";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditBookForm(@PathVariable String id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"VENDOR".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();

            // Check if the book belongs to the vendor
            if (!book.getVendorId().equals(userId)) {
                return "redirect:/vendor/dashboard";
            }

            model.addAttribute("book", book);
            return "vendor/edit-book";
        }

        return "redirect:/vendor/dashboard";
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@PathVariable String id, @ModelAttribute Book book, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"VENDOR".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<Book> existingBookOpt = bookService.getBookById(id);
        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();

            // Check if the book belongs to the vendor
            if (!existingBook.getVendorId().equals(userId)) {
                return "redirect:/vendor/dashboard";
            }

            book.setId(id);
            book.setVendorId(userId);
            bookService.saveBook(book);
        }

        return "redirect:/vendor/dashboard";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable String id, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"VENDOR".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();

            // Check if the book belongs to the vendor
            if (book.getVendorId().equals(userId)) {
                bookService.deleteBook(id);
            }
        }

        return "redirect:/vendor/dashboard";
    }
}

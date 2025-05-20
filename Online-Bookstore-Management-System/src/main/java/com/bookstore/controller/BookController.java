package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.Review;
import com.bookstore.service.BookService;
import com.bookstore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;
    private final ReviewService reviewService;
    
    @Autowired
    public BookController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }
    
    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }
    
    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable String id, Model model) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            model.addAttribute("book", book);
            
            List<Review> reviews = reviewService.getReviewsByBook(id);
            model.addAttribute("reviews", reviews);
            
            return "books/details";
        }
        return "redirect:/books";
    }
    
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }
    
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable String id, Model model) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isPresent()) {
            model.addAttribute("book", bookOpt.get());
            return "books/edit";
        }
        return "redirect:/books";
    }
    
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable String id, @ModelAttribute Book book) {
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/books";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}

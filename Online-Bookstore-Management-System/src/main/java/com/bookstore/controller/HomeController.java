package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Book> books = bookService.searchBooks(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "search-results";
    }

    @GetMapping("/category")
    public String category(@RequestParam String name, Model model) {
        List<Book> books = bookService.getBooksByCategory(name);
        model.addAttribute("books", books);
        model.addAttribute("category", name);
        return "category";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/guest/browse")
    public String guestBrowse(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("isGuest", true);
        return "guest/browse";
    }

    @GetMapping("/guest/book-details/{id}")
    public String guestBookDetails(@PathVariable String id, Model model) {
        Optional<Book> bookOpt = bookService.getBookById(id);
        if (bookOpt.isPresent()) {
            model.addAttribute("book", bookOpt.get());
            model.addAttribute("isGuest", true);
            return "guest/book-details";
        }
        return "redirect:/guest/browse";
    }

    @GetMapping("/guest/search")
    public String guestSearch(@RequestParam String keyword, Model model) {
        List<Book> books = bookService.searchBooks(keyword);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("isGuest", true);
        return "guest/search-results";
    }
}

package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepository extends AbstractFileRepository<Book> {
    
    public BookRepository() {
        super(Book.class, "books.json");
    }
    
    public List<Book> findByVendorId(String vendorId) {
        return findAll().stream()
                .filter(book -> book.getVendorId().equals(vendorId))
                .collect(Collectors.toList());
    }
    
    public List<Book> findByCategory(String category) {
        return findAll().stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    public List<Book> searchBooks(String keyword) {
        String searchTerm = keyword.toLowerCase();
        return findAll().stream()
                .filter(book -> 
                    book.getTitle().toLowerCase().contains(searchTerm) ||
                    book.getAuthor().toLowerCase().contains(searchTerm) ||
                    book.getDescription().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
}

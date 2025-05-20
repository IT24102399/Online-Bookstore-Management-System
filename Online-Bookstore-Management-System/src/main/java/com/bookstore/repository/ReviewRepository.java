package com.bookstore.repository;

import com.bookstore.model.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewRepository extends AbstractFileRepository<Review> {
    
    public ReviewRepository() {
        super(Review.class, "reviews.json");
    }
    
    public List<Review> findByBookId(String bookId) {
        return findAll().stream()
                .filter(review -> review.getBookId().equals(bookId))
                .collect(Collectors.toList());
    }
    
    public List<Review> findByCustomerId(String customerId) {
        return findAll().stream()
                .filter(review -> review.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
}

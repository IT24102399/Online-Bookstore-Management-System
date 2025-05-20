package com.bookstore.service;

import com.bookstore.model.Review;
import com.bookstore.model.User;
import com.bookstore.repository.ReviewRepository;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByBook(String bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getReviewsByCustomer(String customerId) {
        return reviewRepository.findByCustomerId(customerId);
    }

    public Review addReview(Review review) {
        // Set customer name
        Optional<User> userOpt = userRepository.findById(review.getCustomerId());
        userOpt.ifPresent(user -> {
            String fullName = user.getFirstName() + " " + user.getLastName();
            review.setCustomerName(fullName.trim());
        });

        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }
}

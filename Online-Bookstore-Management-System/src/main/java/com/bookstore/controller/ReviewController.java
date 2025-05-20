package com.bookstore.controller;

import com.bookstore.model.Review;
import com.bookstore.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/book/{bookId}")
    public String getBookReviews(@PathVariable String bookId, Model model) {
        model.addAttribute("reviews", reviewService.getReviewsByBook(bookId));
        model.addAttribute("bookId", bookId);
        return "reviews/book-reviews";
    }

    @GetMapping("/add/{bookId}")
    public String showAddReviewForm(@PathVariable String bookId, Model model, HttpSession session) {
        // Check if user is logged in
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Review review = new Review();
        review.setBookId(bookId);
        review.setRating(5); // Default to 5 stars
        model.addAttribute("review", review);
        return "reviews/add";
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            review.setCustomerId(userId);
            reviewService.addReview(review);
            return "redirect:/books/" + review.getBookId();
        }
        return "redirect:/login";
    }

    @GetMapping("/edit/{id}")
    public String showEditReviewForm(@PathVariable String id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Review> reviewOpt = reviewService.getReviewById(id);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();

            // Check if the review belongs to the logged-in user
            if (!review.getCustomerId().equals(userId)) {
                return "redirect:/books/" + review.getBookId();
            }

            model.addAttribute("review", review);
            return "reviews/edit";
        }

        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String updateReview(@PathVariable String id, @ModelAttribute Review review, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            review.setId(id);
            review.setCustomerId(userId);
            reviewService.updateReview(review);
            return "redirect:/books/" + review.getBookId();
        }
        return "redirect:/login";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Review> reviewOpt = reviewService.getReviewById(id);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            String bookId = review.getBookId();

            // Allow deletion if the user is the owner of the review or an admin
            if (review.getCustomerId().equals(userId) || "ADMIN".equals(userRole)) {
                reviewService.deleteReview(id);
            }

            return "redirect:/books/" + bookId;
        }

        return "redirect:/";
    }
}

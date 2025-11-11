package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.Review;
import com.VirtualBookStore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    //  Submit review using JSON body instead of query params
    @PostMapping("/submit")
    public ResponseEntity<?> submitReview(@RequestBody Map<String, Object> request) {
        try {
            Long bookId = ((Number) request.get("bookId")).longValue();
            Long userId = ((Number) request.get("userId")).longValue();
            int rating = (int) request.get("rating");
            String comment = (String) request.get("comment");

            Review review = reviewService.submitReview(bookId, userId, rating, comment);
            return ResponseEntity.ok(review);

        } catch (RuntimeException e) {
            // Catch duplicate review or invalid user/book errors
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Something went wrong"));
        }
    }

    //  Get all reviews for a specific book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getReviewsForBook(bookId));
    }
}
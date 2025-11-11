package com.VirtualBookStore.service;

import com.VirtualBookStore.dto.Review;

import java.util.List;

public interface ReviewService {
    Review submitReview(Long bookId, Long userId, int rating, String comment);

    // Get all reviews for a specific book
    List<Review> getReviewsForBook(Long bookId);
}
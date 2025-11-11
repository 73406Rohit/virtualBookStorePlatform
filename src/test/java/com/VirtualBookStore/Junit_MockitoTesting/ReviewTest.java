package com.VirtualBookStore.Junit_MockitoTesting;

import com.VirtualBookStore.controller.ReviewController;
import com.VirtualBookStore.dto.Review;
import com.VirtualBookStore.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ReviewTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitReviewSuccessfully() {
        Map<String, Object> request = new HashMap<>();
        request.put("bookId", 1L);
        request.put("userId", 2L);
        request.put("rating", 5);
        request.put("comment", "Great Book!");

        Review mockReview = new Review();
        mockReview.setId(10L);
        mockReview.setRating(5);
        mockReview.setComment("Great Book!");

        when(reviewService.submitReview(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(mockReview);

        ResponseEntity<?> response = reviewController.submitReview(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isInstanceOf(Review.class);
        Review returnedReview = (Review) response.getBody();
        assertThat(returnedReview.getId()).isEqualTo(10L);
        assertThat(returnedReview.getRating()).isEqualTo(5);
        assertThat(returnedReview.getComment()).isEqualTo("Great Book!");
    }

    @Test
    void testSubmitReviewDuplicateReview() {
        Map<String, Object> request = new HashMap<>();
        request.put("bookId", 1L);
        request.put("userId", 2L);
        request.put("rating", 5);
        request.put("comment", "Great Book!");

        when(reviewService.submitReview(anyLong(), anyLong(), anyInt(), anyString()))
                .thenThrow(new RuntimeException("You have already reviewed this book."));

        ResponseEntity<?> response = reviewController.submitReview(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isInstanceOf(Map.class);
        Map<?, ?> errorResponse = (Map<?, ?>) response.getBody();
        assertThat(errorResponse.get("error")).isEqualTo("You have already reviewed this book.");
    }
}

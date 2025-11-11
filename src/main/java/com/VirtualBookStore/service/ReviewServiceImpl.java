package com.VirtualBookStore.service;

import com.VirtualBookStore.dao.BookRepository;
import com.VirtualBookStore.dao.ReviewRepository;
import com.VirtualBookStore.dao.UserRepository;
import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.Review;
import com.VirtualBookStore.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public Review submitReview(Long bookId, Long userId, int rating, String comment) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // ✅ Prevent duplicate review
        if (reviewRepository.findByBookIdAndUserId(bookId, userId).isPresent()) {
            throw new RuntimeException("You have already reviewed this book.");
        }

        Review review = Review.builder()
                .book(book)
                .user(user)
                .rating(rating)
                .comment(comment)
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findAllByBookId(bookId);
    }
}

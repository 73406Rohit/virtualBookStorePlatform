package com.VirtualBookStore.dao;

import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.Review;
import com.VirtualBookStore.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review> findByBookIdAndUserId(Long bookId, Long userId);
    List<Review> findAllByBookId(Long bookId);
}

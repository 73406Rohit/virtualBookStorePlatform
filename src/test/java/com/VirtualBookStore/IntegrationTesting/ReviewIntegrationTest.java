package com.VirtualBookStore.IntegrationTesting;

import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.User;
import com.VirtualBookStore.dto.Review;
import com.VirtualBookStore.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // assuming you have application-test.properties for H2 etc
public class ReviewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    public void testSubmitReview_Success() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("bookId", 1L);
        request.put("userId", 2L);
        request.put("rating", 4);
        request.put("comment", "Nice book");

        Book book = new Book();
        book.setId(1L);

        User user = new User();
        user.setId(2L);

        Review review = new Review();
        review.setId(10L);
        review.setBook(book);
        review.setUser(user);
        review.setRating(4);
        review.setComment("Nice book");

        when(reviewService.submitReview(anyLong(), anyLong(), anyInt(), anyString()))
                .thenReturn(review);

        mockMvc.perform(post("/api/reviews/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.book.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(2L))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Nice book"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testSubmitReview_Duplicate() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("bookId", 1L);
        request.put("userId", 2L);
        request.put("rating", 4);
        request.put("comment", "Nice book");

        when(reviewService.submitReview(anyLong(), anyLong(), anyInt(), anyString()))
                .thenThrow(new RuntimeException("You have already reviewed this book."));

        mockMvc.perform(post("/api/reviews/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("You have already reviewed this book."));
    }
}

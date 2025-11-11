package com.VirtualBookStore.IntegrationTesting;

import com.VirtualBookStore.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Use test profile to load test-specific config
public class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")  // Mock admin user for security
    public void testAddBook_Success() throws Exception {
        Book bookToAdd = new Book();
        bookToAdd.setTitle("JUnit Testing Basics");
        bookToAdd.setAuthor("Test Author");
        bookToAdd.setPrice(250.0);
        bookToAdd.setStock(50);

        mockMvc.perform(post("/book/addbook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookToAdd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("JUnit Testing Basics"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(250.0))
                .andExpect(jsonPath("$.stock").value(50));
    }
}

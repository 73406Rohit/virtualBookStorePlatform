package com.VirtualBookStore.IntegrationTesting;

import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.Cart;
import com.VirtualBookStore.dto.CartRequest;
import com.VirtualBookStore.dto.User;
import com.VirtualBookStore.service.CartService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // To use application-test.properties with H2 DB configuration
public class CartIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER") // Simulate an authenticated USER
    public void testAddToCartSuccessfully() throws Exception {
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setBookId(2L);
        cartRequest.setQuantity(3);

        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(2L);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setBook(book);
        cart.setQuantity(3);

        // Mock the service addToCart method
        when(cartService.addToCart(anyLong(), anyLong(), anyInt())).thenReturn(cart);

        // Perform POST request and assert response
        mockMvc.perform(post("/api/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.book.id").value(2L))
                .andExpect(jsonPath("$.quantity").value(3));
    }
}

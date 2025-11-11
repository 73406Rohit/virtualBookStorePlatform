package com.VirtualBookStore.Junit_MockitoTesting;

import com.VirtualBookStore.controller.CartController;
import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.Cart;
import com.VirtualBookStore.dto.CartRequest;
import com.VirtualBookStore.dto.User;
import com.VirtualBookStore.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CartTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddToCart() {
        // Arrange
        CartRequest cartRequest = new CartRequest();
        cartRequest.setUserId(1L);
        cartRequest.setBookId(2L);
        cartRequest.setQuantity(3);

        // Create user and book DTOs or entities if Cart embeds them
        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(2L);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);           // set User object to Cart
        cart.setBook(book);           // set Book object to Cart
        cart.setQuantity(3);

        when(cartService.addToCart(1L, 2L, 3)).thenReturn(cart);

        // Act
        ResponseEntity<Cart> response = cartController.addToCart(cartRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUser().getId()).isEqualTo(1L);   // check by nested object
        assertThat(response.getBody().getBook().getId()).isEqualTo(2L);
        assertThat(response.getBody().getQuantity()).isEqualTo(3);
    }
}
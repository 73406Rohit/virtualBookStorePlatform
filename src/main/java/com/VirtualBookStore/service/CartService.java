package com.VirtualBookStore.service;

import com.VirtualBookStore.dto.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Long userId, Long bookId, int quantity);
    List<Cart> getCartsByUser(Long userId);

    String removeFromCart(Long cartId);
}

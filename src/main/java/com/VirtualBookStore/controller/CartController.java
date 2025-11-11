package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.Cart;
import com.VirtualBookStore.dto.CartRequest;
import com.VirtualBookStore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // add book to cart
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartRequest request) {
        Cart cart = cartService.addToCart(request.getUserId(), request.getBookId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

     // get all books add by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartsByUser(userId));
    }

    // removed books from cart by user id
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.removeFromCart(cartId));
    }
}

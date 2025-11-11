package com.VirtualBookStore.service;

import com.VirtualBookStore.dao.BookRepository;
import com.VirtualBookStore.dao.CartRepository;
import com.VirtualBookStore.dao.UserRepository;
import com.VirtualBookStore.dto.Book;
import com.VirtualBookStore.dto.Cart;
import com.VirtualBookStore.dto.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Cart addToCart(Long userId, Long bookId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        if (book.getStock() == null || book.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for '" + book.getTitle() + "'");
        }

        Cart cart = cartRepository.findByUserAndBook(user, book);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantity);
        } else {
            cart = Cart.builder()
                    .user(user)
                    .book(book)
                    .quantity(quantity)
                    .build();
        }

        book.setStock(book.getStock() - quantity);
        bookRepository.save(book);

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCartsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return cartRepository.findByUser(user);
    }

    @Override
    @Transactional
    public String removeFromCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        Book book = cart.getBook();
        if (book != null && cart.getQuantity() != null) {
            book.setStock(book.getStock() + cart.getQuantity());
            bookRepository.save(book);
        }

        cartRepository.delete(cart);
        return "Cart item removed successfully!";
    }
}

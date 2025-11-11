package com.VirtualBookStore.service;

import com.VirtualBookStore.dao.*;
import com.VirtualBookStore.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cannot place order: Your cart is empty!");
        }

        double totalAmount = cartItems.stream()
                .mapToDouble(c -> c.getBook().getPrice() * c.getQuantity())
                .sum();

        Order order = Order.builder()
                .user(user)
                .total(totalAmount)
                .status("PLACED")
                .createdAt(LocalDateTime.now())
                .build();

        order.setItems(
                cartItems.stream().map(cart ->
                        OrderItem.builder()
                                .order(order)
                                .book(cart.getBook())
                                .quantity(cart.getQuantity())
                                .price(cart.getBook().getPrice())
                                .build()
                ).collect(Collectors.toList())
        );

        Order savedOrder = orderRepository.save(order);

        // Clear the cart after placing the order
        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }
}

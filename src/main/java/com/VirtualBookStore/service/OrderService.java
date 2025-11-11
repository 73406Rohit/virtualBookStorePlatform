package com.VirtualBookStore.service;
import com.VirtualBookStore.dto.Order;
import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    List<Order> getOrdersByUser(Long userId);
    List<Order> getAllOrders();
    Order getOrderById(Long orderId);
}


package com.VirtualBookStore.dao;

import com.VirtualBookStore.dto.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}

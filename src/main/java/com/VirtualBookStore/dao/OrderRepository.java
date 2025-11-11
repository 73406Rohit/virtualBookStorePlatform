package com.VirtualBookStore.dao;

import com.VirtualBookStore.dto.Order;
import com.VirtualBookStore.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);

}

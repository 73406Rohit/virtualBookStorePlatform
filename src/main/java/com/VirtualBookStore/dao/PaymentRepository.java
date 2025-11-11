package com.VirtualBookStore.dao;

import com.VirtualBookStore.dto.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findByRazorpayOrderId(String razorpayOrderId);
    Payment findByOrderId(Long orderId);
}

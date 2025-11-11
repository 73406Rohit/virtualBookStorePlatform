package com.VirtualBookStore.service;

import com.VirtualBookStore.dto.Order;
import com.VirtualBookStore.dto.Payment;

public interface PaymentService {

    Payment createPayment(Order order) throws Exception;
    void updatePaymentStatus(String razorpayOrderId,String razorpayPaymentId,String status);

    Payment getPaymentByRazorpayOrderId(String razorpayOrderId);
    Payment getPaymentByOrderId(Long orderId);

}

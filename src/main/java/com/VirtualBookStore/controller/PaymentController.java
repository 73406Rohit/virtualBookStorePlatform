package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.Order;
import com.VirtualBookStore.dto.Payment;
import com.VirtualBookStore.service.OrderService;
import com.VirtualBookStore.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    //   Create Razorpay payment for an existing order
    @PostMapping("/makepayment/{orderId}")
    public ResponseEntity<?> createPayment(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            Payment payment = paymentService.createPayment(order);

            // Return payment details and Razorpay key for frontend integration
            return ResponseEntity.ok(new PaymentResponse(order, payment, razorpayKeyId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating payment: " + e.getMessage());
        }
    }

    //  Update payment status (called after successful payment)
    @PostMapping("/update-status")
    public ResponseEntity<String> updatePayment(@RequestParam String razorpay_order_id,
                                                @RequestParam String razorpay_payment_id,
                                                @RequestParam String status) {
        paymentService.updatePaymentStatus(razorpay_order_id, razorpay_payment_id, status);
        return ResponseEntity.ok("Payment updated successfully");
    }

    // ✅ Get Payment Details (optional)
    @GetMapping("/{razorpayOrderId}")
    public ResponseEntity<Payment> getPayment(@PathVariable String razorpayOrderId) {
        Payment payment = paymentService.getPaymentByRazorpayOrderId(razorpayOrderId);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    //  Helper class for API response
    record PaymentResponse(Order order, Payment payment, String razorpayKeyId) {}
}

package com.VirtualBookStore.controller;

import com.VirtualBookStore.service.PaymentService;
import com.VirtualBookStore.service.OrderService;
import com.VirtualBookStore.dto.Payment;
import com.VirtualBookStore.dto.Order;
import com.VirtualBookStore.controller.PaymentController.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PaymentViewController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    // ✅ Inject Razorpay key id from application.properties
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    /**
     * Show initial payment page with orderId input form.
     */
    @GetMapping("/payment")
    public String showPaymentPage() {
        return "payment";  // Thymeleaf template page
    }

    /**
     * Show payment page populated with Order and Payment details for given orderId.
     * Passes PaymentResponse including the razorpayKeyId to frontend.
     */
    @GetMapping(value = "/payment", params = "orderId")
    public String showPaymentDetails(@RequestParam String orderId, Model model) {
        try {
            Order order = orderService.getOrderById(Long.parseLong(orderId));
            if (order == null) {
                model.addAttribute("errorMessage", "Invalid Order ID");
                return "payment";
            }

            Payment payment = paymentService.getPaymentByOrderId(order.getId());
            if (payment == null) {
                payment = paymentService.createPayment(order);
            }

            // ✅ Ensure key is passed in PaymentResponse
            PaymentResponse paymentResponse = new PaymentResponse(order, payment, razorpayKeyId);
            model.addAttribute("paymentResponse", paymentResponse);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading payment information: " + e.getMessage());
        }
        return "payment";
    }

    @GetMapping("/orderSuccess")
    public String showOrderSuccess(@RequestParam(required = false) String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "orderSuccess";
    }

    @GetMapping("/paymentError")
    public String showPaymentError(Model model) {
        model.addAttribute("message", "Something went wrong during payment. Please try again.");
        return "error";
    }
}

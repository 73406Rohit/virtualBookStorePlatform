package com.VirtualBookStore.controller;

import com.VirtualBookStore.dto.Order;
import com.VirtualBookStore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderViewController {

    private final OrderService orderService;

    //  This controller is only for frontend (Thymeleaf/UI) redirection
    @PostMapping("/order/place/{userId}")
    public String placeOrderAndRedirect(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        // Redirect user to Razorpay payment page (frontend endpoint)
        return "redirect:/payment?orderId=" + order.getId();
    }
}

package com.VirtualBookStore.service;

import com.VirtualBookStore.dao.OrderRepository;
import com.VirtualBookStore.dao.PaymentRepository;
import com.VirtualBookStore.dto.Order;
import com.VirtualBookStore.dto.Payment;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.secret.key}")
    private String razorpaySecretKey;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Payment createPayment(Order order) throws Exception {
        // ✅ Initialize Razorpay client
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpaySecretKey);

        // ✅ Prepare Razorpay order request
        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", (int) (order.getTotal() * 100)); // amount in paise
        orderReq.put("currency", "INR");
        orderReq.put("receipt", "order_" + order.getId());

        // ✅ Create order on Razorpay
        com.razorpay.Order razorOrder = client.orders.create(orderReq);

        // ✅ Save payment record
        Payment payment = Payment.builder()
                .razorpayOrderId(razorOrder.get("id"))
                .status(razorOrder.get("status"))
                .amount(order.getTotal())
                .order(order)
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(String razorpayOrderId, String razorpayPaymentId, String status) {
        Payment payment = paymentRepository.findByRazorpayOrderId(razorpayOrderId);
        if (payment != null) {
            payment.setRazorpayPaymentId(razorpayPaymentId);
            payment.setStatus(status);
            paymentRepository.save(payment);

            // ✅ Update order status too
            Order order = payment.getOrder();
            if (order != null) {
                order.setStatus("PAID");
                orderRepository.save(order);
            }
        }
    }

    @Override
    public Payment getPaymentByRazorpayOrderId(String razorpayOrderId) {
        return paymentRepository.findByRazorpayOrderId(razorpayOrderId);
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

}

package com.bookstore.service;

import com.bookstore.model.Order;
import com.bookstore.model.Payment;
import com.bookstore.repository.OrderRepository;
import com.bookstore.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public Optional<Payment> getPaymentById(String id) {
        return paymentRepository.findById(id);
    }
    
    public Optional<Payment> getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
    
    public List<Payment> getPaymentsByCustomer(String customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }
    
    public Payment processPayment(Payment payment) {
        // Generate transaction ID
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());
        
        // Process payment (in a real system, this would integrate with a payment gateway)
        payment.setStatus("COMPLETED");
        
        // Update order status
        Optional<Order> orderOpt = orderRepository.findById(payment.getOrderId());
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus("PAID");
            order.setPaymentId(payment.getId());
            orderRepository.save(order);
        }
        
        return paymentRepository.save(payment);
    }
    
    public Payment updatePaymentStatus(String paymentId, String status) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setStatus(status);
            return paymentRepository.save(payment);
        }
        return null;
    }
    
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }
}

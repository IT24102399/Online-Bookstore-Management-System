package com.bookstore.repository;

import com.bookstore.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PaymentRepository extends AbstractFileRepository<Payment> {
    
    public PaymentRepository() {
        super(Payment.class, "payments.json");
    }
    
    public List<Payment> findByCustomerId(String customerId) {
        return findAll().stream()
                .filter(payment -> payment.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    
    public Optional<Payment> findByOrderId(String orderId) {
        return findAll().stream()
                .filter(payment -> payment.getOrderId().equals(orderId))
                .findFirst();
    }
    
    public List<Payment> findByStatus(String status) {
        return findAll().stream()
                .filter(payment -> payment.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}

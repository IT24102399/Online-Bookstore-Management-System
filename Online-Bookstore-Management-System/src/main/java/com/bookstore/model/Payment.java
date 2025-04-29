package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseEntity {
    private String orderId;
    private String customerId;
    private double amount;
    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, PAYPAL, etc.
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED
    private LocalDateTime paymentDate;
    private String transactionId;
    
    public Payment() {
        super();
        this.paymentDate = LocalDateTime.now();
        this.status = "PENDING";
    }
}

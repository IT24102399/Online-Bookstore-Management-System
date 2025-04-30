package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
    private String customerId;
    private List<OrderItem> items = new ArrayList<>();
    private double totalAmount;
    private String status; // PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
    private String deliveryAgentId;
    private String shippingAddress;
    private LocalDateTime orderDate;
    private String paymentId;
    
    public Order() {
        super();
        this.orderDate = LocalDateTime.now();
        this.status = "PENDING";
    }
    
    @Data
    public static class OrderItem {
        private String bookId;
        private String bookTitle;
        private int quantity;
        private double price;
    }
}

package com.bookstore.repository;

import com.bookstore.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepository extends AbstractFileRepository<Order> {
    
    public OrderRepository() {
        super(Order.class, "orders.json");
    }
    
    public List<Order> findByCustomerId(String customerId) {
        return findAll().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    
    public List<Order> findByDeliveryAgentId(String deliveryAgentId) {
        return findAll().stream()
                .filter(order -> order.getDeliveryAgentId() != null && 
                       order.getDeliveryAgentId().equals(deliveryAgentId))
                .collect(Collectors.toList());
    }
    
    public List<Order> findByStatus(String status) {
        return findAll().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}

package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    
    @Autowired
    public OrderService(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public List<Order> getOrdersByDeliveryAgent(String deliveryAgentId) {
        return orderRepository.findByDeliveryAgentId(deliveryAgentId);
    }
    
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    public Order createOrder(Order order) {
        // Calculate total amount
        double totalAmount = 0.0;
        for (Order.OrderItem item : order.getItems()) {
            Optional<Book> bookOpt = bookRepository.findById(item.getBookId());
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                item.setBookTitle(book.getTitle());
                item.setPrice(book.getPrice());
                totalAmount += (book.getPrice() * item.getQuantity());
                
                // Update book quantity
                book.setQuantity(book.getQuantity() - item.getQuantity());
                bookRepository.save(book);
            }
        }
        order.setTotalAmount(totalAmount);
        
        return orderRepository.save(order);
    }
    
    public Order updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
    
    public Order assignDeliveryAgent(String orderId, String deliveryAgentId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setDeliveryAgentId(deliveryAgentId);
            return orderRepository.save(order);
        }
        return null;
    }
    
    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}

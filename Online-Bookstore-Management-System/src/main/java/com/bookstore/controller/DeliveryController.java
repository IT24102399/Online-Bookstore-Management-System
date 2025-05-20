package com.bookstore.controller;

import com.bookstore.model.Order;
import com.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    private final OrderService orderService;

    @Autowired
    public DeliveryController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"DELIVERY_AGENT".equals(userRole)) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getOrdersByDeliveryAgent(userId);
        model.addAttribute("orders", orders);

        return "delivery/dashboard";
    }

    @GetMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable String id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"DELIVERY_AGENT".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Check if the order is assigned to this delivery agent
            if (!order.getDeliveryAgentId().equals(userId)) {
                return "redirect:/delivery/dashboard";
            }

            model.addAttribute("order", order);
            return "delivery/order-details";
        }

        return "redirect:/delivery/dashboard";
    }

    @PostMapping("/orders/{id}/update-status")
    public String updateOrderStatus(@PathVariable String id, @RequestParam String status, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId == null || !"DELIVERY_AGENT".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Check if the order is assigned to this delivery agent
            if (!order.getDeliveryAgentId().equals(userId)) {
                return "redirect:/delivery/dashboard";
            }

            // Only allow certain status updates (e.g., SHIPPED, DELIVERED)
            if (status.equals("SHIPPED") || status.equals("DELIVERED")) {
                orderService.updateOrderStatus(id, status);
            }
        }

        return "redirect:/delivery/orders/" + id;
    }
}

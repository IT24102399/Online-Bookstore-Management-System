package com.bookstore.controller;

import com.bookstore.model.DeliveryAgent;
import com.bookstore.model.Order;
import com.bookstore.service.OrderService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String getOrders(HttpSession session, Model model) {
        String userRole = (String) session.getAttribute("userRole");
        String userId = (String) session.getAttribute("userId");

        if (userRole == null || userId == null) {
            return "redirect:/login";
        }

        List<Order> orders;

        if (userRole.equals("ADMIN")) {
            orders = orderService.getAllOrders();
        } else if (userRole.equals("CUSTOMER")) {
            orders = orderService.getOrdersByCustomer(userId);
        } else if (userRole.equals("DELIVERY_AGENT")) {
            orders = orderService.getOrdersByDeliveryAgent(userId);
        } else {
            return "redirect:/";
        }

        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable String id, Model model) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            return "orders/details";
        }
        return "redirect:/orders";
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute Order order, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            order.setCustomerId(userId);
            orderService.createOrder(order);
            return "redirect:/orders";
        }
        return "redirect:/login";
    }

    @PostMapping("/{id}/update-status")
    public String updateOrderStatus(@PathVariable String id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/{id}/assign-delivery")
    public String showAssignDeliveryForm(@PathVariable String id, Model model) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            List<DeliveryAgent> deliveryAgents = userService.getAllDeliveryAgents();
            model.addAttribute("deliveryAgents", deliveryAgents);
            return "orders/assign-delivery";
        }
        return "redirect:/orders";
    }

    @PostMapping("/{id}/assign-delivery")
    public String assignDeliveryAgent(@PathVariable String id, @RequestParam String deliveryAgentId) {
        orderService.assignDeliveryAgent(id, deliveryAgentId);
        return "redirect:/orders/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}

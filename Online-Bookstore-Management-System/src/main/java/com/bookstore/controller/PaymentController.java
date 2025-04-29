package com.bookstore.controller;

import com.bookstore.model.Order;
import com.bookstore.model.Payment;
import com.bookstore.service.OrderService;
import com.bookstore.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Autowired
    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getPayments(HttpSession session, Model model) {
        String userRole = (String) session.getAttribute("userRole");
        String userId = (String) session.getAttribute("userId");

        if (userRole == null || userId == null) {
            return "redirect:/login";
        }

        List<Payment> payments;

        if (userRole.equals("ADMIN")) {
            payments = paymentService.getAllPayments();
        } else if (userRole.equals("CUSTOMER")) {
            payments = paymentService.getPaymentsByCustomer(userId);
        } else {
            return "redirect:/";
        }

        model.addAttribute("payments", payments);
        return "payments/list";
    }

    @GetMapping("/{id}")
    public String getPaymentDetails(@PathVariable String id, Model model) {
        Optional<Payment> paymentOpt = paymentService.getPaymentById(id);
        if (paymentOpt.isPresent()) {
            model.addAttribute("payment", paymentOpt.get());
            return "payments/details";
        }
        return "redirect:/payments";
    }

    @GetMapping("/process/{orderId}")
    public String showPaymentForm(@PathVariable String orderId, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<Order> orderOpt = orderService.getOrderById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();

            // Check if the order belongs to the logged-in user
            if (!order.getCustomerId().equals(userId)) {
                return "redirect:/orders";
            }

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setCustomerId(userId);
            payment.setAmount(order.getTotalAmount());

            model.addAttribute("order", order);
            model.addAttribute("payment", payment);

            return "payments/process";
        }

        return "redirect:/orders";
    }

    @PostMapping("/process")
    public String processPayment(@ModelAttribute Payment payment, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            payment.setCustomerId(userId);
            paymentService.processPayment(payment);
            return "redirect:/payments";
        }
        return "redirect:/login";
    }

    @PostMapping("/{id}/update-status")
    public String updatePaymentStatus(@PathVariable String id, @RequestParam String status) {
        paymentService.updatePaymentStatus(id, status);
        return "redirect:/payments/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable String id) {
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }
}

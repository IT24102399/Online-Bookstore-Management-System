package com.bookstore.controller;

import com.bookstore.model.*;
import com.bookstore.service.BookService;
import com.bookstore.service.OrderService;
import com.bookstore.service.PaymentService;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    @Autowired
    public AdminController(UserService userService, BookService bookService,
            OrderService orderService, PaymentService paymentService) {
        this.userService = userService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<Book> books = bookService.getAllBooks();
        List<Order> orders = orderService.getAllOrders();
        List<Customer> customers = userService.getAllCustomers();
        List<Vendor> vendors = userService.getAllVendors();
        List<DeliveryAgent> deliveryAgents = userService.getAllDeliveryAgents();

        model.addAttribute("bookCount", books.size());
        model.addAttribute("orderCount", orders.size());
        model.addAttribute("customerCount", customers.size());
        model.addAttribute("vendorCount", vendors.size());
        model.addAttribute("deliveryAgentCount", deliveryAgents.size());

        return "admin/dashboard";
    }

    @GetMapping("/books")
    public String manageBooks(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "admin/books";
    }

    @GetMapping("/orders")
    public String manageOrders(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        return "admin/orders";
    }

    @GetMapping("/payments")
    public String managePayments(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);

        return "admin/payments";
    }

    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "admin/users";
    }

    @GetMapping("/add-admin")
    public String showAddAdminForm(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        model.addAttribute("admin", new Admin());
        return "admin/add-admin";
    }

    @PostMapping("/add-admin")
    public String addAdmin(@ModelAttribute Admin admin, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        userService.saveUser(admin);
        return "redirect:/admin/users";
    }

    @GetMapping("/add-vendor")
    public String showAddVendorForm(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        model.addAttribute("vendor", new Vendor());
        return "admin/add-vendor";
    }

    @PostMapping("/add-vendor")
    public String addVendor(@ModelAttribute Vendor vendor, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        userService.saveUser(vendor);
        return "redirect:/admin/users";
    }

    @GetMapping("/add-delivery-agent")
    public String showAddDeliveryAgentForm(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        model.addAttribute("deliveryAgent", new DeliveryAgent());
        return "admin/add-delivery-agent";
    }

    @PostMapping("/add-delivery-agent")
    public String addDeliveryAgent(@ModelAttribute DeliveryAgent deliveryAgent, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        userService.saveUser(deliveryAgent);
        return "redirect:/admin/users";
    }

    @GetMapping("/customers")
    public String manageCustomers(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<Customer> customers = userService.getAllCustomers();
        model.addAttribute("customers", customers);

        return "admin/customers";
    }

    @GetMapping("/vendors")
    public String manageVendors(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<Vendor> vendors = userService.getAllVendors();
        model.addAttribute("vendors", vendors);

        return "admin/vendors";
    }

    @GetMapping("/delivery-agents")
    public String manageDeliveryAgents(Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        List<DeliveryAgent> deliveryAgents = userService.getAllDeliveryAgents();
        model.addAttribute("deliveryAgents", deliveryAgents);

        return "admin/delivery-agents";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/vendors/edit/{id}")
    public String showEditVendorForm(@PathVariable String id, Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent() && userOpt.get() instanceof Vendor) {
            model.addAttribute("vendor", (Vendor) userOpt.get());
            return "admin/edit-vendor";
        }

        return "redirect:/admin/vendors";
    }

    @PostMapping("/vendors/edit/{id}")
    public String updateVendor(@PathVariable String id, @ModelAttribute Vendor vendor, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        vendor.setId(id);
        vendor.setRole("VENDOR"); // Ensure role is preserved
        userService.saveUser(vendor);

        return "redirect:/admin/vendors";
    }

    @GetMapping("/delivery-agents/edit/{id}")
    public String showEditDeliveryAgentForm(@PathVariable String id, Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent() && userOpt.get() instanceof DeliveryAgent) {
            model.addAttribute("deliveryAgent", (DeliveryAgent) userOpt.get());
            return "admin/edit-delivery-agent";
        }

        return "redirect:/admin/delivery-agents";
    }

    @PostMapping("/delivery-agents/edit/{id}")
    public String updateDeliveryAgent(@PathVariable String id, @ModelAttribute DeliveryAgent deliveryAgent,
            HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        deliveryAgent.setId(id);
        deliveryAgent.setRole("DELIVERY_AGENT"); // Ensure role is preserved
        userService.saveUser(deliveryAgent);

        return "redirect:/admin/delivery-agents";
    }

    @GetMapping("/orders/assign-delivery/{id}")
    public String showAssignDeliveryForm(@PathVariable String id, Model model, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            List<DeliveryAgent> deliveryAgents = userService.getAllDeliveryAgents();

            model.addAttribute("order", order);
            model.addAttribute("deliveryAgents", deliveryAgents);

            return "orders/assign-delivery";
        }

        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/assign-delivery/{id}")
    public String assignDelivery(@PathVariable String id, @RequestParam String deliveryAgentId, HttpSession session) {
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            return "redirect:/login";
        }

        orderService.assignDeliveryAgent(id, deliveryAgentId);
        orderService.updateOrderStatus(id, "PROCESSING");

        return "redirect:/admin/orders";
    }
}

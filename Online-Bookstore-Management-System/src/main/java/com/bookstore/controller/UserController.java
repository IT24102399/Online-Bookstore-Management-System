package com.bookstore.controller;

import com.bookstore.model.Customer;
import com.bookstore.model.User;
import com.bookstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
            HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.authenticateUser(username, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userRole", user.getRole());

            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        if (!userService.isUsernameAvailable(customer.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username already taken");
            return "redirect:/register";
        }

        if (!userService.isEmailAvailable(customer.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email already in use");
            return "redirect:/register";
        }

        userService.saveUser(customer);
        redirectAttributes.addFlashAttribute("success", "Registration successful. Please login.");
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            Optional<User> userOpt = userService.getUserById(userId);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("user", user);

                // Add role-specific attributes
                if (user instanceof Customer) {
                    Customer customer = (Customer) user;
                    model.addAttribute("address", customer.getAddress());
                    model.addAttribute("phone", customer.getPhone());
                } else if (user instanceof Vendor) {
                    Vendor vendor = (Vendor) user;
                    model.addAttribute("companyName", vendor.getCompanyName());
                    model.addAttribute("address", vendor.getAddress());
                    model.addAttribute("businessRegistrationNumber", vendor.getBusinessRegistrationNumber());
                    model.addAttribute("phone", vendor.getPhone());
                } else if (user instanceof DeliveryAgent) {
                    DeliveryAgent agent = (DeliveryAgent) user;
                    model.addAttribute("vehicleNumber", agent.getVehicleNumber());
                    model.addAttribute("vehicleType", agent.getVehicleType());
                    model.addAttribute("deliveryArea", agent.getDeliveryArea());
                    model.addAttribute("address", agent.getAddress());
                    model.addAttribute("phone", agent.getPhone());
                } else if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    model.addAttribute("department", admin.getDepartment());
                    model.addAttribute("phone", admin.getPhone());
                }

                return "users/profile";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User user,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String businessRegistrationNumber,
            @RequestParam(required = false) String vehicleNumber,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) String deliveryArea,
            @RequestParam(required = false) String department,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        String userId = (String) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("userRole");

        if (userId != null) {
            // Get the existing user to preserve the role and other fields
            Optional<User> existingUserOpt = userService.getUserById(userId);

            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();

                // Update common fields
                existingUser.setFirstName(user.getFirstName());
                existingUser.setLastName(user.getLastName());
                existingUser.setEmail(user.getEmail());

                // Only update password if it's not empty
                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    existingUser.setPassword(user.getPassword());
                }

                // Update role-specific fields
                if ("CUSTOMER".equals(userRole) && existingUser instanceof Customer) {
                    Customer customer = (Customer) existingUser;
                    if (address != null)
                        customer.setAddress(address);
                    if (phone != null)
                        customer.setPhone(phone);
                } else if ("VENDOR".equals(userRole) && existingUser instanceof Vendor) {
                    Vendor vendor = (Vendor) existingUser;
                    if (companyName != null)
                        vendor.setCompanyName(companyName);
                    if (address != null)
                        vendor.setAddress(address);
                    if (businessRegistrationNumber != null)
                        vendor.setBusinessRegistrationNumber(businessRegistrationNumber);
                    if (phone != null)
                        vendor.setPhone(phone);
                } else if ("DELIVERY_AGENT".equals(userRole) && existingUser instanceof DeliveryAgent) {
                    DeliveryAgent agent = (DeliveryAgent) existingUser;
                    if (vehicleNumber != null)
                        agent.setVehicleNumber(vehicleNumber);
                    if (vehicleType != null)
                        agent.setVehicleType(vehicleType);
                    if (deliveryArea != null)
                        agent.setDeliveryArea(deliveryArea);
                    if (address != null)
                        agent.setAddress(address);
                    if (phone != null)
                        agent.setPhone(phone);
                } else if ("ADMIN".equals(userRole) && existingUser instanceof Admin) {
                    Admin admin = (Admin) existingUser;
                    if (department != null)
                        admin.setDepartment(department);
                    if (phone != null)
                        admin.setPhone(phone);
                }

                // Save the updated user
                userService.saveUser(existingUser);

                // Update the session
                session.setAttribute("user", existingUser);

                redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
            }
        }

        return "redirect:/users/profile";
    }

    // Admin section
    @GetMapping("/admin/customers")
    public String listCustomers(Model model) {
        List<Customer> customers = userService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customers";
    }

    @GetMapping("/admin/vendors")
    public String listVendors(Model model) {
        List<Vendor> vendors = userService.getAllVendors();
        model.addAttribute("vendors", vendors);
        return "admin/vendors";
    }

    @GetMapping("/admin/delivery-agents")
    public String listDeliveryAgents(Model model) {
        List<DeliveryAgent> deliveryAgents = userService.getAllDeliveryAgents();
        model.addAttribute("deliveryAgents", deliveryAgents);
        return "admin/delivery-agents";
    }

    @GetMapping("/admin/add-vendor")
    public String showAddVendorForm(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "admin/add-vendor";
    }

    @PostMapping("/admin/add-vendor")
    public String addVendor(@ModelAttribute Vendor vendor) {
        userService.saveUser(vendor);
        return "redirect:/admin/vendors";
    }

    @GetMapping("/admin/add-delivery-agent")
    public String showAddDeliveryAgentForm(Model model) {
        model.addAttribute("deliveryAgent", new DeliveryAgent());
        return "admin/add-delivery-agent";
    }

    @PostMapping("/admin/add-delivery-agent")
    public String addDeliveryAgent(@ModelAttribute DeliveryAgent deliveryAgent) {
        userService.saveUser(deliveryAgent);
        return "redirect:/admin/delivery-agents";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable String id, @RequestParam(required = false) String type) {
        userService.deleteUser(id);

        if ("vendor".equals(type)) {
            return "redirect:/admin/vendors";
        } else if ("delivery-agent".equals(type)) {
            return "redirect:/admin/delivery-agents";
        } else {
            return "redirect:/admin/customers";
        }
    }
}

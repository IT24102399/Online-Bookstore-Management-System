package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role; // ADMIN, CUSTOMER, VENDOR, DELIVERY_AGENT, GUEST
    private String accountStatus = "ACTIVE"; // ACTIVE, INACTIVE, SUSPENDED

    public User() {
        super();
    }

    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return "";
    }

    public void setFullName(String fullName) {
        if (fullName != null && !fullName.isEmpty()) {
            String[] parts = fullName.split(" ", 2);
            this.firstName = parts[0];
            this.lastName = parts.length > 1 ? parts[1] : "";
        }
    }
}

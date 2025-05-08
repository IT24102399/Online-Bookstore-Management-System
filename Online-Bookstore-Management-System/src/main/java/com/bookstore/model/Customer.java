package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
    private String address;
    private String phone;

    public Customer() {
        super();
        setRole("CUSTOMER");
    }

    // For backward compatibility
    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }
}

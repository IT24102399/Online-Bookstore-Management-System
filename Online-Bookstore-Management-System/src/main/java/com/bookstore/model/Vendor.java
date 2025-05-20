package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Vendor extends User {
    private String companyName;
    private String address; // Business address
    private String businessRegistrationNumber;
    private String phone;

    public Vendor() {
        super();
        setRole("VENDOR");
    }

    // For backward compatibility
    public String getBusinessAddress() {
        return address;
    }

    public void setBusinessAddress(String businessAddress) {
        this.address = businessAddress;
    }

    public String getTaxId() {
        return businessRegistrationNumber;
    }

    public void setTaxId(String taxId) {
        this.businessRegistrationNumber = taxId;
    }
}

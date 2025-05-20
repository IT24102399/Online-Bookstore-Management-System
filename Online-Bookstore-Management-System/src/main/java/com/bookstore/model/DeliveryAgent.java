package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryAgent extends User {
    private String vehicleNumber;
    private String vehicleType = "CAR"; // BIKE, CAR, VAN, TRUCK
    private String deliveryArea;
    private String address;
    private String phone;

    public DeliveryAgent() {
        super();
        setRole("DELIVERY_AGENT");
    }

    // For backward compatibility
    public String getLicenseNumber() {
        return vehicleNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        // Do nothing, field is deprecated
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }
}

package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    private String department;
    private String phone;

    public Admin() {
        super();
        setRole("ADMIN");
    }
}

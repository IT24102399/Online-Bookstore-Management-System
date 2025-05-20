package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantity;
    private String category;
    private String description;
    private String vendorId; // ID of the vendor who sells this book
    
    public Book() {
        super();
    }
}

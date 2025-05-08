package com.bookstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseEntity {
    private String bookId;
    private String customerId;
    private String customerName;
    private Integer rating = 5; // 1-5, default to 5 stars
    private String comment;

    public Review() {
        super();
    }
}

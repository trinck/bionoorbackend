package com.bionoor.api.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discount_codes")
public class DiscountCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne
    private Product product;// the product which is affected by reduce
    
    @ManyToOne
    private Category category;// category affected
    
    private int percentage;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    // Constructors, getters, and setters
}


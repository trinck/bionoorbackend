package com.bionoor.api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Invoice {

    // Primary key for the invoice entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date when the invoice was created
    @Column(nullable = false)
    private LocalDate creationDate;

    // Total amount of the invoice
    @Column(nullable = false)
    private Double totalAmount;

    // User who made the purchase and is associated with this invoice
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Order associated with this invoice
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Default constructor required by JPA
    public Invoice() {}

   
}


package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the payment

    @Column(nullable = false)
    private Double amount; // amount of the payment

    @Column(nullable = false)
    private Date date; // date and time when the payment was made

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // invoice that the payment is associated with

    
    
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice; // invoice that the payment is associated with

    // other properties and methods

}
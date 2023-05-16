package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime date; // date and time when the payment was made

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // order that the payment is associated with

    // other properties and methods

}
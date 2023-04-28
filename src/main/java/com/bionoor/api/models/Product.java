package com.bionoor.api.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the product

    @Column(nullable = false)
    private String name; // product name

    @Column(nullable = false)
    private String description; // product description

    @Column(nullable = false)
    private Double price; // product price

    private String reference;
    private List< String> images; // URL for product image

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // category that the product belongs to

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews; // list of reviews for the product

    // other properties and methods

}


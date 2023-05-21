package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the product

    @Column(nullable = false)
    private String name; // product name

    @Column(nullable = false)
    private String description; // product description

    @Column(nullable = false)
    private Double price; // product price

    @ManyToMany
    @JoinTable(
      name = "discount_product", 
      joinColumns = @JoinColumn(name = "product_id"), 
      inverseJoinColumns = @JoinColumn(name = "discount_id"))
    private List<DiscountCode> discountCodes;

    private String reference;
    
    @ManyToMany
    @JoinTable(
      name = "product_media", 
      joinColumns = @JoinColumn(name = "product_id"), 
      inverseJoinColumns = @JoinColumn(name = "media_id"))
    private List<Media> images; // URL for product image

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // category that the product belongs to

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews; // list of reviews for the product
    
    @ManyToMany(mappedBy = "products")
    private List<Certificat> certificats;

    // other properties and methods

}


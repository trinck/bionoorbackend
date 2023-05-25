package com.bionoor.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
import com.bionoor.api.web.RestDiscount.InputDiscountIn;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "discount_codes")
public class DiscountCode implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToMany(mappedBy = "discountCodes", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<Product> products= new ArrayList<Product>();// the product which is affected by reduce
    
   
    @ManyToMany(mappedBy = "discountCodes", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    private List<Category> categories = new ArrayList<Category>();// category affected
    
    @Column(nullable = false)
    private int percentage;
    
    
    private Boolean actif;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    
    public void setProduct(Product product) {
    	this.products.add(product);
    }
    
    public void setCategory(Category product) {
    	this.categories.add(product);
    }
    
    public DiscountCode(InputDiscountIn discountCategory) {
    	
    	this.code = discountCategory.getCode();
    	this.endDate = discountCategory.getEndDate();
    	this.percentage = discountCategory.getPercentage();
    	
    }

    // Constructors, getters, and setters
}


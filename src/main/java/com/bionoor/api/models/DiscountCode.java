package com.bionoor.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
import com.bionoor.api.web.RestDiscount.InputDiscountIn;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "type")
public abstract class  DiscountCode implements Serializable{
	
	
	@Id
    private Long id;

    private String code;
   
   
    private int discount;
    
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    
    private Boolean actif;
    
    private Boolean used;
    
   // @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endDate;
    
   // @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt;
    
   // @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date modifiedAt;
    
	
	/*
	 * @ManyToMany(fetch = FetchType.LAZY)
	 * 
	 * @JoinTable( name = "discount_product", joinColumns
	 * = @JoinColumn(name="discount_id"), inverseJoinColumns
	 * = @JoinColumn(name="product_id") ) private List< Product> products = new
	 * ArrayList<>();
	 * 
	 * 
	 * @ManyToMany(fetch = FetchType.LAZY)
	 * 
	 * @JoinTable( name = "discount_category", joinColumns
	 * = @JoinColumn(name="discount_id"), inverseJoinColumns
	 * = @JoinColumn(name="category_id") ) private List< Category> categories = new
	 * ArrayList<>();
	 * 
	 * 
	 * @ManyToMany(mappedBy = "usedDiscounts", fetch = FetchType.LAZY, cascade =
	 * CascadeType.PERSIST) private List<Customer> usedBy = new ArrayList<>();
	 */
	 
    
    
    public DiscountCode(InputDiscountIn discountCategory){
    	
    	this.code = discountCategory.getCode();
    	this.endDate = discountCategory.getEndDate();
    	this.discount = discountCategory.getPercentage();
    	
    }

    // Constructors, getters, and setters
}



package com.bionoor.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.dto.InputDiscountIn;
import com.bionoor.api.web.RestDiscount.InputDiscountCategory;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "discount_codes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "application", discriminatorType = DiscriminatorType.STRING)
public abstract class  DiscountCode implements Serializable{
   
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

   @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
   protected Date createdAt;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    protected Date modifiedAt;
    
    @Column(unique = true, nullable = false)
    protected String code;
    
    
    
    @Column(name = "application", insertable = false, updatable = false)
    private String discriminatorValue;

	
    @ManyToMany(mappedBy = "discountCodes", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    protected List<Discountable> discountables = new ArrayList<>();
    
    @Column(nullable = false)
    protected Double discount;
    
    
    protected Boolean actif;

    
    @ManyToMany(mappedBy = "usedDiscountCodes", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    protected List<Customer> usedBy = new ArrayList<>();
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date endDate;
    
   
    
    public DiscountCode(InputDiscountIn discountCategory) {
    	
    	this.code = discountCategory.getCode();
    	this.endDate = discountCategory.getEndDate();
    	this.discount = discountCategory.getDiscount();
    	this.actif =   discountCategory.getActif();  	
    	
    }

    // Constructors, getters, and setters
}


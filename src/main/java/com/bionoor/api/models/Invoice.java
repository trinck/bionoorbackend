package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Invoice implements Serializable{

	
	
    // Primary key for the invoice entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date when the invoice was created
  
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
    private Date createdAt;

    private Boolean paid;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
    private Date modifiedAt;
    
    private Double remise;
    
    @OneToOne()
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    
    @OneToOne()
    @JoinColumn(name = "modified_by_id")
    private User modifiedBy;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
    private Date dueDate;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST}, mappedBy = "invoice")
    private List<Payment> payments = new ArrayList<>();

    // Total amount of the invoice
    @Column(nullable = false)
    private Double totalAmount;

   
   
   
   @OneToOne
   @JoinColumn(name = "order_id")
    private Order order; // Order associated with this invoice

    // Default constructor required by JPA
   

   
}


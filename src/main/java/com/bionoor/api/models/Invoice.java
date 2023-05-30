package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Serializable{

	
    // Primary key for the invoice entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date when the invoice was created
    @Column(nullable = false)
    private Date creationDate;

    // Total amount of the invoice
    @Column(nullable = false)
    private Double totalAmount;
    
    private Boolean paid;
    
    
    @ManyToOne()
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    
    @ManyToOne()
    @JoinColumn(name = "modified_by_id")
    private User modifiedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
    private Date dueDate;
    
    // User who made the purchase and is associated with this invoice
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Payment> payments; // list of payments associated with the invoice

    // Order associated with this invoice
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Default constructor required by JPA
   

   
}


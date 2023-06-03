package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.admin.AdminInvoice.InputInvoice;

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
    private Date createdAt;

    // Total amount of the invoice
    @Column(nullable = false)
    private Double totalAmount;
    
    private Boolean paid;
    
    private Double transport; 
    
    @ManyToOne()
    @JoinColumn(name = "created_by_id")
    private User createdBy;
    
    @ManyToOne()
    @JoinColumn(name = "modified_by_id")
    private User modifiedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
    private Date dueDate;
    
    private int vat;
    private Double remise;
    // User who made the purchase and is associated with this invoice
    @ManyToOne
    @JoinColumn(name = "customer_id" ,nullable = true)
    private Customer customer;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments; // list of payments associated with the invoice

    // Order associated with this invoice
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Default constructor required by JPA
   
    
    public Invoice(InputInvoice inputInvoice) {
    	
    	this.id = inputInvoice.getId();
    	this.remise = inputInvoice.getRemise();
    	this.dueDate = inputInvoice.getDueDate();
    	this.paid = inputInvoice.getPaid();
    	this.vat = inputInvoice.getVat();
    	
    }

   
}


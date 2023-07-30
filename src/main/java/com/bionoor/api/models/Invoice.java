package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.utils.InvoiceListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(InvoiceListener.class)
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
    
    
    private String createdBy;
    
    private String modifiedBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
    private Date dueDate;
    
    private int vat;
    
    private double dueToPay;
    private Double remise;
    // User who made the purchase and is associated with this invoice
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "customer_id" ,nullable = true) private Customer customer;
	 */

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>(); // list of payments associated with the invoice

    // Order associated with this invoice
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Default constructor required by JPA
   
    
    public Invoice(InputOrderInvoiceDTO inputInvoice) {
    	this.id = inputInvoice.getId();
    	this.remise = inputInvoice.getRemise();
    	this.dueDate = inputInvoice.getDueDate();
    	this.paid = inputInvoice.getPaid();
    	this.vat = inputInvoice.getVat();
    	this.transport = inputInvoice.getTransport();
    	
    }
    
    
    public void update(InputOrderInvoiceDTO inputInvoice) {
    	
    	this.remise = inputInvoice.getRemise();
    	this.dueDate = inputInvoice.getDueDate();
    	this.paid = inputInvoice.getPaid();
    	this.vat = inputInvoice.getVat();
    	this.transport = inputInvoice.getTransport();
    	
    }
    
    @PrePersist
    public void prePersist() {
    	this.createdAt = new Date();
    	
    }

   
}


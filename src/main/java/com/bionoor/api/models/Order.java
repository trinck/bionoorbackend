package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order implements Serializable{

	public enum OrderStatus{
		
		PENDING, DELIVERED,PROCESSING, READY;
	}
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the order

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt; // date and time when the order was placed

    @OneToOne
    @JoinColumn(name = "discountDCP_id")
    private DiscountDCP discountDCP;
    
    @Column(nullable = false)
    private Double totalAmount; // total price of the order

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    
    
    @ManyToOne
    @JoinColumn(name = "paymentMethod_id")
    private PaymentMethod paymentMethod;
    
    @Column(nullable = false)
    private boolean fulfilled; // flag indicating whether the order has been fulfilled

    
    @OneToOne(mappedBy = "order")
    private Invoice invoice;
    
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // user who placed the order

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems; // list of items in the order

   
	

    // other properties and methods

}
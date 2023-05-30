package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer implements Serializable{
	

    /**
	 * 
	 */
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    
	  @ManyToMany(fetch = FetchType.LAZY)
	  
	  @JoinTable( name = "usedDiscountCode", joinColumns
	  = @JoinColumn(name="customer_id"), inverseJoinColumns
	  = @JoinColumn(name="discountCode_id") )
	  private List<DiscountCode> usedDiscountCodes= new ArrayList<>(); // discount codes used by customer
	 
	@OneToMany(mappedBy = "customer")
    private List<DiscountDCC> discountDCCs = new ArrayList<>();
	  
	  
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders; // list of orders associated with the user
    
    @OneToOne(mappedBy = "customer")
	private Address address;
    
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	 private List<Review> reviews = new ArrayList<>(); // list of reviews associated with the user
	 
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	 private List<Payment> payments = new ArrayList<>(); // payments realized by customers 
   
    private String email;
    private String password;
    private boolean confirmed;
    private boolean blocked;

    private boolean enabled;
    
    
    

    // constructors, getters and setters
}


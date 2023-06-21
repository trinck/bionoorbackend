package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bionoor.api.dto.InputCustomerDTO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "status", discriminatorType = DiscriminatorType.STRING)
public abstract class Customer extends GenericBionoorEntity implements Serializable{
	

    /**
	 * 
	 */
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(unique = true)
	protected String username;
    
	protected String firstName;
    
	protected String lastName;
	  @ManyToMany(fetch = FetchType.LAZY)
	  
	  @JoinTable( name = "usedDiscountCode", joinColumns
	  = @JoinColumn(name="customer_id"), inverseJoinColumns
	  = @JoinColumn(name="discountCode_id") )
	  protected List<DiscountCode> usedDiscountCodes= new ArrayList<>(); // discount codes used by customer
	 
	@OneToMany(mappedBy = "customer", orphanRemoval = true)
	protected List<DiscountDCC> discountDCCs = new ArrayList<>();
	  
	  
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    protected List<Order> orders = new ArrayList<>();; // list of orders associated with the user
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    protected Address address;
    
    
    @Column(name = "status", insertable = false, updatable = false)
    protected String discriminatorValue;
    
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    protected List<Review> reviews = new ArrayList<>(); // list of reviews associated with the user
	 
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    protected List<Payment> payments = new ArrayList<>(); // payments realized by customers 
   
    @Column(nullable = false, unique = true)
    protected String email;
    
    protected String password;
    
    protected String tel;
    protected boolean confirmed;
    

    protected boolean enabled;
    
    
    

    // constructors, getters and setters
    
    
    
    public Customer(InputCustomerDTO customerDTO) {
    	
    	 this.username = customerDTO.getUsername();
         this.firstName = customerDTO.getFirstName();
         this.lastName = customerDTO.getLastName();
         this.tel = customerDTO.getTel();
         this.email = customerDTO.getEmail();
         this.password = customerDTO.getPassword();
        
    }
}


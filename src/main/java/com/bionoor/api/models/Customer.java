package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
public class Customer implements Serializable{
	
	
		public enum Gender{
			   MALE,FEMALE
		   }
	 
		
	
	@Id
	 @GeneratedValue
	//@UuidGenerator
	 private Long id;
	 
	 @Column(nullable = false)
	private String fistname;
	 @Column(nullable = false)
	private String lastname;
	 
	 @Column(nullable = false, unique = true)
	 private String usename;
	 
	 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	 private List<Order> orders = new ArrayList<>(); // list of orders associated with the user
		
		/*
		 * @ManyToMany(fetch = FetchType.LAZY)
		 * 
		 * @JoinTable( name = "used_discount_customer", joinColumns
		 * = @JoinColumn(name="customer_id"), inverseJoinColumns
		 * = @JoinColumn(name="discount_id") ) private List<DiscountCode> usedDiscounts
		 * = new ArrayList<>(); // discount codes used by customer
		 */
	 
		
		/*
		 * @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy =
		 * "customer") private List<DiscountCustomer> discountCustomers = new
		 * ArrayList<>();// discount codes for specific customer
		 */
	 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	 private List<Review> reviews = new ArrayList<>(); // list of reviews associated with the user
	 
	 @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	 private List<Payment> payments = new ArrayList<>(); // payments realized by customers 
	 
	 
	 @Column(nullable = false)
	 private String email;
	 
	
	 @OneToOne(mappedBy = "customer")
	 private Address address;
	 
	 @Column(nullable = false)
	 private String password;
	 
	 private boolean confirmed;
	 
	 private boolean actif;
	
	 @Column(nullable = false)
	 private String tel;
	
	 @Enumerated(value = EnumType.STRING)
	 private Gender gender;
	 
	 @OneToMany(mappedBy = "customer")
	 private List<Comment>  comments = new  ArrayList<>();

 // constructors, getters and setters


}


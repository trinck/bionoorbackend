package com.bionoor.api.models;

import java.io.Serializable;
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
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders; // list of orders associated with the user
    
    
   
    private String email;
    private String password;
    private boolean confirmed;
    private boolean blocked;

    private boolean enabled;
    
    
    

    // constructors, getters and setters
}


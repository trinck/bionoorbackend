package com.bionoor.api.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Role implements Serializable{
 
	public enum EnumRole{
		
		ADMIN, USER;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EnumRole role;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

   
}

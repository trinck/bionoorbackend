package com.bionoor.api.models;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable{
 
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   @Column(nullable = false, unique = true)
    private String role;
   
   private String description; 
   
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private List<User> users;

   
}

package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends GenericBionoorEntity implements Serializable{

	
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; // unique identifier for the user

    @Column(nullable = false)
    private String firstName; // user's firstname

    @Column(nullable = false)
    private String lastName; // user's lastname

    @Column(nullable = false, unique = true)
    private String username; // username

    @ManyToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
    
    @Column(nullable = false, unique = true)
    private String email; // user's email address

    @Column(nullable = false)
    private String password; // user's password (stored as a hash)

    
    // other properties and methods
  
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable( joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")) 
    private List<Role> roles;

}

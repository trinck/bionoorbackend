package models;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Role {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

   
}

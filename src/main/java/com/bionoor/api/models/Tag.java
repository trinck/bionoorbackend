package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tags")
public class Tag implements Serializable  {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tag;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "tag_post", joinColumns = @JoinColumn(name="tag_id"),
      inverseJoinColumns = @JoinColumn(name = "post_id")
    		)
    private List<Post> posts = new ArrayList<>();
    

}
package com.bionoor.api.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Media implements Serializable{

    // Primary key for the media entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the media (e.g. "my-image.png", "promo-video.mp4")
    @Column(nullable = false, unique = true)
    private String name;

    // Mime type of the media (e.g. "image/png", "video/mp4")
    @Column(nullable = false)
    private String type;
    
	
	  @ManyToMany(mappedBy = "images", fetch = FetchType.LAZY) 
	  private List<Product> products;
	  
    
    @Column(nullable = false)
    private String url;
    
    private Long size;
    
    private String dimensions;
    
    
    // Default constructor required by JPA
 

   

   
}

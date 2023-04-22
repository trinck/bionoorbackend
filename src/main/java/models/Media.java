package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Media {

    // Primary key for the media entity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the media (e.g. "my-image.png", "promo-video.mp4")
    @Column(nullable = false)
    private String name;

    // Mime type of the media (e.g. "image/png", "video/mp4")
    @Column(nullable = false)
    private String type;
    
    private int size;
    
    private String dimensions;
    
    
    // Default constructor required by JPA
    public Media() {}

   

   
}

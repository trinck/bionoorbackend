package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @Column(nullable = false)
    private String title;
    
   
    @Column(nullable = false)
    private String content;
    
    
    @ManyToMany
    @JoinTable(
    		name = "post_media",
    		joinColumns = @JoinColumn(name="post_id"),
    		inverseJoinColumns = @JoinColumn(name="media_id")
    		)
    private List<Media> medias = new  ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToMany(mappedBy = "posts",fetch = FetchType.LAZY)
    private List<Tag> tags = new ArrayList<>();
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    // getters and setters
}


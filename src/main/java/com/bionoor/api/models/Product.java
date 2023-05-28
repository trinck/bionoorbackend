package com.bionoor.api.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.admin.AdminProduct.InputProductDto;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable{

   
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date modifiedAt;
	
    @Column(nullable = false)
    private String name; // product name

    
    @Column(nullable = false,unique = true)
    private String code; // product name
    
    private String steps; // product steps preparation
    
    private String ingredients; // product ingredients

    private String description; // product description

    @Column(nullable = false)
    private Double price; // product price
    
    @Column(nullable = false)
    private Long quantity; // product price
    
    @Column(nullable = false)
    private Boolean inPromotion;
    private Double promotion; // promotion
	

    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
      name = "product_media", 
      joinColumns = @JoinColumn(name = "product_id"), 
      inverseJoinColumns = @JoinColumn(name = "media_id"))
    private List<Media> images = new ArrayList<Media>(); // URL for product image

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category; // category that the product belongs to

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<Review>(); // list of reviews for the product
    
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Certificat> certificats = new ArrayList<Certificat>();// certificats
    
	/*
	 * @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST },
	 * mappedBy = "products") private List<DiscountCode> discountCodes = new
	 * ArrayList<>();//discount code applied to this products
	 */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productrange_id")
    private ProductRange productRange;

    
    public void setReview(Review review) {
    	this.reviews.add(review);
    }
    public void setCertificat(Certificat certificat) {
    	this.certificats.add(certificat);
    }
    public void setMedia(Media media) {
    	this.images.add(media);
    	
    }
    
    public Product(InputProductDto productDto ) {
    	this.price = productDto.getPrice();
    	this.code = productDto.getCode();
    	this.description = productDto.getDescription();
    	this.name = productDto.getName();
    	this.ingredients = productDto.getIngredients();
    	this.promotion = productDto.getPromotion();
    	this.quantity = productDto.getQuantity();
    	
    }
    

}


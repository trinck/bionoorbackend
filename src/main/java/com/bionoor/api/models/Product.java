package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.admin.AdminProduct.InputProductDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
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
public class Product extends Discountable implements Serializable{

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id; //
	 * unique identifier for the product
	 */
    @Column(nullable = false)
    private String name; // product name

    
    @Column(nullable = false)
    private String code; // product name
    
    private String steps; // product steps preparation
    
    private String ingredients; // product ingredients
    
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createAt; // creation date 
    
   
  
    @Column(length = 10000)
    private String description; // product description

    @Column(nullable = false)
    private Double price; // product price
    
    @Column(nullable = false)
    private Long quantity; // product price
    
    private Double promotion; // promotion
    
    private boolean isOnSale; // promotion

	/*
	 * @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	 * 
	 * @JoinTable( name = "discountCode_product", joinColumns = @JoinColumn(name =
	 * "product_id"), inverseJoinColumns = @JoinColumn(name = "discount_id"))
	 * private List<DiscountCode> discountCodes = new ArrayList<DiscountCode>();
	 */
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
      name = "product_media", 
      joinColumns = @JoinColumn(name = "product_id"), 
      inverseJoinColumns = @JoinColumn(name = "media_id"))
    private List<Media> images = new ArrayList<Media>(); // URL for product image

    
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // category that the product belongs to

 
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<Review>(); // list of reviews for the product
    
    
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Certificat> certificats = new ArrayList<Certificat>();
    
    
    @ManyToOne()
    @JoinColumn(name = "product_range_id")
    private ProductRange productRange;

    // other properties and methods
    
   
    
   
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
    	this.code = productDto.getCode().trim();
    	this.description = productDto.getDescription().trim();
    	this.name = productDto.getName().trim();
    	this.ingredients = productDto.getIngredients().trim();
    	this.promotion = productDto.getPromotion();
    	this.quantity = productDto.getQuantity();
    	
    }
    

}


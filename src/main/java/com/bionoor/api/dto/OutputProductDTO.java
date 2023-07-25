package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Certificat;
import com.bionoor.api.models.Media;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.ProductRange;
import com.bionoor.api.models.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputProductDTO {

	
	
	
    private String name; // product name

    
  
    private String code; // product name
    
    private String steps; // product steps preparation
    
    private String ingredients; // product ingredients
    

   
    private Date createAt; // creation date 
    
   private Long id;
  
    private String description; // product description

  
    private Double price; // product price
    
    private boolean isOnSale; // product price
    
  
    private Long quantity; // product price
    
    private Double promotion; // promotion
    
    private List<Media> images = new ArrayList<Media>(); // URL for product image

    
	/*
	 * @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "category_id") private Category category; // category that
	 * the product belongs to
	 */
 
	/*
	 * @OneToMany(mappedBy = "product", cascade = CascadeType.ALL) private
	 * List<Review> reviews = new ArrayList<Review>(); // list of reviews for the
	 * product
	 * 
	 * 
	 * @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY, cascade =
	 * CascadeType.PERSIST) private List<Certificat> certificats = new
	 * ArrayList<Certificat>();
	 * 
	 * 
	 * @ManyToOne()
	 * 
	 * @JoinColumn(name = "product_range_id") private ProductRange productRange;
	 */

    // other properties and methods
    
    public OutputProductDTO(Product product) {
    
    	this.name = product.getName();
        this.code = product.getCode();
        this.steps = product.getSteps();
        this.ingredients = product.getIngredients();
        this.id = product.getId();
        
        this.createAt = product.getCreateAt();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.promotion = product.getPromotion();
        
        this.images = product.getImages();
        this.isOnSale = product.isOnSale();
    	
    }
   
   
    
}

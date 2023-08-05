package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bionoor.api.models.Media;
import com.bionoor.api.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputProductBestSeller {

	
	private String name; // product name

	private String code; // product name

	private Date createdAt; // creation date

	private Long id;

	private Double price; // product price

	private boolean isOnSale; // product price

	private Double earned;
	
	private Integer quantitySold;

	private Long quantity; // product price

	private Double promotion; // promotion

	private List<Media> images = new ArrayList<Media>(); // URL for product image
  

    
    // other properties and methods
    
    public OutputProductBestSeller(Product product) {
    
    	this.name = product.getName();
        this.code = product.getCode();
       
      
        this.id = product.getId();
        
        this.createdAt = product.getCreateAt();
       
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.promotion = product.getPromotion();
        
        this.images = product.getImages();
        this.isOnSale = product.isOnSale();
        
    }
   
   
}

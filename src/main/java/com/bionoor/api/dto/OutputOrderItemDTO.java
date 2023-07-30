package com.bionoor.api.dto;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputOrderItemDTO {

	
	    private Long id; // unique identifier for the order item

	   
	    private int quantity; // quantity of the product in the order

	   
	    private OutputProductDTO product; // product that the order item refers to

	  
	    private Long order; // order that the order item belongs to
	    
	    private int quantityDelivered;
	    // other properties and methods
	 
	    
	    public OutputOrderItemDTO( OrderItem orderItem) {
	    	
	    	this.id = orderItem.getId();
	    	this.quantity = orderItem.getQuantity();
	    	this.product = new OutputProductDTO( orderItem.getProduct());
	    	this.order = orderItem.getOrder().getId();
	    	this.quantityDelivered = orderItem.getQuantityDelivered();
	    }
	    
}

package com.bionoor.api.models;

import java.io.Serializable;

import com.bionoor.api.utils.OrderItemListener;
import com.bionoor.api.web.RestOrder.InputOrderItemDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
@EntityListeners(OrderItemListener.class)
public class OrderItem implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the order item

    @Column(nullable = false)
    private int quantity; // quantity of the product in the order

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // product that the order item refers to

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // order that the order item belongs to
    
    private int quantityDelivered;
    // other properties and methods
 
    public OrderItem(InputOrderItemDTO dto) {
    	this.id = dto.getId();
    	this.quantity = dto.getQuantity();
    }
}

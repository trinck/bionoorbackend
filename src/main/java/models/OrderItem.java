package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the order item

    @Column(nullable = false)
    private int quantity; // quantity of the product in the order

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // product that the order item refers to

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // order that the order item belongs to

    // other properties and methods

}

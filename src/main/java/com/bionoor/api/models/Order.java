package com.bionoor.api.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // unique identifier for the order

    @Column(nullable = false)
    private LocalDateTime date; // date and time when the order was placed

    @Column(nullable = false)
    private Double total; // total price of the order

    @Column(nullable = false)
    private boolean fulfilled; // flag indicating whether the order has been fulfilled

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer; // user who placed the order

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems; // list of items in the order

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments; // list of payments associated with the order

	

    // other properties and methods

}
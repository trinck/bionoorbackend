package com.bionoor.api.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "reviews")
public class Review implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // unique identifier for the review

	@Column(nullable = false)
	private String comment; // text of the review

	@Column(nullable = false)
	private int rating; // rating for the product (1 to 5 stars)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product; // product that the review refers to
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer; // customer that had put review

	// other properties and methods
}

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
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // unique identifier for the review

	@Column(nullable = false)
	private String comment; // text of the review

	@Column(nullable = false)
	private int rating; // rating for the product (1 to 5 stars)

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product; // product that the review refers to

	// other properties and methods
}

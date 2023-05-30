package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@Entity
@Data
@NoArgsConstructor
@Table(name = "categories")
public class Category extends Discountable implements Serializable{

	/**
	 * 
	 */
	

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id; //
	 * unique identifier for the category
	 */
	@Column(nullable = false, unique = true)
	private String name; // name of the category
	
	@Column(nullable = false)
	private String image; 
	
	// The parent category of this category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
    
 // The child categories of this category
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.PERSIST)
    private List<Category> subCategories = new ArrayList<Category>();;
    
 // The child categories of this category
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
      name = "discountCode_category", 
      joinColumns = @JoinColumn(name = "category_id"), 
      inverseJoinColumns = @JoinColumn(name = "discount_id"))
    private List<DiscountCode> discountCodes = new ArrayList<DiscountCode>();


	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Product> products = new ArrayList<Product>(); // list of products in the category

	// other properties and methods
	
	
	
	public void setDiscountCode(DiscountCode  discountCode) {
    	this.discountCodes.add(discountCode);
    }
}
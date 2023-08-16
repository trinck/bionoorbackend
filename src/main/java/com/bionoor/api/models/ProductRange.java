package com.bionoor.api.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ValueGenerationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProductRange implements Serializable{
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
  
	@Column(nullable = false, unique = true)
   private String title;
	
	@OneToMany(mappedBy = "productRange")
	private List<Product> products = new ArrayList<Product>(); 

}

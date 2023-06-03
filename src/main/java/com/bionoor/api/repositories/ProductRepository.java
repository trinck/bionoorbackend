package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	
	List<Product> findByNameContains(String name);
	List<Product> findByName(String name);
}

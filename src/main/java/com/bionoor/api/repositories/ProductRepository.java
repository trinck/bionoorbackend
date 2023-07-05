package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bionoor.api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	
	List<Product> findByNameContains(String name);
	List<Product> findByName(String name);
	@Query("SELECT p FROM Product p WHERE p.name IN :names")
	List<Product> findByNames( @Param("names") List<String> names);

}

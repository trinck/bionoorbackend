package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bionoor.api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	
	public List<Product> findByNameContains(String name);
	
	public Page<Product> findByNameContains(String name , Pageable pageable);
	public Page<Product> findByCodeContains(String code , Pageable pageable);
	public List<Product> findByName(String name);
	public Product findByCode(String code);
	@Query("SELECT p FROM Product p WHERE p.name IN :names")
	public List<Product> findByNames( @Param("names") List<String> names);
	
	public	Page<Product> findById(Long id , Pageable pageable);
	 
	 
}

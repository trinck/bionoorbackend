package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.Order.OrderStatus;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	
	@Query("SELECT c FROM Category c WHERE c.name IN :names")
	List<Category> findByNames( @Param("names") List<String> names);
	
	public	Page<Category> findById(Long id , Pageable pageable);
 	public Page<Category> findByNameContains(String name , Pageable pageable);
 	
	
}

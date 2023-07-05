package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.Product;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	
	@Query("SELECT c FROM Category c WHERE c.name IN :names")
	List<Category> findByNames( @Param("names") List<String> names);
	
}

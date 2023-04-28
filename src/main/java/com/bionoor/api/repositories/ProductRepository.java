package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}

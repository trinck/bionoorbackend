package com.bionoor.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.PaymentMethod;
import com.bionoor.api.models.Product;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{

	
	
 public	Optional<PaymentMethod> findByName(String name);
}

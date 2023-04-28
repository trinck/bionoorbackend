package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.DiscountCode;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
  
}

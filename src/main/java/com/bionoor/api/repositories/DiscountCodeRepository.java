package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Category;
import com.bionoor.api.models.DiscountCode;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
	
  public List<DiscountCode> findByCode(String code);
}

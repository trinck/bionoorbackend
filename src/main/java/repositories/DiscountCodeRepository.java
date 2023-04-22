package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.DiscountCode;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
  
}

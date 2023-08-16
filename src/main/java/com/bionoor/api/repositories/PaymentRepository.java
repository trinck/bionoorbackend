package com.bionoor.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	
	public	Page<Payment> findById(Long id, Pageable pageable);
	 
	 public	Page<Payment> findByInvoiceOrderCustomerUsername(String username , Pageable pageable);
}

package com.bionoor.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

	
	
 public	Page<Invoice> findById(Long id, Pageable pageable);
}

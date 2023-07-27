package com.bionoor.api.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;

import jakarta.persistence.EntityNotFoundException;

public interface InvoiceServiceIn {

	
	
	
public Invoice add(Invoice invoice ) ;
	
		
	public Invoice add(InputOrderInvoiceDTO inputInvoice ) ;
	
	
	
	public Invoice modify(InputOrderInvoiceDTO inputInvoice ) ;
	
	
	public Invoice putDueDate(Date dueDate, Long id ) ;
	


	
	public Invoice delete(Long id);
	
	
	public Invoice modify(Invoice invoice);
	
	public List<Invoice> allInvoices( ) ;
	
	public Page<Invoice> pages( int page, int size, Long id , String sort) ;

	
	public Invoice getById(Long id) ;
}

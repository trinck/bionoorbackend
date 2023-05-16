package com.bionoor.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.InvoiceRepository;

@Service
public class InvoiceService implements ServiceInterface<Invoice>{
 
	InvoiceRepository invoiceRepository;
	
	public InvoiceService(InvoiceRepository invoiceRepository) {
		// TODO Auto-generated constructor stub
		this.invoiceRepository = invoiceRepository;
	}
	
	
	
	public Invoice add(Invoice invoice ) {
		
		return this.invoiceRepository.save(invoice);
	}

	
	public String delete(Long id) {
			
		
		String message = "";
		
	      Invoice invoice = this.invoiceRepository.getReferenceById(id);
			if(invoice == null) {
				message = "Product with id "+id+" doesn't exist";
			}else {
				this.invoiceRepository.deleteById(id);
				message = "Product "+id+" has been deleted";
			}
	      
	      return message;
	}
	
	public Invoice modify(Invoice invoice) {
				
			
		 return	this.invoiceRepository.save(invoice);
		      
			
	}



	@Override
	public Invoice getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}


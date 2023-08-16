package com.bionoor.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.InvoiceRepository;
import com.bionoor.api.repositories.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InvoiceService implements InvoiceServiceIn{
 
	
	@Autowired
	private	InvoiceRepository invoiceRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Invoice add(Invoice invoice ) {
		
		return this.invoiceRepository.save(invoice);
	}

	
		
	public Invoice add(InputOrderInvoiceDTO inputInvoice ) {
			
		Invoice invoice = new Invoice(inputInvoice);
		Order order = this.orderRepository.findById(inputInvoice.getOrder()).orElse(null);
		
		if(order == null) {
		throw new EntityNotFoundException("order with id "+inputInvoice.getOrder()+" doesn't exists");	
		}
		
		order.setInvoice(invoice);
		invoice.setOrder(order);
		/*rest of processing to do**************
		 * *****set create by**************
		 * ******etc..*************************/
		
		return this.invoiceRepository.save(invoice);
		}
	
	
	
	public Invoice modify(InputOrderInvoiceDTO inputInvoice ) {
		
		Order order = this.orderRepository.findById(inputInvoice.getOrder()).orElse(null);
		
		if(order == null) {
		throw new EntityNotFoundException("order with id "+inputInvoice.getOrder()+" doesn't exists");	
		}
		
		Invoice invoice = order.getInvoice();
		invoice.update(inputInvoice);
		
		/*rest of processing to do**************
		 * *****set create by**************
		 * ******etc..*************************/
		
		return this.invoiceRepository.save(invoice);
		}

	
	
	public Invoice putDueDate(Date dueDate, Long id ) {
		
			Invoice invoice = this.getById(id);
			invoice.setDueDate(dueDate);
			return this.invoiceRepository.save(invoice);
		}
	
	
	


	
	public Invoice delete(Long id) {
			
	      Invoice invoice = this.invoiceRepository.getReferenceById(id);
	      
	     
			if(invoice == null) {
				throw new EntityNotFoundException("Invoice with id = "+id+"already doesn't exists");
			}else {
				 Order order = invoice.getOrder();
				 order.setInvoice(null);
				 this.orderRepository.save(order);
				 
				// this.invoiceRepository.delete(invoice);
				 
			}
	      
	     return invoice;
	}
	
	
	public Invoice modify(Invoice invoice) {
				
			
		 return	this.invoiceRepository.save(invoice);
		      
			
	}

	
	public List<Invoice> allInvoices( ) {
		
		
		 return	this.invoiceRepository.findAll();
		     
	}
	
	
	public List<Invoice> invoicesNotPaid( ) {
		
		
		 return	this.invoiceRepository.findByDueToPayGreaterThan(0);
		     
	}
	
	
	public Page<Invoice> pages( int page, int size, Long id , String sort) {
		
		
		 String [] sorts = sort.split(":");
		Page<Invoice> pages;
		if(id != null) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.invoiceRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			}else {
				
				pages = this.invoiceRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.invoiceRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.invoiceRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			

		}
		
		
		
		 return	pages;
		     
	}

	
	
	
public Page<Invoice> pagesByUsername( int page, int size, String sort, String username) {
		
		
	   String [] sorts = sort.split(":");
		Page<Invoice> pages;
		if(username != null && !username.isEmpty()) {
			if(sorts[1].equalsIgnoreCase("ascending")) {
				
				
					pages = this.invoiceRepository.findByOrderCustomerUsername(username, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
				
			}else  {
				
				pages = this.invoiceRepository.findByOrderCustomerUsername(username, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			
			
		}else {
			
			
			if(sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.invoiceRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			}else {
				
				pages = this.invoiceRepository.findAll( PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
			
		}
		
		 return	pages;
		     
	}


	
	public Invoice getById(Long id) {
		Invoice invoice = this.invoiceRepository.findById(id).orElse(null);
		if(invoice!=null) {
			return invoice;
		}
		
		throw new EntityNotFoundException("Entity invoice with id = "+id+" did not found");
	}

}


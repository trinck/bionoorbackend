package com.bionoor.api.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.admin.AdminInvoice.InputInvoice;
import com.bionoor.api.models.Category;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.InvoiceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InvoiceService {
 
	
	@Autowired
	private	InvoiceRepository invoiceRepository;
	
	@Autowired
	private OrderService orderService;
	
	public Invoice add(Invoice invoice ) {
		
		return this.invoiceRepository.save(invoice);
	}

	
		
	public Invoice add(InputInvoice inputInvoice ) {
			
		Invoice invoice = new Invoice(inputInvoice);
		Order order = this.orderService.getById(inputInvoice.getOrderId());
		invoice.setOrder(order);
		invoice.setCreatedAt(new Date());
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
				 this.orderService.add(order);
				 
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


	
	public Invoice getById(Long id) {
		Invoice invoice = this.invoiceRepository.findById(id).orElse(null);
		if(invoice!=null) {
			return invoice;
		}
		
		throw new EntityNotFoundException("Entity invoice with id = "+id+" did not found");
	}

}


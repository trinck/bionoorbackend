package com.bionoor.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.repositories.OrderItemRepository;
import com.bionoor.api.utils.InvoiceProcessingIn;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderItemService implements OrderItemServiceIn{

	
	
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	ProductServiceIn productService;
	

	
	public OrderItem delete(Long id) {
		
		OrderItem orderItem = this.orderItemRepository.getReferenceById(id);
			if(orderItem == null) {
				throw new EntityNotFoundException("orderItem with id = "+id+"already doesn't exists");
			}else {
				this.orderItemRepository.deleteById(id);
			}
	      
	     return orderItem;
	}
	
	public OrderItem modify(OrderItem orderItem) {
				
		
		 return	this.orderItemRepository.save(orderItem);
		      
			
	}

	
	public OrderItem add(OrderItem orderItem) {
		
		 return	this.orderItemRepository.save(orderItem);
		      
	}


	
	public OrderItem getById(Long id) {
		OrderItem orderItem = this.orderItemRepository.findById(id).orElse(null);
		if(orderItem!=null) {
			return orderItem;
		}
		
		throw new EntityNotFoundException("Entity orderItem with id = "+id+" did not found");
	}
}

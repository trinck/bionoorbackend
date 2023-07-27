package com.bionoor.api.utils;

import org.springframework.stereotype.Service;

import com.bionoor.api.models.OrderItem;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class OrderItemListener {

	
	private InvoiceProcessingIn in;
	
	@PostPersist
	public void postPersist(OrderItem orderItem) {
			
	
		this.in = new InvoiceProcessingImp();
		
		this.in.TotalAmountOrder(orderItem.getOrder());
		
	}
	
	
	@PreUpdate
	public void preUpdate(OrderItem orderItem) {
			
		this.in = new InvoiceProcessingImp();
		this.in.TotalAmountOrder(orderItem.getOrder());
		
	}
	
	
	
	@PrePersist
	public void prePersist(OrderItem orderItem) {
			
		/*
		 * this.in = new InvoiceProcessingImp(); System.out.println("order items size" +
		 * orderItem.getOrder().getOrderItems().size());
		 * this.in.TotalAmountOrder(orderItem.getOrder(), false);
		 */
		
	}
	
	
	@PreRemove
	public void preRemove(OrderItem orderItem) {
			
		this.in = new InvoiceProcessingImp();
		this.in.TotalAmountOrder(orderItem.getOrder());
		
	}
}

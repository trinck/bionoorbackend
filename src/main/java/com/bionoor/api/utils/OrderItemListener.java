package com.bionoor.api.utils;

import org.springframework.stereotype.Service;

import com.bionoor.api.models.OrderItem;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
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
		this.in.TotalAmountOrder(orderItem.getOrder(), false);
		
	}
	
	
	@PreUpdate
	public void preUpdate(OrderItem orderItem) {
			
		this.in = new InvoiceProcessingImp();
		this.in.TotalAmountOrder(orderItem.getOrder(), false);
		
	}
	
	
	@PreRemove
	public void preRemove(OrderItem orderItem) {
			
		this.in = new InvoiceProcessingImp();
		this.in.TotalAmountOrder(orderItem.getOrder(), false);
		
	}
}

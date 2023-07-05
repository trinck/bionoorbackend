package com.bionoor.api.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;

import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class OrderListener {

	
	
	private InvoiceProcessingIn invoiceProcessingIn = new InvoiceProcessingImp();
	
	
	
	@PrePersist
	public void prePersist(Order order) {
		
			order.setCreatedAt(new Date());
			order.setStatus(OrderStatus.PENDING);
			order.setFulfilled(false);
			order.setTotalAmount(0d);
		
		this.invoiceProcessingIn.TotalAmountOrder(order);
		
	}
	
	
	
	@PreUpdate
	public void preUpdate(Order order) {
		
		
			order.setModifiedAt(new Date());
		//this.invoiceProcessingIn.TotalAmountOrder(order);
		
	}
	
}

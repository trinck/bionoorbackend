package com.bionoor.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Order;

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
		
		this.invoiceProcessingIn.TotalAmountOrder(order, false);
		
	}
	
	
	
	@PreUpdate
	public void preUpdate(Order order) {
		
		
		this.invoiceProcessingIn.TotalAmountOrder(order, false);
		
	}
	
}
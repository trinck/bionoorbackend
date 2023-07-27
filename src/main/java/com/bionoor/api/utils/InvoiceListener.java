package com.bionoor.api.utils;

import com.bionoor.api.models.Invoice;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class InvoiceListener {

	private InvoiceProcessingIn in;
	
	@PrePersist
	public void prePersist(Invoice invoice) {
		
		
		
		this.in = new InvoiceProcessingImp();
		
		this.in.TotalAmountOrder(invoice.getOrder());
		
	}
	
	
	@PreUpdate
	public void preUpdate(Invoice invoice) {
		
		
		
		this.in = new InvoiceProcessingImp();
		
		this.in.TotalAmountOrder(invoice.getOrder());
		
	}
}

package com.bionoor.api.utils;

import com.bionoor.api.models.Invoice;

import jakarta.persistence.PrePersist;

public class InvoiceListener {

	private InvoiceProcessingIn in;
	
	@PrePersist
	public void prePersist(Invoice invoice) {
		
		
		
		this.in = new InvoiceProcessingImp();
		
		this.in.TotalAmountOrder(invoice.getOrder(), false);
		
	}
}

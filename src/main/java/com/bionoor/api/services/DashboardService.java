package com.bionoor.api.services;

import org.springframework.beans.factory.annotation.Autowired;

public class DashboardService implements DashboardServiceIn {

	@Autowired
	private ProductServiceIn productServiceIn;
	@Autowired
	private CategoryServiceIn categoryServiceIn;
	@Autowired
	private PaymentServiceIn paymentServiceIn;
	@Autowired
	private InvoiceServiceIn invoiceServiceIn;
	
	
	
	
	
	
}

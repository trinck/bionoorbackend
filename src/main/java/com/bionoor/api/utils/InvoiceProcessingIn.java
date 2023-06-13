package com.bionoor.api.utils;

import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;

public interface InvoiceProcessingIn {

	
	public Order TotalAmountOrder(Order order, Boolean definetely);
}

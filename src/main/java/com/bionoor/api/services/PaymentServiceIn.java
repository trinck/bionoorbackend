package com.bionoor.api.services;

import java.util.List;

import com.bionoor.api.exceptions.EntityAlreadyExists;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.PaymentMethod;

import jakarta.persistence.EntityNotFoundException;

public interface PaymentServiceIn {

	
	
public Payment addPayment(Payment payment) ;
	
	
	
	
	public PaymentMethod addPaymentMethod(String name, String description) ;
	
	
	
	public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod);
		
	
	
	
	public PaymentMethod getPaymentMethodById(Long id) ;
	
	
	public List<PaymentMethod> getPaymentMethods() ;
	public List<Payment> getPayments();
	
}

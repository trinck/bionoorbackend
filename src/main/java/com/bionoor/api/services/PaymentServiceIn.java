package com.bionoor.api.services;

import java.util.List;

import org.springframework.data.domain.Page;

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
	
	public Page<Payment> pagesById(int page, int size, Long mc, String sort);
	public Page<Payment> pagesByUsername( int page, int size, String sort,String username) ;




	
	
}

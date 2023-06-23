package com.bionoor.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Payment;
import com.bionoor.api.repositories.PaymentRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
public class PaymentServiceImpl {

	
	@Autowired
	private PaymentRepository paymentRepository;
	private PaymentProcessing paymentProcessing;
	
	
	

	public Payment addPayment() {
		
		
		return null;
	}
	
}

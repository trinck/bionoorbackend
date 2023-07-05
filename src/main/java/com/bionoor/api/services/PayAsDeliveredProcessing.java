package com.bionoor.api.services;

import com.bionoor.api.models.Payment;

public class PayAsDeliveredProcessing implements PaymentProcessing{

	@Override
	public boolean process(Payment payment) {
		
		return true;
	}

}

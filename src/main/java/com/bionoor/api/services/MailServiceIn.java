package com.bionoor.api.services;

import com.bionoor.api.models.Customer;
import com.bionoor.api.models.Order;

public interface MailServiceIn {

	
 public void sendConfirmationOrder(Order order);
 public void sendVerificationEmail(Customer customer);
}

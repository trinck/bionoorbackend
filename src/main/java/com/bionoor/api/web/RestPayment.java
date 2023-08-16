package com.bionoor.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.PaymentAsDelivered;
import com.bionoor.api.services.InvoiceService;
import com.bionoor.api.services.InvoiceServiceIn;
import com.bionoor.api.services.PaymentServiceImpl;

@RestController
@RequestMapping("api/payments")
public class RestPayment {

	@Autowired
	private PaymentServiceImpl paymentServiceImpl;
	@Autowired
	private InvoiceServiceIn invoiceService;
	
	@PostMapping("/save/pay_as_delivered")
	public Payment addPayment(@RequestParam Long invoiceId, @RequestParam Double amount) {
		
		Invoice invoice = this.invoiceService.getById(invoiceId);
		
		PaymentAsDelivered asDelivered = new  PaymentAsDelivered();
		asDelivered.setAmount(amount);
		asDelivered.setInvoice(invoice);
			
	  return	this.paymentServiceImpl.addPayment(asDelivered);
		
		
	}
	
	
}

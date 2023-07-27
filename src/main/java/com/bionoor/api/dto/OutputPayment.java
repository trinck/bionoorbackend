package com.bionoor.api.dto;

import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputPayment {

	
	 
	    protected Long id; // unique identifier for the payment

	   
	    protected Double amount; // amount of the payment
	    
	    
	    protected Long invoice; // invoice that the payment is associated with

	    protected String discriminatorValue;
		
	    public OutputPayment(Payment payment) {
	    	
	    	this.amount = payment.getAmount();
	    	this.discriminatorValue = payment.getDiscriminatorValue();
	    	this.id = payment.getId();
	    	this.invoice =payment.getInvoice().getId();
	    	
	    }
}

package com.bionoor.api.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.models.Customer;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputInvoiceDTO {

	
	    private Long id;

	   
	    private Date createdAt;

	   
	    private Double totalAmount;
	    
	    private Boolean paid;
	    
	    private Double transport; 
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm;ss")
	    private Date dueDate;
	    
	    private int vat;
	    private Double remise;
	
	    private Long customer;

		/*
		 * @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch =
		 * FetchType.LAZY) private List<Payment> payments; // list of payments
		 * associated with the invoice
		 */
	   
	    private Long order;

	
	public OutputInvoiceDTO(Invoice invoice) {
		
		this.id = invoice.getId();
		this.createdAt = invoice.getCreatedAt();
		this.dueDate = invoice.getDueDate();
		this.order = invoice.getOrder().getId();
		this.vat = invoice.getVat();
		this.remise = invoice.getRemise();
		this.transport = invoice.getTransport();
		this.totalAmount = invoice.getTotalAmount();
		this.paid = invoice.getPaid();
		//this.customer = invoice.getCustomer().getId();
		
	}
	
}

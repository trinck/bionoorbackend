package com.bionoor.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.OutputInvoiceDTO;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.services.InvoiceServiceIn;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/invoices")
public class RestInvoice {

	
	@Autowired
	private InvoiceServiceIn invoiceServiceIn;
	
	
	@GetMapping("/")
	public List<OutputInvoiceDTO> invoices() {
		
		List<Invoice> list = this.invoiceServiceIn.allInvoices();
		
		List<OutputInvoiceDTO> dto = new ArrayList<>();
		
		list.forEach(inv ->{
			dto.add(new OutputInvoiceDTO(inv));
		});
		
		return dto;
		
		
	}
	
	@GetMapping("/{id}")
	public OutputInvoiceDTO invoice(@PathVariable Long id) {
			
			
		Invoice invoice = this.invoiceServiceIn.getById(id);
		
			return new OutputInvoiceDTO(invoice);
			
		}
	
	
	@GetMapping("/notPaid")
	public OutputInvoiceDTO invoiceNotPaid(@RequestParam Long id) {
			
				
			Invoice invoice = this.invoiceServiceIn.getById(id);
			
			if(invoice.getDueToPay()==0) {
				throw new  EntityNotFoundException("This invoice cann't paid. it might be already paid");
			}
		
			return new OutputInvoiceDTO(invoice);
			
		}
}

package com.bionoor.api.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Payment;
import com.bionoor.api.models.PaymentAsDelivered;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.services.InvoiceService;
import com.bionoor.api.services.InvoiceServiceIn;
import com.bionoor.api.services.PaymentServiceImpl;
import com.bionoor.api.services.PaymentServiceIn;

@Controller
public class AdminPayment {

	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private PaymentServiceIn paymentServiceImpl;
	@Autowired
	private InvoiceServiceIn invoiceService;
	
	
	
	@GetMapping("/payments")
	public String payments(Model model) {
		
		List<Payment> payments = this.paymentServiceImpl.getPayments();
		model.addAttribute("payments", payments);
		
		return "payments/paymentlist";
	}
	
	
	
	@GetMapping("/paymentForm")
	public String paymentForm(Model model, Long id, String saveMessage) {
		
		
		if(id!=null) {
			
			Invoice invoice = this.invoiceService.getById(id);
			if(invoice.getDueToPay()==0) {
				invoice = null;
				model.addAttribute("error", "The invoice with id = "+id+" has already paid");
			}
			
			model.addAttribute("invoice", invoice);
		}
		
		
		if(saveMessage!=null){
			String[] ms = saveMessage.split(":",2);
			model.addAttribute(ms[0], ms[1]);
		}
		
		model.addAttribute("invoices", this.invoiceService.invoicesNotPaid());
		
		
		return "payments/paymentform";
	}
	
	
	@PostMapping("/savePaymentAsDelivered")
	public String save(Model model, @RequestParam Long id, @RequestParam Double amount) {
		
		Invoice invoice = this.invoiceService.getById(id);
		
		PaymentAsDelivered asDelivered = new  PaymentAsDelivered();
		asDelivered.setAmount(amount);
		asDelivered.setInvoice(invoice);
		String saveMessage;
		
		try {
			this.paymentServiceImpl.addPayment(asDelivered);
			saveMessage =  "Success:"+amount+" Succeful added for invoice "+id;
		}catch(IllegalOperationException e) {
			
			saveMessage = "Error:"+e.getMessage();
		}
		
		
		return "redirect:/paymentForm?saveMessage="+saveMessage;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(value = "/paymentsPages")
	/* this id is for existing invoice*/
	public String page(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, String mc,@RequestParam(defaultValue = "id:ascending") String sort, @RequestParam(defaultValue = "id") String by ) 
	{
		
		
		Page<Payment> payments;
		
		if(by.equalsIgnoreCase("id")) {
			try {
				payments = this.paymentServiceImpl.pagesById(page, size,( mc==null || mc.isEmpty())? null:Long.valueOf(mc), sort);
			}catch(NumberFormatException e){
				
				model.addAttribute("error", "The input search must be a number");
				payments  = this.paymentServiceImpl.pagesById(page, size,null, sort);
			}
			
		}else {
			
			payments = this.paymentServiceImpl.pagesByUsername(page, size, sort, mc);
		}
		
		
	
		model.addAttribute("logo", logo);
	
		model.addAttribute("name", name);
		model.addAttribute("by", by);
		model.addAttribute("now", new Date());
		model.addAttribute("totalElements", payments.getTotalElements());
		model.addAttribute("pages", new int[payments.getTotalPages()]);
		model.addAttribute("mc", mc);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("sort", sort);
		model.addAttribute("totalPages", payments.getTotalPages());
		model.addAttribute("payments", payments.getContent());	
		return "payments/paymentlist";
	}
	
}

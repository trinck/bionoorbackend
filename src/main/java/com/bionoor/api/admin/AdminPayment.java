package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bionoor.api.models.Payment;
import com.bionoor.api.services.PaymentServiceImpl;

@Controller
public class AdminPayment {

	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private PaymentServiceImpl paymentServiceImpl;
	
	
	@GetMapping("/payments")
	public String payments(Model model) {
		
		List<Payment> payments = this.paymentServiceImpl.getPayments();
		model.addAttribute("payments", payments);
		
		return "payments/paymentlist";
	}
}

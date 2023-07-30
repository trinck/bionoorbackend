package com.bionoor.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.Payment;
import com.bionoor.api.services.CategoryServiceIn;
import com.bionoor.api.services.CustomerService;
import com.bionoor.api.services.CustomerServiceIn;
import com.bionoor.api.services.InvoiceServiceIn;
import com.bionoor.api.services.OrderServiceIn;
import com.bionoor.api.services.PaymentServiceIn;
import com.bionoor.api.services.ProductServiceIn;

@Controller
public class AdminDashboard {

	
	@Autowired
	private ProductServiceIn productServiceIn;
	@Autowired
	private CategoryServiceIn categoryServiceIn;
	@Autowired
	private PaymentServiceIn paymentServiceIn;
	@Autowired
	private InvoiceServiceIn invoiceServiceIn;
	@Autowired
	private OrderServiceIn orderServiceIn;
	@Autowired
	private CustomerServiceIn customerServiceIn;
	
	@GetMapping("dashboard")
	public String dashboard(Model model, Authentication authentication) {
		
		
		
		List<Payment> payments = this.paymentServiceIn.getPayments();
		
		Double earned =   payments.stream().map(payment -> payment.getAmount()).reduce((t, u) -> t+u ).orElse(0d);
		int toDeliver = this.orderServiceIn.findByStatus(OrderStatus.READY).size();
		model.addAttribute("toDeliver", toDeliver);
		model.addAttribute("authentication", authentication);
		model.addAttribute("earned", earned);
		
		model.addAttribute("customers", this.customerServiceIn.getCustomers().size());
		return "dashboard/dashboardviews";
	}
}

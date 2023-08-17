package com.bionoor.api.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.services.InvoiceService;
import com.bionoor.api.services.InvoiceServiceIn;
import com.bionoor.api.services.OrderService;
import com.bionoor.api.services.OrderServiceIn;
import com.bionoor.api.utils.InvoiceProcessingIn;

@Controller
public class AdminOrder {

	
	
	
	@Autowired
	private OrderServiceIn orderService;
	
	@Autowired
	private InvoiceServiceIn invoiceService;
	
	

	@GetMapping(value = "/order")
	public String order(Model model,@RequestParam(name = "id") int id) {
		
		
		model.addAttribute("id",id);
		
		return "orders/orderview.html";
	}
	
	
	
	@GetMapping(value = "/orders")
	public String orders(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, String mc,@RequestParam(defaultValue = "id:ascending") String sort, @RequestParam(defaultValue = "id") String by) {
		
		Map<String, String> mapStatusBG = new HashMap<>();
		mapStatusBG.put(OrderStatus.PENDING.name(), "text-bg-warning");
		mapStatusBG.put(OrderStatus.PROCESSING.name(), "text-bg-secondary");
		mapStatusBG.put(OrderStatus.READY.name(), "text-bg-primary");
		mapStatusBG.put(OrderStatus.DELIVERED.name(), "text-bg-success");
		mapStatusBG.put(OrderStatus.RETURNED.name(), "text-bg-danger");
		mapStatusBG.put(OrderStatus.CANCELED.name(), "text-bg-info");
		
		
		model.addAttribute("mapStatusBG", mapStatusBG);
		
		Page<Order> orders;
		
		if(by.equalsIgnoreCase("id")) {
			try {
				orders = this.orderService.findById(page, size,( mc==null || mc.isEmpty())? null:Long.valueOf(mc), sort);
			}catch(NumberFormatException e){
				
				model.addAttribute("error", "The input search must be a number");
				orders  = this.orderService.findById(page, size,null, sort);
			}
			
		}else if(by.equalsIgnoreCase("customer")) {
			
			orders = this.orderService.findByCustomerUsername(page, size, sort, mc);
		}else {
			
			try {
				orders = this.orderService.findAllByStatus(page, size, sort, mc);
			}catch(IllegalArgumentException e) {
				
				orders =  Page.empty();
			}
		}
		
		
	
		model.addAttribute("by", by);
		model.addAttribute("totalElements", orders.getTotalElements());
		model.addAttribute("pages", new int[orders.getTotalPages()]);
		model.addAttribute("mc", mc);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		model.addAttribute("sort", sort);
		model.addAttribute("totalPages", orders.getTotalPages());
		model.addAttribute("orders", orders.getContent());	
		
		return "orders/orders.html";
	}
	
	
	@GetMapping(value = "/orederView")
	/* this id is for order want to view or add invoice*/
	public String orederView(Model model, @RequestParam(name = "id") Long id) 
	{
		Order order = this.orderService.getById(id);
		
		model.addAttribute("order", order);
		model.addAttribute("invoice", order.getInvoice());
		
		
		return "invoices/invoiceview.html";
	}
	
}

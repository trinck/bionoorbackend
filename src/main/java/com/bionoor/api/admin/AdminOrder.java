package com.bionoor.api.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.services.InvoiceService;
import com.bionoor.api.services.OrderService;

@Controller

public class AdminOrder {

	
	@Value("${app.name}")
	private String name;
	@Value("${app.logo}")
	 private String logo;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InvoiceService invoiceService;

	@GetMapping(value = "/order")
	public String order(Model model,@RequestParam(name = "id") int id) {
		
		model.addAttribute("name", name);
		model.addAttribute("id",id);
		
		return "orders/orderview.html";
	}
	
	@GetMapping(value = "/orders")
	public String orders(Model model) {
		
		Map<String, String> mapStatusBG = new HashMap<>();
		mapStatusBG.put(OrderStatus.PENDING.name(), "text-bg-danger");
		mapStatusBG.put(OrderStatus.PROCESSING.name(), "text-bg-secondary");
		mapStatusBG.put(OrderStatus.READY.name(), "text-bg-primary");
		mapStatusBG.put(OrderStatus.DELIVERED.name(), "text-bg-success");
		List<Order>orders = this.orderService.allOrders();
		
		model.addAttribute("mapStatusBG", mapStatusBG);
		model.addAttribute("orders", orders);
		model.addAttribute("name", name);
		return "orders/orders.html";
	}
	
	
	@RequestMapping(value = "/orederView", method = RequestMethod.GET)
	/* this id is for order want to view or add invoice*/
	public String orederView(Model model, @RequestParam(name = "id") Long id) 
	{
		Order order = this.orderService.getById(id);
		model.addAttribute("order", order);
		model.addAttribute("invoice", order.getInvoice());
		model.addAttribute("logo", logo);
		model.addAttribute("name", name);
		
		return "invoices/invoiceview.html";
	}
	
}

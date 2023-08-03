package com.bionoor.api.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.OutputOrderDTO;
import com.bionoor.api.dto.OutputProductDTO;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.CategoryServiceIn;
import com.bionoor.api.services.CustomerServiceIn;
import com.bionoor.api.services.InvoiceServiceIn;
import com.bionoor.api.services.OrderServiceIn;
import com.bionoor.api.services.PaymentServiceIn;
import com.bionoor.api.services.ProductServiceIn;

@RestController
@RequestMapping("api/dashboard")
public class RestDashboard {

	
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
	
	
	
	@GetMapping("/productsOfMonth")
	public   Map<Integer,OutputProductDTO>  productsOfMonth(@RequestParam(defaultValue = "true") Boolean fulfilled, Integer month){
		
		
		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			 int year = calendar.get(Calendar.YEAR);
		
		
			 month = month == null? calendar.get(Calendar.MONTH): month;
			 
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, year);
		Map<String, List<Order>> sortedByMonth = sortByMonthOrder(list);
		
		 List<Order> currentMonthOrders = sortedByMonth.get(""+month);
		 Map<Product,Integer> products = new HashMap();
		 for(Order order: currentMonthOrders) {
			 
			 for(OrderItem item: order.getOrderItems()) {
				 
				 if(products.get(item.getProduct())== null) {
					 products.put(item.getProduct(), item.getQuantity());
					
				 }else {
					
					 products.put(item.getProduct(), products.get(item.getProduct())+item.getQuantity());
					
				 }
				 
				 
				 
			 }
			 
		 }
		 
		 
		 
		 Map<Integer,OutputProductDTO> body = new HashMap();
		 products.forEach((p,i)->{
			 body.put( i, new OutputProductDTO(p));
			
		 });
		 
		return body ;
	}
	
	
	
	
	@GetMapping("/sales")
	public Map<String,  List<OutputOrderDTO>> sales(@RequestParam(defaultValue = "true") Boolean fulfilled, Integer year){
		
		
		if(year ==null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			year = calendar.get(Calendar.YEAR);
		}
		
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, year);
		List<OutputOrderDTO> orderDTOs = toOutputDTO(list);
		Map<String, List<OutputOrderDTO>> sortedByMonth = sortByMonth(orderDTOs);
		return sortedByMonth;
	}
	
	
	
	@GetMapping("/ordersByStatus")
	public Map<OrderStatus, List<OutputOrderDTO>> ordersByStatus(){
		
		List<OutputOrderDTO> ready = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.READY));
		List<OutputOrderDTO> pending = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.PENDING));
		List<OutputOrderDTO> processing = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.PROCESSING));
		List<OutputOrderDTO> returned = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.RETURNED));
		List<OutputOrderDTO> delivered = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.DELIVERED));
		
		Map<OrderStatus, List<OutputOrderDTO> > body = new HashMap<>();
		
		body.put(OrderStatus.READY, ready);
		body.put(OrderStatus.PENDING, pending);
		body.put(OrderStatus.PROCESSING, processing);
		body.put(OrderStatus.RETURNED, returned);
		body.put(OrderStatus.DELIVERED, delivered);
		
		return body;
	} 
	
	
	
	@GetMapping("/fulfilled")
	public Map<String, Map<String, List<OutputOrderDTO>>> fulfilled(@RequestParam boolean fulfilled, @RequestParam int annee) {
		
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, annee);
		List<OutputOrderDTO> orderDTOs = toOutputDTO(list);
		Map<String, List<OutputOrderDTO>> groupedByDate = sortByMonth(orderDTOs);
		
		
		
		
		List<Order> list2 = this.orderServiceIn.getAllByYear(annee);
		List<OutputOrderDTO> orderDTO2 = toOutputDTO(list2);
		
		Map<String, List<OutputOrderDTO>> orderByYear = sortByMonth(orderDTO2);
		
		
		Map<String, Map<String, List<OutputOrderDTO>>> body  = new HashMap<>();
		
		body.put("fulfilled", groupedByDate);
		body.put("ready", orderByYear);
		
	   return	body;
	}
	
	
	public Map<String, List<OutputOrderDTO>> sortByMonth(List<OutputOrderDTO> DTOs){
		
		Map<String, List<OutputOrderDTO>> groupedByDate = new HashMap<>();
			
			for(OutputOrderDTO order: DTOs) {
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(order.getCreatedAt());
					int month = calendar.get(Calendar.MONTH);
					String stringBuilder = ""+month;
					if(groupedByDate.containsKey(stringBuilder)) {
						
						groupedByDate.get(stringBuilder).add(order);
					}else{
						List<OutputOrderDTO> newlist = new ArrayList<>();
						newlist.add(order);
						groupedByDate.put(stringBuilder, newlist);
					}
				}
		return groupedByDate;
	}
	
	
	
	
public Map<String, List<Order>> sortByMonthOrder(List<Order> orders){
		
		Map<String, List<Order>> groupedByDate = new HashMap<>();
			
			for(Order order2: orders) {
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(order2.getCreatedAt());
					int month = calendar.get(Calendar.MONTH);
					String stringBuilder = ""+month;
					if(groupedByDate.containsKey(stringBuilder)) {
						
						groupedByDate.get(stringBuilder).add(order2);
					}else{
						List<Order> newlist = new ArrayList<>();
						newlist.add(order2);
						groupedByDate.put(stringBuilder, newlist);
					}
				}
		return groupedByDate;
	}
	
			public List<OutputOrderDTO> toOutputDTO(List<Order> list){
						
				List<OutputOrderDTO> orderDTO = new ArrayList<>();
						
				list.forEach(o ->{
							
					orderDTO.add(new OutputOrderDTO(o));
				});
				
				return orderDTO;
			}
}

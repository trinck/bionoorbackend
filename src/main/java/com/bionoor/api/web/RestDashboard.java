package com.bionoor.api.web;

import java.util.ArrayList;
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
import com.bionoor.api.models.Order;
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
	
			public List<OutputOrderDTO> toOutputDTO(List<Order> list){
						
				List<OutputOrderDTO> orderDTO = new ArrayList<>();
						
				list.forEach(o ->{
							
					orderDTO.add(new OutputOrderDTO(o));
				});
				
				return orderDTO;
			}
}

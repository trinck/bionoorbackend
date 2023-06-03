package com.bionoor.api.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.services.OrderService;

import io.micrometer.common.lang.NonNull;


import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class RestOrder {

	@Autowired
	private OrderService orderService;
	
	
	
	@PostMapping(value = "/api/orders/save")
	public Order addOrder(@RequestBody InputOrderDTO inputOrderDTO) {
		
	   return	this.orderService.add(inputOrderDTO);
	}
	
	
	
	@PostMapping(value = "/api/orders/put/fulfilled")
	public ResponseEntity<String> toogleFulfilled(@RequestParam Boolean fulfilled, @RequestParam Long id) {
		
		
		this.orderService.toogleFilfilled(fulfilled, id);
	   return	new ResponseEntity<>("Order is fulfilled", HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/api/orders/orderItems/delete")
	public ResponseEntity<OutputOrderItemDTO> deleteOrderItem(@RequestParam Long orderItemId, @RequestParam Long id) {
		
		
		 OutputOrderItemDTO outputOrderItemDTO = new OutputOrderItemDTO(this.orderService.deleteOrderItem(orderItemId, id));
	   return	new ResponseEntity<>(outputOrderItemDTO, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/api/orders/orderItems/save")
	public ResponseEntity<OutputOrderItemDTO> addOrderItem(@ModelAttribute InputOrderItemDTO inputOrderItemDTO) {
		
		
		OutputOrderItemDTO OutputOrderItemDTO = new OutputOrderItemDTO(this.orderService.addOrderItem(inputOrderItemDTO));
		
	   return	new ResponseEntity<>(OutputOrderItemDTO, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "/api/orders/orderItems/put/quantity")
	public ResponseEntity<String> putOrderItemQuantity(@RequestParam int quantity, @RequestParam Long id) {
		
		
		this.orderService.putOrderItemQuantity(id, quantity);
		
	   return	new ResponseEntity<>("item quantity udapted", HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/api/orders/put/status")
	public ResponseEntity<String> putStatus(@RequestParam String status, @RequestParam long id) {
		
	   return new ResponseEntity<String>(this.orderService.putStatus(status, id).getStatus().name(), HttpStatus.OK)	;
	}
	
	
	
	@Data
	@NoArgsConstructor
	public class InputOrderDTO{
		
		private Long id;
	    private Long discountCode;
	    
	    private Double totalAmount; // total price of the order

	    @NonNull
	    private int paymentMethod;
	    
	    private List<InputOrderItemDTO> orderItems; // list of items in the order

	   
	}
	
	@Data
	@NoArgsConstructor
	public class InputOrderItemDTO{
		 
			private Long order;
			private Long id;
		    private int quantity; // quantity of the product in the order
		    private Long product; // product that the order item refers to
		    
	}
	
	
	@Data
	@NoArgsConstructor
	public class OutputOrderItemDTO{
		 
			private Long id;
		    private int quantity; // quantity of the product in the order
		    private Long product; // product that the order item refers to
		    
		    public OutputOrderItemDTO(OrderItem orderItem) {
		    	this.id = orderItem.getId();
		    	this.quantity = orderItem.getQuantity();
		    	this.product = orderItem.getProduct().getId();
		    }
	}
	
	
}









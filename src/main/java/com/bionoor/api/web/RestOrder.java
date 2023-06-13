package com.bionoor.api.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.dto.OutputDiscountCodeDTO;
import com.bionoor.api.dto.OutputOrderDTO;
import com.bionoor.api.dto.OutputOrderItemDTO;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.services.OrderService;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
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
	public OutputOrderDTO deleteOrderItem(@RequestParam Long orderItemId, @RequestParam Long id) {
		
		
		 OutputOrderDTO orderItemDTO = new OutputOrderDTO(this.orderService.deleteOrderItem(orderItemId, id));
	   return	orderItemDTO;
	}
	
	
	
	@GetMapping(value = "/api/orders/discountCode/delete")
	public OutputOrderDTO deleteDiscountCode( @RequestParam Long id) {
		
		return new OutputOrderDTO( this.orderService.deleteDiscountCode(id));
	   	
	}
	
	
	@GetMapping(value = "/api/orders/discountCode/get")
	public OutputDiscountCodeDTO getDiscountCode( @RequestParam Long id) {
		
		return new OutputDiscountCodeDTO(this.orderService.getDiscountCode(id));
	   	
	}
	
	
	@PostMapping(value = "/api/orders/discountCode/add")
	public OutputOrderDTO addDiscountCode(@RequestParam String code, @RequestParam Long id) {
		
	   return	 new OutputOrderDTO(this.orderService.addDiscountCode(code, id));
	}
	
	
	
	@PostMapping(value = "/api/orders/orderItems/save")
	public OutputOrderDTO addOrderItem(@ModelAttribute InputOrderItemDTO inputOrderItemDTO) {
		
		
		OutputOrderDTO outputOrderDTO = new OutputOrderDTO(this.orderService.addOrderItem(inputOrderItemDTO));
		
	   return	outputOrderDTO;
	}
	
	
	
	@PostMapping(value = "/api/orders/invoice/save")
	public OutputOrderDTO addOrderInvoice(@ModelAttribute @Valid InputOrderInvoiceDTO inputOrderInvoiceDTO) {
		
		
		 Order order =  this.orderService.addOrderInvoice(inputOrderInvoiceDTO);
		 OutputOrderDTO outputOrderDTO = new OutputOrderDTO(order);
		
	   return outputOrderDTO;
	}
	
	
	
	
	@GetMapping(value = "/api/orders/index")
	public OutputOrderDTO findOrderById(@RequestParam Long id) {
		
		OutputOrderDTO orderDTO = new OutputOrderDTO( this.orderService.getById(id));
		
	   return	orderDTO;
	}
	
	
	
	
	
	@PostMapping(value = "/api/orders/orderItems/put/quantity")
	public OutputOrderDTO putOrderItemQuantity(@RequestParam int quantity, @RequestParam Long id,  @RequestParam Long orderItemId ) {
		
		
			Order order = this.orderService.putOrderItemQuantity(id, quantity, orderItemId);
		
	   return	new OutputOrderDTO(order);
	}
	
	
	@PostMapping(value = "/api/orders/put/status")
	public OutputOrderDTO putStatus(@RequestParam String status, @RequestParam long id) {
		
	   return new OutputOrderDTO(this.orderService.putStatus(status, id))	;
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
		    private String productName; // product that the order item refers to
		    
	}
	
	
	
	
	
	
	
}








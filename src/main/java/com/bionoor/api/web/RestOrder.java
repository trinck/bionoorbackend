package com.bionoor.api.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.InputOrderDTO;
import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.dto.InputOrderItemDTO;
import com.bionoor.api.dto.OutputDiscountCodeDTO;
import com.bionoor.api.dto.OutputOrderDTO;
import com.bionoor.api.dto.OutputOrderItemDTO;
import com.bionoor.api.dto.OutputProductDTO;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
import com.bionoor.api.services.OrderService;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("api/orders")
public class RestOrder {

	@Autowired
	private OrderService orderService;
	
	
	
	@PostMapping(value = "/save")
	public OutputOrderDTO addOrder(@RequestBody @Valid InputOrderDTO inputOrderDTO) {
		
		Order order = this.orderService.add(inputOrderDTO);
		
	   return	 new OutputOrderDTO(order);
	}
	
	
	
	@GetMapping
	public List<OutputOrderDTO>  allOrders() {
		
		List<Order> list = this.orderService.allOrders();
		List<OutputOrderDTO> orderDTOs = new ArrayList<>();
		
		list.forEach(o ->{
			
			orderDTOs.add(new OutputOrderDTO(o));
		});
		
	   return	orderDTOs;
	}
	
	
	
	@PostMapping(value = "/put/fulfilled")
	public ResponseEntity<String> toogleFulfilled(@RequestParam Boolean fulfilled, @RequestParam Long id) {
		
		
		this.orderService.toogleFilfilled(fulfilled, id);
	   return	new ResponseEntity<>("Order is fulfilled", HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/orderItems/delete")
	public OutputOrderDTO deleteOrderItem(@RequestParam Long orderItemId, @RequestParam Long id) {
		
		
		 OutputOrderDTO orderItemDTO = new OutputOrderDTO(this.orderService.deleteOrderItem(orderItemId, id));
	   return	orderItemDTO;
	}
	
	
	
	@GetMapping(value = "/discountCode/delete")
	public OutputOrderDTO deleteDiscountCode( @RequestParam Long id) {
		
		return new OutputOrderDTO( this.orderService.deleteDiscountCode(id));
	   	
	}
	
	
	@GetMapping(value = "/discountCode/get")
	public OutputDiscountCodeDTO getDiscountCode( @RequestParam Long id) {
		
		return new OutputDiscountCodeDTO(this.orderService.getDiscountCode(id));
	   	
	}
	
	
	@PostMapping(value = "/discountCode/add")
	public OutputOrderDTO addDiscountCode(@RequestParam String code, @RequestParam Long id) {
		
	   return	 new OutputOrderDTO(this.orderService.addDiscountCode(code, id));
	}
	
	
	
	@PostMapping(value = "/orderItems/save")
	public OutputOrderDTO addOrderItem(@ModelAttribute InputOrderItemDTO inputOrderItemDTO) {
		
		
		OutputOrderDTO outputOrderDTO = new OutputOrderDTO(this.orderService.addOrderItem(inputOrderItemDTO));
		
	   return	outputOrderDTO;
	}
	
	
	
	@PostMapping(value = "/invoice/save")
	public OutputOrderDTO addOrderInvoice(@ModelAttribute @Valid InputOrderInvoiceDTO inputOrderInvoiceDTO) {
		
		
		 Order order =  this.orderService.addOrderInvoice(inputOrderInvoiceDTO);
		 OutputOrderDTO outputOrderDTO = new OutputOrderDTO(order);
		
	   return outputOrderDTO;
	}
	
	
	
	
	@GetMapping(value = "/{id}")
	
	public OutputOrderDTO findOrderById(@PathVariable Long id) {
		
		OutputOrderDTO orderDTO = new OutputOrderDTO( this.orderService.getById(id));
		
	   return	orderDTO;
	}
	
	
	
	
	
	@PostMapping(value = "/orderItems/put/quantity")
	public OutputOrderDTO putOrderItemQuantity(@RequestParam int quantity, @RequestParam Long id,  @RequestParam Long orderItemId ) {
		
		
			Order order = this.orderService.putOrderItemQuantity(id, quantity, orderItemId);
		
	   return	new OutputOrderDTO(order);
	}
	
	
	@PostMapping(value = "/put/status")
	public OutputOrderDTO putStatus(@RequestParam String status, @RequestParam long id) {
		
	   return new OutputOrderDTO(this.orderService.putStatus(status, id))	;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}









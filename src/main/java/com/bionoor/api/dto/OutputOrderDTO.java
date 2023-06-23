package com.bionoor.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.PayAsDelivered;
import com.bionoor.api.models.PaymentMethod;
import com.bionoor.api.utils.Utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputOrderDTO {

	
	
    private Long id; // unique identifier for the order

   
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt; // date and time when the order was placed

   
    private OutputDiscountCodeDTO discountCode;
    
    
  //  private User modifiedBy; //user who has modified this order
    
  
    private Double totalAmount; // total price of the order

  
    private OrderStatus status;
    
   
    private String adrress;
    
    
    private PaymentMethod paymentMethod;
    
  
    private boolean fulfilled; // flag indicating whether the order has been fulfilled

    
    private OutputInvoiceDTO invoice;
    
    
    private UUID customer; // user who placed the order

    private List<OutputOrderItemDTO> orderItems = new ArrayList<>(); // list of items in the order

	
	public OutputOrderDTO(Order order)  {
		
		this.id = order.getId();
		this.createdAt = order.getCreatedAt();
		this.totalAmount = order.getTotalAmount();
		this.adrress = order.getAdrress();
		this.customer  = order.getCustomer().getId();
		this.fulfilled = order.isFulfilled();
		this.invoice =  order.getInvoice()!= null? new OutputInvoiceDTO(order.getInvoice()):null;
		
		this.paymentMethod =  order.getPaymentMethod();
		
		this.discountCode =   order.getDiscountCode()!= null? new OutputDiscountCodeDTO(order.getDiscountCode()):null;
		this.status = order.getStatus();
		
		
		order.getOrderItems().forEach(item ->{
			this.orderItems.add(new OutputOrderItemDTO(item));
		});
		
	}
    
	
}

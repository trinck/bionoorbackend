package com.bionoor.api.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.exceptions.DiscountCodeException;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.PayAsDelivered;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.OrderItemRepository;
import com.bionoor.api.repositories.OrderRepository;
import com.bionoor.api.utils.InvoiceProcessingIn;
import com.bionoor.api.web.RestOrder.InputOrderDTO;
import com.bionoor.api.web.RestOrder.InputOrderItemDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DiscountCodeService discountCodeService;
	
	@Autowired
	private InvoiceProcessingIn invoiceProcessingIn;
	
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	

	
	public Order add(Order toSave) {
		
		
		//return this.orderRepository.save(this.invoiceProcessingIn.TotalAmountOrder(toSave, false));
		
		return this.orderRepository.save(toSave);
	}
	
	
	public List<Order> allOrders() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAll();
	}
	
	
	public Order putStatus(String status, Long id) {
		
		Order order = this.getById(id);
		
		switch(status) {
			case "RETURNED": order.setStatus(OrderStatus.RETURNED); break;
			case  "PENDING":  order.setStatus(OrderStatus.PENDING); break;
			case "READY":order.setStatus(OrderStatus.READY); break;
			case "DELIVERED": order.setStatus(OrderStatus.DELIVERED); break;
			case "PROCESSING": order.setStatus(OrderStatus.PROCESSING); break;
			default:  break;
		}
		return this.add(order);
	}
	
	
	
		
	public Order toogleFilfilled(Boolean fulfilled , Long id) {
			
			Order order = this.getById(id);
			order.setFulfilled(fulfilled);
			return this.add(order);
		}
	
	/*************put discountcode*********************************************
	***************************************************************************/
	public Order addDiscountCode(String  code, Long id) {
		
		Order order = this.getById(id);
		DiscountCode discountCode = this.discountCodeService.getByCode(code);
		
		if(discountCode.getActif()) {
			
			if(!discountCode.getEndDate().after(new Date())) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				
				throw new DiscountCodeException("DiscountCode :"+code+" is ended in "+dateFormat.format(discountCode.getEndDate()));
			}
			
		}else {
			throw new DiscountCodeException("DiscountCode :"+code+" is deactivated");
		}
		
		order.setDiscountCode(discountCode);
		 
		 
		 return this.add(order);
	}
	
	
	public Order deleteDiscountCode(Long id) {
			
			Order order = this.getById(id);
			
			DiscountCode code = order.getDiscountCode();
			 order.setDiscountCode(null);
			 
			 return this.add(order);
		}
	
	
	
	public DiscountCode getDiscountCode(Long id) {
		
		Order order = this.getById(id);
		
		DiscountCode code = order.getDiscountCode();
		
		if(code == null) throw new EntityNotFoundException("this order have not discount code ");
		 return code;
	}
	
	
	/*************put orderitem*********************************************
	***************************************************************************/
	public Order putOrderItem(InputOrderItemDTO orderItemDTO  , Long id) {
		
		Order order = this.getById(id);
		
		
		for(OrderItem item : order.getOrderItems()) {
			if(item.getId() == orderItemDTO.getId()) {
				
				Product product = this.productService.findByName(orderItemDTO.getProductName()); 
				 item.setQuantity(orderItemDTO.getQuantity());
				 item.setProduct(product); return this.add(order);
					
			}
		}
		
		throw new EntityNotFoundException("Order or OrderItem entity didn't found");
	}
	
	/*************put orderitem************************************************
	***************************************************************************/
	public Order addOrderItem(InputOrderItemDTO orderItemDTO) {
		
		Order order = this.getById(orderItemDTO.getOrder());
		//System.out.println("sizeeeeeeeeeeee "+order.getOrderItems().size());
		
		OrderItem orderItem = new OrderItem(orderItemDTO);
		Product product = this.productService.findByName(orderItemDTO.getProductName());
		
		//if this item already exists
		
		if(orderItem.getId()!=null) {
			
			for(OrderItem item: order.getOrderItems()) {
				if(item.getId().equals( orderItem.getId())) {
					
					item.setProduct(product);
					item.setQuantity(orderItemDTO.getQuantity());
					this.orderItemService.add(item);
					return this.add(order);
					
				}
			}
			
		}
		
		
		
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItem = this.orderItemService.add(orderItem);
		
		
		
		return this.add(order);
	}
	
	
public Order putOrderItemQuantity(Long id, int quantity, Long orderItemId) {
		
	
		Order order = this.getById(id);
		
		for(OrderItem item: order.getOrderItems()) {
			
			
			if(item.getId().equals( orderItemId) ) {
				
				item.setQuantity(quantity);
				
				this.orderItemService.add(item);
				break;
			}
		}
		
		
		return  this.add(order);
	}
	
	



//add invoice to order***********************************

public Order addOrderInvoice(InputOrderInvoiceDTO inputOrderInvoiceDTO ) {
	
	
	Order order = this.getById(inputOrderInvoiceDTO.getOrder());
		
		if(inputOrderInvoiceDTO.getId() != null) {
			
			
			throw new IllegalArgumentException("you try to create new invoice with an ID! ID must be initialize by hibernate");
			
		}
	
	 this.invoiceService.add(inputOrderInvoiceDTO);
	
	 
	 
	return this.add(order);
}




	

	
	/*************put Payment method*********************************************
	***************************************************************************/

	public Order putPaymentMethod(int method , Long id) {
		
		Order order = this.getById(id);
		switch(method) {
		
		case 1: PayAsDelivered payAsDelivered = new PayAsDelivered();  
								payAsDelivered.setCreatedAt(new Date()); 
								payAsDelivered.setDescription("une petite description");	
								order.setPaymentMethod(payAsDelivered); 
								break;
		
		default: break;
	}
		return this.add(order);
	}

	
	/*************add order*********************************************
	***************************************************************************/
	
	public  Order add( InputOrderDTO inputOrderDTO ) {
		
		Order order = new Order(inputOrderDTO);
		order.setDiscountCode(this.discountCodeService.getById(inputOrderDTO.getDiscountCode()));
		order.setStatus(OrderStatus.PENDING);
		order.setTotalAmount(inputOrderDTO.getTotalAmount());
		order.setFulfilled(false);
		
		inputOrderDTO.getOrderItems().forEach(item ->{
			
			OrderItem orderItem = new OrderItem(item);
			
			Product product = this.productService.findByName(item.getProductName());
			if(product.getQuantity()< item.getQuantity()) {
				throw new IllegalOperationException("quantity of product "+product.getName()+" is less than required");
			}
			 product.setQuantity(product.getQuantity()-item.getQuantity());
			 orderItem.setProduct(product);
			 orderItem.setOrder(order);
			 order.getOrderItems().add(orderItem);
			
		});
		
		//*****put the method of payment for this order***/
		
		switch(inputOrderDTO.getPaymentMethod()) {
		
			case 1: PayAsDelivered payAsDelivered = new PayAsDelivered();  
									payAsDelivered.setCreatedAt(new Date()); 
									payAsDelivered.setDescription("Pay as delivered, and assure your order contains all your items");	
									order.setPaymentMethod(payAsDelivered); 
									break;
			
			default: break;
		}
		
		order.setCreatedAt(new Date());
		
		return this.add(order);
	}


	
	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Order deleteOrderItem(Long OrderItemId, Long id) {
		// TODO Auto-generated method stub
		
		Order order = this.getById(id);
		OrderItem orderItem = this.orderItemService.getById(OrderItemId);
		order.getOrderItems().remove(orderItem);
		
		
		return this.add(order);
	}

	
	public Order modify(Order modified) {
		// TODO Auto-generated method stub
		return this.orderRepository.save(modified);
	}
	
	
	

	
	public Order getById(Long id) {
		
		Order order = this.orderRepository.findById(id).orElse(null);
		 if(order==null) {
			 throw new EntityNotFoundException("Order with id ="+id+" doesn't exists");
		 }
		 return order;
	}
    
  
}


package com.bionoor.api.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputOrderDTO;
import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.dto.InputOrderItemDTO;
import com.bionoor.api.exceptions.DiscountCodeException;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.PayAsDelivered;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.CustomerRepository;
import com.bionoor.api.repositories.OrderItemRepository;
import com.bionoor.api.repositories.OrderRepository;
import com.bionoor.api.utils.InvoiceProcessingIn;

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
	
	@Autowired
	private CustomerRepository customerRepository;
	
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
	
	
	
	/* put the item quantity*/
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
		
		case 0: PayAsDelivered payAsDelivered = new PayAsDelivered();  
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
		
		
		if(inputOrderDTO.getOrderItems() == null || inputOrderDTO.getOrderItems().size() == 0) {
			
			throw new IllegalArgumentException("the orderItems can't be null or empty");
		}
		
		
		Order order = new Order();
	
		order.setAdrress("test Address");
		Customer customer = this.customerRepository.findById(UUID.fromString("24f19be7-84c3-4fd3-94be-bec9e2ff4a97")).orElse(null);
		//set customer
		order.setCustomer(customer);

		//*****put the method of payment for this order***/
		
				switch(inputOrderDTO.getPaymentMethod()) {
				
					case 0: PayAsDelivered payAsDelivered = new PayAsDelivered(); 
											//payAsDelivered.setCreatedAt(new Date()); 
											payAsDelivered.setDescription("Pay as delivered, and assure your order contains all your items");	
											order.setPaymentMethod(payAsDelivered); 
											break;
					
					default: break;
				}
		
		
		//order = this.add(order);
		//order = this.getById(order.getId());
		
		
		
		//add all items in new order
		List<OrderItem> orderItems = new ArrayList<>();
		
		
		for( InputOrderItemDTO item : inputOrderDTO.getOrderItems()) {
			
			OrderItem orderItem = new OrderItem(item);

			
			Product product = this.productService.findByName(item.getProductName());
			 orderItem.setProduct(product);
			  orderItem.setOrder(order);
			 //add item to list orderItems
			 orderItems.add(orderItem);

		}
		//save all items
		order.setOrderItems(orderItems);
		
		return this.add(order) ;
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


package com.bionoor.api.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.bionoor.api.web.RestOrder.InputOrderDTO;
import com.bionoor.api.web.RestOrder.InputOrderItemDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DiscountCodeService discountCodeService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	

	
	public Order add(Order toSave) {
		// TODO Auto-generated method stub
		return this.orderRepository.save(toSave);
	}
	
	
	public List<Order> allOrders() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAll();
	}
	
	
	public Order putStatus(String status, Long id) {
		
		Order order = this.getById(id);
		
		switch(status) {
		
			case  "PENDING":  order.setStatus(OrderStatus.PENDING); break;
			case "READY":order.setStatus(OrderStatus.READY); break;
			case "DELIVERED": order.setStatus(OrderStatus.DELIVERED); break;
			case "PROCESSING": order.setStatus(OrderStatus.PROCESSING); break;
			default:  break;
		}
		return this.orderRepository.save(order);
	}
	
	
	
		
	public Order toogleFilfilled(Boolean fulfilled , Long id) {
			
			Order order = this.getById(id);
			order.setFulfilled(fulfilled);
			return this.orderRepository.save(order);
		}
	
	/*************put discountcode*********************************************
	***************************************************************************/
	public Order addDiscountCode(Long  discountCode, Long id) {
		
		Order order = this.getById(id);
		DiscountCode code = this.discountCodeService.getById(discountCode);
		order.setDiscountCode(code);
		return this.orderRepository.save(order);
	}
	
	
	/*************put orderitem*********************************************
	***************************************************************************/
	public OrderItem putOrderItem(InputOrderItemDTO orderItemDTO  , Long id) {
		
		Order order = this.getById(id);
		
		
		for(OrderItem item : order.getOrderItems()) {
			if(item.getId() == orderItemDTO.getId()) {
				
				Product product = this.productService.getById(orderItemDTO.getProduct());
				product.setQuantity(product.getQuantity()+ item.getQuantity());
				 
				 item.setQuantity(orderItemDTO.getQuantity());
				 item.setProduct(product); return this.orderItemService.add(item);
					
			}
		}
		
		throw new EntityNotFoundException("Order or OrderItem entity didn't found");
	}
	
	/*************put orderitem*********************************************
	***************************************************************************/
	public OrderItem addOrderItem(InputOrderItemDTO orderItemDTO) {
		
		Order order = this.getById(orderItemDTO.getOrder());
		OrderItem orderItem = new OrderItem(orderItemDTO);
		Product product = this.productService.getById(orderItemDTO.getProduct());
		
		//if this item already exists
		
		if(orderItem.getId()!=null) {
			orderItem = this.orderItemService.getById(orderItem.getId());
			orderItem.setProduct(product);
			return this.orderItemService.add(orderItem);
		}
		
		//if item doesn't exists alread
		orderItem.setProduct(product);
		order.getOrderItems().add(orderItem);
		orderItem.setOrder(order);
		
		return this.orderItemService.add(orderItem);
	}
	
	
public OrderItem putOrderItemQuantity(Long id, int quantity) {
		
		
		OrderItem orderItem = this.orderItemService.getById(id);	
		orderItem.setQuantity(quantity);
		
		return this.orderItemService.add(orderItem);
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
		return this.orderRepository.save(order);
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
			
			Product product = this.productService.getById(item.getProduct());
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
		
		
		
		return this.orderRepository.save(order);
	}


	
	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public OrderItem deleteOrderItem(Long OrderItemId, Long id) {
		// TODO Auto-generated method stub
		
		Order order = this.getById(id);
		OrderItem orderItem = this.orderItemService.getById(OrderItemId);
		
		Product product = orderItem.getProduct();
		product.setQuantity(product.getQuantity()+orderItem.getQuantity());
		order.getOrderItems().remove(orderItem);
		this.productService.modify(product);
		return orderItem;
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


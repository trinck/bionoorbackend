package com.bionoor.api.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bionoor.api.dto.InputOrderDTO;
import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.dto.InputOrderItemDTO;
import com.bionoor.api.exceptions.DiscountCodeException;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.PaymentMethod;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.Order.OrderStatus;

import jakarta.persistence.EntityNotFoundException;

public interface OrderServiceIn {

	
	

	public List<Order> getAllByYear( int year) ;
	public void sendOrderConfirmation(Long id) ;
	public List<Order> findByStatus(OrderStatus status) ;
	public Order add(Order toSave) ;
	
	public List<Order> allOrders() ;
	
	public Order putStatus(String status, Long id) ;
	
	public Order toogleFilfilled(Boolean fulfilled , Long id) ;
	/*************put discountcode*********************************************
	***************************************************************************/
	public Order addDiscountCode(String  code, Long id) ;
	
	public Order deleteDiscountCode(Long id) ;
	public DiscountCode getDiscountCode(Long id) ;
	
	/*************put orderitem*********************************************
	***************************************************************************/
	public Order putOrderItem(InputOrderItemDTO orderItemDTO  , Long id) ;
	
	/*************put orderitem************************************************
	***************************************************************************/
	public Order addOrderItem(InputOrderItemDTO orderItemDTO) ;
	
	/* put the item quantity*/
public Order putOrderItemQuantity(Long id, int quantity, Long orderItemId) ;
//add invoice to order***********************************
public Order addOrderInvoice(InputOrderInvoiceDTO inputOrderInvoiceDTO );

	/*************put Payment method*********************************************
	***************************************************************************/

	public Order updatPaymentMethod(Long method , Long id) ;
	/*************add order*********************************************
	***************************************************************************/
	
	public  Order add( InputOrderDTO inputOrderDTO ) ;
	
	public String delete(Long id) ;
	
	public Order deleteOrderItem(Long OrderItemId, Long id) ;
	
	public Order modify(Order modified) ;
	public Order getById(Long id) ;
	
	public List<Order> getAllByFulfilled(boolean fulfilled, int annee);
	
	public Page<Order> findById(int page, int size, Long id, String sort);
 	public Page<Order> findByCustomerUsername( int page, int size, String sort,String username);
 	public Page<Order> findAllByStatus( int page, int size, String sort,String orderStatus);
    
}

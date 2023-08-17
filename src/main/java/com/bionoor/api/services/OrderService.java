package com.bionoor.api.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.query.IllegalQueryOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bionoor.api.dto.InputOrderDTO;
import com.bionoor.api.dto.InputOrderInvoiceDTO;
import com.bionoor.api.dto.InputOrderItemDTO;
import com.bionoor.api.exceptions.DiscountCodeException;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.DiscountCode;
import com.bionoor.api.models.DiscountDCC;
import com.bionoor.api.models.Invoice;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.PaymentAsDelivered;
import com.bionoor.api.models.PaymentMethod;
import com.bionoor.api.models.Product;
import com.bionoor.api.repositories.CustomerRepository;
import com.bionoor.api.repositories.OrderItemRepository;
import com.bionoor.api.repositories.OrderRepository;
import com.bionoor.api.utils.InvoiceProcessingIn;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService implements OrderServiceIn {

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
	private PaymentServiceImpl paymentServiceImpl;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderItemServiceIn orderItemService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private MailServiceIn mailServiceIn;

	public void sendOrderConfirmation(Long id) {

		Order order = this.getById(id);
		this.mailServiceIn.sendConfirmationOrder(order);

	}

	public Order add(Order toSave) {

		this.orderRepository.save(this.invoiceProcessingIn.TotalAmountOrder(toSave));

		return this.orderRepository.save(toSave);
	}

	public List<Order> allOrders() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAll();
	}

	public Order putStatus(String status, Long id) {

		Order order = this.getById(id);
		
		if(order.getStatus()==OrderStatus.CANCELED) {return order;}

		switch (status.toUpperCase()) {
		case "RETURNED": if(order.getStatus()==OrderStatus.READY || order.getStatus()==OrderStatus.DELIVERED) {
							order.setStatus(OrderStatus.RETURNED);}
			break;
		case "PENDING": if(order.getStatus()== OrderStatus.PROCESSING  )
			   order.setStatus(OrderStatus.PENDING);
			break;
		case "READY": if(order.getStatus()==OrderStatus.PROCESSING  && order.getInvoice()!=null)
								order.setStatus(OrderStatus.READY); 
			break;
		case "DELIVERED": if(order.getStatus()==OrderStatus.READY)
						order.setStatus(OrderStatus.DELIVERED);
			break;
		case "PROCESSING": if(order.getStatus()==OrderStatus.PENDING )
								order.setStatus(OrderStatus.PROCESSING);
			break;
			
		case "CANCELED": if(order.getStatus()==OrderStatus.PENDING || order.getStatus()==OrderStatus.PROCESSING) {
						order.setStatus(OrderStatus.CANCELED);
						return cancelOrder(order);
					}
						
			break;
		default:
			break;
		}
		return this.add(order);
	}

	public Order cancelOrder(Order order) {

		List<OrderItem> items = order.getOrderItems();

		items.forEach(item -> {
			Product p = item.getProduct();
			p.setQuantity(item.getQuantity() + p.getQuantity());
		});
		
	 return	this.add(order);

	}

	public Order toogleFilfilled(Boolean fulfilled, Long id) {

		Order order = this.getById(id);
		order.setFulfilled(fulfilled);
		return this.add(order);
	}

	/*******************************************************************************************
	 * put discountcode*************************************************************************
	 *******************************************************************************************/
	public Order addDiscountCode(String code, Long id) {

		Order order = this.getById(id);
		DiscountCode discountCode = this.discountCodeService.getByCode(code);

		if (discountCode.getActif()) {

			if (!discountCode.getEndDate().after(new Date())) {

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

				throw new DiscountCodeException(
						"DiscountCode :" + code + " is ended in " + dateFormat.format(discountCode.getEndDate()));
			} else if (order.getCustomer().getUsedDiscountCodes().contains(discountCode)) {
				throw new DiscountCodeException(String.format("You already used this discount code"));
			}

		} else {
			throw new DiscountCodeException("DiscountCode :" + code + " is deactivated");
		}

		Customer customer = order.getCustomer();
		if (discountCode.getDiscriminatorValue().equalsIgnoreCase("DCC")) {
			try {

				// (DiscountDCC) Utils.getTargetObject(discountCode, DiscountDCC.class))
				if (!customer.discountDCCsContains(discountCode)) {
					throw new IllegalOperationException("you can't use this discoun code");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new IllegalOperationException(e.getMessage());
			}

		}

		next: order.setDiscountCode(discountCode);

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

		if (code == null)
			throw new EntityNotFoundException("this order have not discount code ");
		return code;
	}

	/*************************************************************************
	 * put orderitem**********************************************************
	 ***************************************************************************/
	public Order putOrderItem(InputOrderItemDTO orderItemDTO, Long id) {

		Order order = this.getById(id);

		for (OrderItem item : order.getOrderItems()) {
			if (item.getId() == orderItemDTO.getId()) {

				Product product = this.productService.findByName(orderItemDTO.getProductName());

				item.setQuantity(orderItemDTO.getQuantity());
				item.setProduct(product);
				return this.add(order);

			}
		}

		throw new EntityNotFoundException("Order or OrderItem entity didn't found");
	}

	/*************
	 * put orderitem************************************************
	 ***************************************************************************/
	public Order addOrderItem(InputOrderItemDTO orderItemDTO) {

		Order order = this.getById(orderItemDTO.getOrder());
		// System.out.println("sizeeeeeeeeeeee "+order.getOrderItems().size());

		OrderItem orderItem = new OrderItem(orderItemDTO);
		Product product = this.productService.findByName(orderItemDTO.getProductName());

		// if this item already exists

		if (orderItem.getId() != null) {

			for (OrderItem item : order.getOrderItems()) {
				if (item.getId().equals(orderItem.getId())) {

					if (product.getQuantity() < item.getQuantity()) {
						throw new IllegalOperationException("Available product's quantity is less than required");
					}
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
	
	

	/* put the item quantity */
	public Order putOrderItemQuantity(Long id, int quantity, Long orderItemId) {

		Order order = this.getById(id);

		for (OrderItem item : order.getOrderItems()) {

			if (item.getId().equals(orderItemId)) {

				if (item.getProduct().getQuantity() < quantity) {

					throw new IllegalOperationException("Available product's quantity is less than required");
				}

				item.setQuantity(quantity);
				this.orderItemService.add(item);
				break;
			}
		}

		return this.add(order);
	}

	
//add invoice to order***********************************
	public Order addOrderInvoice(InputOrderInvoiceDTO inputOrderInvoiceDTO) {

		Order order = this.getById(inputOrderInvoiceDTO.getOrder());

		if (inputOrderInvoiceDTO.getId() != null) {
			this.invoiceService.modify(inputOrderInvoiceDTO);
			return this.add(order);
		}

		this.invoiceService.add(inputOrderInvoiceDTO);

		return this.add(order);
	}

	/*************************************************************************
	 * put Payment method***************************************************
	 ***************************************************************************/

	public Order updatPaymentMethod(Long method, Long id) {

		Order order = this.getById(id);
		PaymentMethod paymentMethod = this.paymentServiceImpl.getPaymentMethodById(method);

		order.setPaymentMethod(paymentMethod);
		return this.add(order);
	}

	/***************************************************************************
	 * add order***************************************************************
	 ***************************************************************************/

	public Order add(InputOrderDTO inputOrderDTO) {

		if (inputOrderDTO.getOrderItems() == null || inputOrderDTO.getOrderItems().size() == 0) {

			throw new IllegalArgumentException("the orderItems can't be null or empty");
		}

		Order order = new Order();

		Customer customer = this.customerRepository.findById(UUID.fromString("3163a3a8-a0cf-4b23-b419-b1784865951a"))
				.orElse(null);

		// set customer
		order.setCustomer(customer);
		order.setAdrress(customer.getAddress().getCity().getName() + "," + customer.getAddress().getStreet()); 
		PaymentMethod paymentMethod = this.paymentServiceImpl.getPaymentMethodById(inputOrderDTO.getPaymentMethod());

		order.setPaymentMethod(paymentMethod);
		
		// add all items in new order
		List<OrderItem> orderItems = new ArrayList<>();

		for (InputOrderItemDTO item : inputOrderDTO.getOrderItems()) {

			OrderItem orderItem = new OrderItem(item);

			Product product = this.productService.findByName(item.getProductName());
			if (item.getQuantity() > product.getQuantity()) {

				throw new IllegalArgumentException(
						String.format("The product %s is not anough", item.getProductName()));
			}

			product.setQuantity(product.getQuantity() - item.getQuantity());
			orderItem.setProduct(product);
			orderItem.setOrder(order);
			// add item to list orderItems
			orderItems.add(orderItem);

		}
		// save all items
		order.setOrderItems(orderItems);

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
		if (order == null) {
			throw new EntityNotFoundException("Order with id =" + id + " doesn't exists");
		}
		return order;
	}

	@Override
	public List<Order> findByStatus(OrderStatus status) {

		return this.orderRepository.findAllByStatus(status);
	}

	@Override
	public List<Order> getAllByFulfilled(boolean fulfilled, int annee) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByFulfilledAndYears(fulfilled, annee);
	}

	public List<Order> getAllByYear(int year) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByYear(year);
	}

	@Override
	public Page<Order> findById(int page, int size, Long id, String sort) {
		String[] sorts = sort.split(":");
		Page<Order> pages;
		if (id != null) {
			if (sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.orderRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			} else {

				pages = this.orderRepository.findById(id, PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		} else {

			if (sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.orderRepository.findAll(PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			} else {

				pages = this.orderRepository.findAll(PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}

		}

		return pages;
	}

	@Override
	public Page<Order> findByCustomerUsername(int page, int size, String sort, String username) {
		String[] sorts = sort.split(":");
		Page<Order> pages;
		if (username != null) {
			if (sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.orderRepository.findByCustomerUsername(username,
						PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			} else {

				pages = this.orderRepository.findByCustomerUsername(username,
						PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		} else {

			if (sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.orderRepository.findAll(PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			} else {

				pages = this.orderRepository.findAll(PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}

		}

		return pages;
	}

	@Override
	public Page<Order> findAllByStatus(int page, int size, String sort, String orderStatus) {
		String[] sorts = sort.split(":");
		Page<Order> pages;
		if (orderStatus != null) {
			if (sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.orderRepository.findAllByStatus(OrderStatus.valueOf(orderStatus.toUpperCase()),
						PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));

			} else {

				pages = this.orderRepository.findAllByStatus(OrderStatus.valueOf(orderStatus.toUpperCase()),
						PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}
		} else {

			if (sorts[1].equalsIgnoreCase("ascending")) {
				pages = this.orderRepository.findAll(PageRequest.of(page, size, Sort.by(sorts[0]).ascending()));
			} else {

				pages = this.orderRepository.findAll(PageRequest.of(page, size, Sort.by(sorts[0]).descending()));

			}

		}

		return pages;
	}

}

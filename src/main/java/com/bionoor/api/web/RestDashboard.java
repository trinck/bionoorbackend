package com.bionoor.api.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bionoor.api.dto.OutputBestSellerListDTO;
import com.bionoor.api.dto.OutputOrderDTO;
import com.bionoor.api.dto.OutputProductBestSeller;
import com.bionoor.api.dto.OutputProductDTO;
import com.bionoor.api.exceptions.IllegalOperationException;
import com.bionoor.api.models.Address;
import com.bionoor.api.models.Customer;
import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.OrderItem;
import com.bionoor.api.models.Product;
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
	@Value("${app.stock-average}")
	private int stockAverage;
	
	
	@GetMapping("/productsOfMonth")
	public  OutputBestSellerListDTO  productsOfMonth(@RequestParam(defaultValue = "true") Boolean fulfilled,@RequestParam(required = false) Integer month, @RequestParam(defaultValue = "4") Integer limit){
		
		
		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			 int year = calendar.get(Calendar.YEAR);
		
		
			 month = month == null? calendar.get(Calendar.MONTH): month;
			 
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, year);
		Map<String, List<Order>> sortedByMonth = sortByMonthOrder(list);
		
		
		 List<Order> currentMonthOrders = sortedByMonth.get(String.valueOf(month));
		 if(currentMonthOrders == null) {
			 throw new NullPointerException("There is no products sold this month");
		 }
		 
		 Map<Product,Integer> products = new HashMap();
		 for(Order order: currentMonthOrders) {
			 
			 for(OrderItem item: order.getOrderItems()) {
				 
				 if(products.get(item.getProduct())== null) {
					 products.put(item.getProduct(), item.getQuantity());
					
				 }else {
					
					 products.put(item.getProduct(), products.get(item.getProduct())+item.getQuantity());
					
				 }
								 		 
			 }
			 
		 }
		 
		 
		 
		 List<OutputProductBestSeller> body = new ArrayList<>();
		 products.forEach((p,i)->{
			 
			 OutputProductBestSeller bestSeller = new OutputProductBestSeller(p);
			 bestSeller.setEarned(i*p.getPrice());
			 bestSeller.setQuantitySold(i);
			 body.add(bestSeller );
			
		 });
		 
		 body.sort(new Comparator<OutputProductBestSeller>() {

			@Override
			public int compare(OutputProductBestSeller o1, OutputProductBestSeller o2) {
				// TODO Auto-generated method stub
				return o1.getQuantitySold()>= o2.getQuantitySold()?-1 :1 ;
			}
		});		 
		 
		 OutputBestSellerListDTO bestSellerDTO = OutputBestSellerListDTO
				 .builder()
				 .limit(limit)
				 .products(body.subList(0, limit<= body.size()? limit: body.size()))
				 .stockAverage(stockAverage)
				 .month(month)
				 .build();		 
		return bestSellerDTO ;
	}
	
	
	
	
	@GetMapping("/sales")
	public Map<String,  List<OutputOrderDTO>> sales(@RequestParam(defaultValue = "true") Boolean fulfilled, Integer year){
		
		
		if(year ==null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			year = calendar.get(Calendar.YEAR);
		}
		
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, year);
		List<OutputOrderDTO> orderDTOs = toOutputDTO(list);
		Map<String, List<OutputOrderDTO>> sortedByMonth = sortByMonth(orderDTOs);
		return sortedByMonth;
	}
	
	
	
	
	@GetMapping("/productsStock")
	public Map<String,  Integer> stock(){
		
		
		List<Product> list = this.productServiceIn.allProducts();
		
		Map<String, Integer> stock = new HashMap();
		
		List<Product> outOfStock = list.stream().filter(p -> p.getQuantity()==0?true:false).toList();
		List<Product> lowStock = list.stream().filter(p -> (p.getQuantity()>0 && p.getQuantity()<=stockAverage)?true:false).toList();
		List<Product> available = list.stream().filter(p -> p.getQuantity()>stockAverage?true: false).toList();
		
		

		stock.put("Low Stock Items", lowStock.size());
		stock.put("Available Items", available.size());
		stock.put("Stockout Items", outOfStock.size());
		
		
		return stock;
	}
	
	
	
	@GetMapping("/ordersByRegion")
	public Map<String, Map<String,List<OutputOrderDTO>>> orderByRegion(@RequestParam(defaultValue = "true") Boolean fulfilled, Integer year){
		
		
		if(year ==null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			year = calendar.get(Calendar.YEAR);
		}
		
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, year);
		Map<String, List<Order>> listByRegion = new HashMap<String, List<Order>>();
		
		list.forEach(order ->{
			
			
			Address address = order.getCustomer().getAddress();
			String key = address.getCity().getCountry().getName()+":"+address.getCity().getName();
			if(listByRegion.get(key)== null){
			
				List<Order> listOrder = new ArrayList();
				listOrder.add(order);
				listByRegion.put(key, listOrder);
				
			 }else {
				
				 listByRegion.get(key).add(order);
				
			 }
			
		});
		
		
		Map<String, Map<String,List<OutputOrderDTO>>> listByRegionToSend = new HashMap();
		
		
		for(Entry<String, List<Order>> entry:listByRegion.entrySet() ) {
			
			String[] keys = entry.getKey().split(":");
			
			if(listByRegionToSend.containsKey(keys[0])) {
				
				Map<String,List<OutputOrderDTO>> mapByCity = listByRegionToSend.get(keys[0]);
				if(mapByCity.containsKey(keys[1])){
					
					mapByCity.get(keys[1]).addAll(toOutputDTO( entry.getValue()));
				}else {
					
					mapByCity.put(keys[1],toOutputDTO( entry.getValue()));
				}
				
			}else {
				Map<String,List<OutputOrderDTO>> mapByCity = new HashMap();
				mapByCity.put(keys[1], toOutputDTO( entry.getValue()));
				listByRegionToSend.put(keys[0], mapByCity);
			}
			
		}
		
		
		
		return listByRegionToSend;
	}
	
	
	
	@GetMapping("/ordersByStatus")
	public Map<OrderStatus, List<OutputOrderDTO>> ordersByStatus(){
		
		List<OutputOrderDTO> ready = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.READY));
		List<OutputOrderDTO> pending = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.PENDING));
		List<OutputOrderDTO> processing = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.PROCESSING));
		List<OutputOrderDTO> returned = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.RETURNED));
		List<OutputOrderDTO> delivered = toOutputDTO( this.orderServiceIn.findByStatus(OrderStatus.DELIVERED));
		
		Map<OrderStatus, List<OutputOrderDTO> > body = new HashMap<>();
		
		body.put(OrderStatus.READY, ready);
		body.put(OrderStatus.PENDING, pending);
		body.put(OrderStatus.PROCESSING, processing);
		body.put(OrderStatus.RETURNED, returned);
		body.put(OrderStatus.DELIVERED, delivered);
		
		return body;
	} 
	
	
	
	@GetMapping("/fulfilled")
	public Map<String, Map<String, List<OutputOrderDTO>>> fulfilled(@RequestParam boolean fulfilled, Integer year) {
		
		
		
		if(year ==null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			year = calendar.get(Calendar.YEAR);
		}
		
		List<Order> list = this.orderServiceIn.getAllByFulfilled(fulfilled, year);
		List<OutputOrderDTO> orderDTOs = toOutputDTO(list);
		Map<String, List<OutputOrderDTO>> groupedByDate = sortByMonth(orderDTOs);
		
		
		
		
		List<Order> list2 = this.orderServiceIn.getAllByYear(year);
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
	
	
	
	
public Map<String, List<Order>> sortByMonthOrder(List<Order> orders){
		
		Map<String, List<Order>> groupedByDate = new HashMap<>();
			
			for(Order order2: orders) {
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(order2.getCreatedAt());
					int month = calendar.get(Calendar.MONTH);
					String stringBuilder = ""+month;
					if(groupedByDate.containsKey(stringBuilder)) {
						
						groupedByDate.get(stringBuilder).add(order2);
					}else{
						List<Order> newlist = new ArrayList<>();
						newlist.add(order2);
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

package com.bionoor.api.services;

import org.springframework.stereotype.Service;

import com.bionoor.api.models.Order;
import com.bionoor.api.repositories.OrderRepository;

@Service
public class OrderService  implements ServiceInterface<Order>{

	OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		// TODO Auto-generated constructor stub
		
		this.orderRepository = orderRepository;
	}
	
	@Override
	public Order add(Order toSave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order modify(Order modified) {
		// TODO Auto-generated method stub
		return this.orderRepository.save(modified);
	}

	@Override
	public Order getById(Long id) {
		
		return this.orderRepository.findById(id).orElse(null);
	}
    
  
}


package com.bionoor.api.services;

import com.bionoor.api.models.OrderItem;


public interface OrderItemServiceIn {

public OrderItem delete(Long id) ;
	
	public OrderItem modify(OrderItem orderItem) ;
	public OrderItem add(OrderItem orderItem);


	
	public OrderItem getById(Long id) ;
}

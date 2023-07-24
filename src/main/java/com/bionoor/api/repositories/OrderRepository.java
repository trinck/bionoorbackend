package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

 	public List<Order> findAllByStatus(OrderStatus status);
}
